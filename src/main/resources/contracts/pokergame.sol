// SPDX-License-Identifier: MIT
pragma solidity ^0.8.25;

contract PokerTournament is VRFConsumerBaseV2 {
    VRFCoordinatorV2Interface private immutable COORDINATOR;
    uint64 private immutable subscriptionId;
    bytes32 private immutable keyHash;
    uint32 private constant CALLBACK_GAS_LIMIT = 500000;
    uint16 private constant REQUEST_CONFIRMATIONS = 3;
    uint32 private constant NUM_WORDS = 2;

    struct Player {
        address payable addr;
        uint256 stack;
        bool folded;
        bytes2[2] holeCards;
    }

    enum GamePhase {REGISTRATION, PREFLOP, FLOP, TURN, RIVER, SHOWDOWN}

    Player[] public players;
    bytes2[] public communityCards;
    GamePhase public currentPhase;
    uint256 public pot;
    uint256 public requestId;
    address public dealer;

    mapping(uint256 => bytes32) public randomRequests;

    event CardsDealt(uint256 requestId);
    event CommunityCardsRevealed(bytes2[] cards);
    event PlayerAction(address player, string action, uint256 amount);
    event Winner(address winner, uint256 amount);

    constructor(
        address vrfCoordinator,
        uint64 _subscriptionId,
        bytes32 _keyHash
    ) VRFConsumerBaseV2(vrfCoordinator) {
        COORDINATOR = VRFCoordinatorV2Interface(vrfCoordinator);
        subscriptionId = _subscriptionId;
        keyHash = _keyHash;
        dealer = msg.sender;
    }

    function startTournament() external onlyDealer {
        require(players.length >= 2, "Minimum 2 players");
        currentPhase = GamePhase.REGISTRATION;
        requestId = COORDINATOR.requestRandomWords(
            keyHash,
            subscriptionId,
            REQUEST_CONFIRMATIONS,
            CALLBACK_GAS_LIMIT,
            NUM_WORDS
        );
        randomRequests[requestId] = keccak256(abi.encodePacked(block.timestamp));
    }

    function fulfillRandomWords(uint256 _requestId, uint256[] memory randomWords) internal override {
        require(randomRequests[_requestId] != bytes32(0), "Invalid request");

        // Distribuição segura de cartas usando 2 números aleatórios
        uint256 seed = randomWords[0];
        _distributeHoleCards(seed);
        seed = randomWords[1];
        _prepareCommunityCards(seed);

        currentPhase = GamePhase.PREFLOP;
        emit CardsDealt(_requestId);
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
    }

    function _createShuffledDeck(uint256 seed) private pure returns (bytes2[52] memory) {
        bytes2[52] memory deck;
        // Implementação do Fisher-Yates shuffle com seed
        for (uint8 i = 0; i < 52; i++) {
            deck[i] = bytes2(uint16(i));
        }

        for (uint256 i = 0; i < 52; i++) {
            uint256 j = (uint256(keccak256(abi.encodePacked(seed, i))) % (52 - i)) + i;
            (deck[i], deck[j]) = (deck[j], deck[i]);
        }
        return deck;
    }

    modifier onlyDealer() {
        require(msg.sender == dealer, "Not dealer");
        _;
    }
}