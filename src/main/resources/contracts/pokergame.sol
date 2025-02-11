// SPDX-License-Identifier: MIT
pragma solidity ^0.8.25;

contract PokerTournament {
    struct Player {
        address payable addr;
        uint256 stack;
        bool folded;
        bytes2[2] holeCards;
    }

    enum GamePhase {REGISTRATION, PREFLOP, FLOP, TURN, RIVER, SHOWDOWN, FINISHED}

    Player[] public players;
    bytes2[] public communityCards;
    GamePhase public currentPhase;
    uint256 public pot;
    address public dealer;

    event PlayerRegistered(address player);
    event CardsDealt();
    event CommunityCardsRevealed(bytes2[] cards);
    event PlayerAction(address player, string action, uint256 amount);
    event PhaseAdvanced(GamePhase newPhase);
    event Winner(address winner, uint256 amount);

    constructor() {
        dealer = msg.sender;
        currentPhase = GamePhase.REGISTRATION;
    }

    modifier onlyDealer() {
        require(msg.sender == dealer, "Not dealer");
        _;
    }

    modifier inPhase(GamePhase phase) {
        require(currentPhase == phase, "Invalid phase");
        _;
    }

    function registerPlayer() external payable inPhase(GamePhase.REGISTRATION) {
        require(msg.value > 0, "Deve enviar um valor como stake");
        players.push(Player(payable(msg.sender), msg.value, false, [bytes2(0), bytes2(0)]));
        emit PlayerRegistered(msg.sender);
    }

    function startTournament() external onlyDealer inPhase(GamePhase.REGISTRATION) {
        require(players.length >= 2, "Mínimo 2 jogadores");
        uint256 seed = uint256(keccak256(abi.encodePacked(block.timestamp, block.difficulty, msg.sender)));
        _distributeHoleCards(seed);
        _prepareCommunityCards(seed);
        currentPhase = GamePhase.PREFLOP;
        emit CardsDealt();
    }

    function registerPlayerAction(string calldata action, uint256 amount) external {
        emit PlayerAction(msg.sender, action, amount);
    }

    function advancePhase() external onlyDealer returns (bool) {
        if (currentPhase == GamePhase.PREFLOP) {
            currentPhase = GamePhase.FLOP;
        } else if (currentPhase == GamePhase.FLOP) {
            currentPhase = GamePhase.TURN;
        } else if (currentPhase == GamePhase.TURN) {
            currentPhase = GamePhase.RIVER;
        } else if (currentPhase == GamePhase.RIVER) {
            currentPhase = GamePhase.SHOWDOWN;
        } else if (currentPhase == GamePhase.SHOWDOWN) {
            currentPhase = GamePhase.FINISHED;
        } else {
            return false;
        }
        emit PhaseAdvanced(currentPhase);
        return true;
    }

    function announceWinner(address winner, uint256 amount) external onlyDealer inPhase(GamePhase.SHOWDOWN) {
        payable(winner).transfer(amount);
        emit Winner(winner, amount);
        currentPhase = GamePhase.FINISHED;
    }

    function _distributeHoleCards(uint256 seed) private {
        bytes2[52] memory deck = _createShuffledDeck(seed);
        uint256 cardIndex = 0;

        for (uint256 i = 0; i < players.length; i++) {
            players[i].holeCards[0] = deck[cardIndex++];
            players[i].holeCards[1] = deck[cardIndex++];
        }
    }

    function _prepareCommunityCards(uint256 seed) private {
        bytes2[52] memory deck = _createShuffledDeck(seed);
        communityCards.push(deck[0]);
        communityCards.push(deck[1]);
        communityCards.push(deck[2]);
        emit CommunityCardsRevealed(communityCards);
    }

    function _createShuffledDeck(uint256 seed) private pure returns (bytes2[52] memory) {
        bytes2[52] memory deck;
        for (uint8 i = 0; i < 52; i++) {
            deck[i] = bytes2(uint16(i));
        }
        for (uint256 i = 0; i < 52; i++) {
            uint256 j = (uint256(keccak256(abi.encodePacked(seed, i))) % (52 - i)) + i;
            (deck[i], deck[j]) = (deck[j], deck[i]);
        }
        return deck;
    }
}
