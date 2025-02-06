pragma solidity ^0.8.25;

contract PokerGame is ReentrancyGuard, VRFConsumerBaseV2 {

    struct Player {
        address payable wallet;
        uint256 balance;
        bool fold;
        bytes32 hasCards;
    }


    uint256 public constant RAKE_TAX = 2; // 2%
    uint256 public constant BIG_BLIND = 0.05; // 0.05 ETH

    enum State {WAITING, DEALING, PRE_FLOP, FLOP, TURN, RIVER, SHOWDOWN}
    State public currentState;


    Player[] public players;
    uint256 public pot;
    bytes32[] public communityCards;
    address public dealer;

    VRFCoordinatorV2Interface public COORDINATOR;
    uint256 public subscriptionId;
    bytes32 public keyHash;
    uint256 public callbackGasLimit = 500000;

    event StartGame(uint256 indexed gameId);
    event EndGame(uint256 indexed gameId);
    event PlayerAction(address indexed player, uint256 indexed gameId, string action, uint256 amount);
    event CommunityCards(uint256 indexed gameId, bytes32[] cards);
    event DeclareWinner(uint256 indexed gameId, address indexed winner, uint256 amount);

    constructor(address _vrfCoordinator, uint256 _subscriptionId) {
        VRFConsumerBaseV2(_vrfCoordinator);
        COORDINATOR = VRFCoordinatorV2(_vrfCoordinator);
        subscriptionId = _subscriptionId;
    }

    function enterGame() external payable {
        require(msg.value >= BIG_BLIND * 10, "Insufficient balance");
        players.push(Player(payable(msg.sender), msg.value, false, 0));
    }

    function startGame() external onlyDealer {
        require(players.length >= 2, "Insufficient players");
        currentState = State.PRE_FLOP;
        _getRandomCard();
        emit StartGame(block.timestamp);
    }

    function action(uint256 _gameId, uint8 _action, uint256 _amount) external nonReentrant {
        require(currentState != State.WAITING, "Game not started");
        require(currentState != State.SHOWDOWN, "Game finished");
        require(msg.sender == players[0].wallet, "Not your turn");
        require(_amount > 0, "Invalid amount");

        Player storage player = _getPlayer(msg.sender);

        if (_action == 0) {
            player.fold = true;
        } else if (_action == 1) {
            _processBet(player, _amount);
        } else {
            _raise(_amount);
        }

        emit PlayerAction(msg.sender, _gameId, _action, _amount);
    }


    function fulfillRandomWords(uint256 requestId, uint256[] memory randomWords) internal override {
        _distribuirCartas(randomWords);
        emit DeliverCards(_getCardsCommit());
    }

    function _distribuirCartas(uint256[] memory randomNumbers) private {
        // Lógica segura de distribuição usando VRF
    }

}