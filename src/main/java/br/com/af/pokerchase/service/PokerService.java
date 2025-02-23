package br.com.af.pokerchase.service;

import br.com.af.pokerchase.config.ApplicationProperties;
import br.com.af.pokerchase.contract.PokerToken;
import br.com.af.pokerchase.controller.PokerWebSocketController;
import br.com.af.pokerchase.entity.ActionEntity;
import br.com.af.pokerchase.entity.CardEntity;
import br.com.af.pokerchase.entity.CommunityCardEntity;
import br.com.af.pokerchase.entity.PlayerEntity;
import br.com.af.pokerchase.entity.PlayerHandEntity;
import br.com.af.pokerchase.entity.TableEntity;
import br.com.af.pokerchase.enums.PlayerActionEnum;
import br.com.af.pokerchase.repository.ActionRepository;
import br.com.af.pokerchase.repository.CardRepository;
import br.com.af.pokerchase.repository.CommunityCardRepository;
import br.com.af.pokerchase.repository.PlayerHandRepository;
import br.com.af.pokerchase.repository.PlayerRepository;
import br.com.af.pokerchase.repository.TableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.ens.EnsResolutionException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
public class PokerService {

  private final ApplicationProperties appProperties;
  private final PokerToken contract;
  private final PokerWebSocketController webSocketController;
  private final TableRepository tableRepository;
  private final PlayerRepository playerRepository;
  private final CardRepository cardRepository;
  private final PlayerHandRepository playerHandRepository;
  private final CommunityCardRepository communityCardRepository;
  private final ActionRepository actionRepository;

  @Autowired
  public PokerService(ApplicationProperties appProperties,
                      PokerWebSocketController webSocketController,
                      TableRepository tableRepository,
                      PlayerRepository playerRepository,
                      CardRepository cardRepository,
                      PlayerHandRepository playerHandRepository,
                      CommunityCardRepository communityCardRepository,
                      ActionRepository actionRepository) throws IOException {
    this.appProperties = appProperties;
    this.tableRepository = tableRepository;
    this.playerRepository = playerRepository;
    this.cardRepository = cardRepository;
    this.playerHandRepository = playerHandRepository;
    this.communityCardRepository = communityCardRepository;
    this.actionRepository = actionRepository;
    contract = loadContract();
    this.webSocketController = webSocketController;
    monitorEvents();
  }

  private PokerToken loadContract() throws IOException {
    try {
      Web3j web3j = Web3j.build(new HttpService(appProperties.getRpcUrl()));
      Credentials credentials = Credentials.create(appProperties.getPrivateKey());
      return PokerToken.load(appProperties.getContractAddress(), web3j, credentials, new DefaultGasProvider());
    } catch (EnsResolutionException e) {
      log.error("Erro ao resolver o endereço do contrato: {}", e.getMessage());
      throw new IOException("Falha ao carregar o contrato", e);
    }
  }

  public void createTable(long tableId, BigInteger entryFee) throws Exception {
    contract.createTable(BigInteger.valueOf(tableId), entryFee).send();
    TableEntity table = new TableEntity();
    table.setId(tableId);
    table.setEntryFee(entryFee);
    table.setActive(true);
    table.setState("WAITING");
    tableRepository.save(table);
  }

  // Adicionar jogador à mesa e salvar no banco
  public void joinTable(long tableId, String playerAddress, BigInteger amount) throws Exception {
    contract.joinTable(BigInteger.valueOf(tableId), playerAddress, amount).send();
    TableEntity table = tableRepository.findById(tableId).orElseThrow();
    PlayerEntity player = playerRepository.findByAddress(playerAddress);
    if (player == null) {
      player = new PlayerEntity();
      player.setAddress(playerAddress);
      player.setTable(table);
      playerRepository.save(player);
    }
  }

  // Atualizar o estado da mesa e salvar no banco
  public void setTableState(long tableId, String newState) throws Exception {
    BigInteger stateIndex = getStateIndex(newState);
    contract.setTableState(BigInteger.valueOf(tableId), stateIndex).send();
    TableEntity table = tableRepository.findById(tableId).orElseThrow();
    table.setState(newState);
    tableRepository.save(table);
  }

