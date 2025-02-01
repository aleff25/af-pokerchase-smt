package br.com.af.pokerchase.service;

import br.com.af.pokerchase.common.exception.TournamentNotFoundException;
import br.com.af.pokerchase.domain.GameType;
import br.com.af.pokerchase.domain.Tournament;
import br.com.af.pokerchase.domain.TournamentStatus;
import br.com.af.pokerchase.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentService {

  private final TournamentRepository tournamentRepository;
  private final PokerGameService pokerGameService;

  public void startNextRound(Tournament tournament) {
    List<String> remainingPlayers = getRemainingPlayers(tournament);
    List<List<String>> tables = groupPlayersIntoTables(remainingPlayers, 6); // 6 jogadores por mesa

    tables.forEach(tablePlayers -> {
      String gameId = pokerGameService.createGame(GameType.TEXAS_HOLDEM, tablePlayers).gameId();
      tournament.getActiveGames().add(gameId);
    });

    tournament.setCurrentRound(tournament.getCurrentRound() + 1);
    tournamentRepository.save(tournament);
  }

  private List<String> getRemainingPlayers(Tournament tournament) {
    // Implemente a lógica para filtrar jogadores ainda no torneio
    return tournament.getPlayerIds(); // Simplificado
  }

  private List<List<String>> groupPlayersIntoTables(List<String> players, int tableSize) {
    // Divide os jogadores em grupos de `tableSize`
    return List.of(players); // Simplificado
  }

  // Método para iniciar um torneio
  public void startTournament(String tournamentId, List<String> playerIds) {
    Tournament tournament = new Tournament();
    tournament.setId(tournamentId);
    tournament.setPlayerIds(playerIds);
    tournament.setActiveGames(new ArrayList<>());
    tournament.setCurrentRound(1);
    tournament.setStatus(TournamentStatus.IN_PROGRESS);
    tournamentRepository.save(tournament);

    startNextRound(tournament);
  }

  // Método para finalizar um torneio
  public void endTournament(String tournamentId) {
    Tournament tournament = tournamentRepository.findById(tournamentId)
      .orElseThrow(() -> new TournamentNotFoundException(tournamentId));

    tournament.setStatus(TournamentStatus.COMPLETED);
    tournamentRepository.save(tournament);
  }
}