  // Distribuir cartas para os jogadores e salvar no banco
  public void dealPlayerCards(long tableId, String playerAddress, CardEntity card1, CardEntity card2) throws Exception {
    PlayerEntity player = playerRepository.findByAddress(playerAddress);
    cardRepository.save(card1);
    cardRepository.save(card2);
    PlayerHandEntity hand = new PlayerHandEntity();
    hand.setPlayer(player);
    hand.setCard1(card1);
    hand.setCard2(card2);
    playerHandRepository.save(hand);
  }

  // Distribuir cartas comunitárias e salvar no banco
  public void dealCommunityCards(long tableId, List<CardEntity> cards) throws Exception {
    TableEntity table = tableRepository.findById(tableId).orElseThrow();
    for (CardEntity card : cards) {
      cardRepository.save(card);
      CommunityCardEntity communityCard = new CommunityCardEntity();
      communityCard.setTable(table);
      communityCard.setCard(card);
      communityCardRepository.save(communityCard);
    }
  }

  // Registrar ação do jogador e salvar no banco
  public void registerPlayerAction(long tableId, String playerAddress, String actionType, BigInteger amount) throws Exception {
    TableEntity table = tableRepository.findById(tableId).orElseThrow();
    PlayerEntity player = playerRepository.findByAddress(playerAddress);
    ActionEntity action = new ActionEntity();
    action.setAction(PlayerActionEnum.valueOf(actionType));
    action.setAmount(amount);
    action.setPlayer(player);
    action.setTable(table);
    actionRepository.save(action);
    if (actionType.equals("RAISE") || actionType.equals("CALL")) {
      contract.updatePot(BigInteger.valueOf(tableId), table.getEntryFee().add(amount)).send();
    }
  }

  // Finalizar mesa e salvar no banco
  public void finalizeTable(long tableId, String winnerAddress, BigInteger amount) throws Exception {
    contract.finalizeTable(BigInteger.valueOf(tableId), winnerAddress, amount).send();
    TableEntity table = tableRepository.findById(tableId).orElseThrow();
    table.setActive(false);
    tableRepository.save(table);
  }

  private void monitorEvents() {
    contract.tableCreatedEventFlowable().subscribe(event -> {
      String message = "Table created: " + event.tableId;
      webSocketController.sendEvent("TABLE_CREATED", message);
    });

    contract.playerJoinedEventFlowable().subscribe(event -> {
      String message = "Player " + event.player + " joined table " + event.tableId;
      webSocketController.sendEvent("PLAYER_JOINED", message);
    });

    contract.potUpdatedEventFlowable().subscribe(event -> {
      String message = "Pot updated for table " + event.tableId + ": " + event.newPot;
      webSocketController.sendEvent("POT_UPDATED", message);
    });

    contract.winnerPaidEventFlowable().subscribe(event -> {
      String message = "Winner " + event.winner + " paid " + event.amount + " for table " + event.tableId;
      webSocketController.sendEvent("WINNER_PAID", message);
    });

    contract.tableClosedEventFlowable().subscribe(event -> {
      String message = "Table " + event.tableId + " closed";
      webSocketController.sendEvent("TABLE_CLOSED", message);
    });

    contract.stateChangedEventFlowable().subscribe(event -> {
      String message = "Table " + event.tableId + " state changed to " + event.newState;
      webSocketController.sendEvent("STATE_CHANGED", message);
    });
  }

  private BigInteger getStateIndex(String state) {
    switch (state) {
      case "WAITING":
        return BigInteger.ZERO;
      case "PREFLOP":
        return BigInteger.ONE;
      case "FLOP":
        return BigInteger.valueOf(2);
      case "TURN":
        return BigInteger.valueOf(3);
      case "RIVER":
        return BigInteger.valueOf(4);
      case "SHOWDOWN":
        return BigInteger.valueOf(5);
      case "FINISHED":
        return BigInteger.valueOf(6);
      default:
        throw new IllegalArgumentException("Estado inválido: " + state);
    }
  }

}
