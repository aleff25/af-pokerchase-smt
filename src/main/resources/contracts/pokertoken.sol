// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract PokerToken is Initializable, UUPSUpgradeable, OwnableUpgradeable, ERC20Upgradeable {
    address public backendServer;

    // Estados do Texas Hold'em
    enum GameState {WAITING, PREFLOP, FLOP, TURN, RIVER, SHOWDOWN, FINISHED}

    // Estrutura para representar uma mesa de poker
    struct Table {
        uint256 tableId;
        uint256 entryFee;
        bool isActive;
        address[] players;
        uint256 pot;
        GameState state;
    }

    mapping(uint256 => Table) public tables;

    // Eventos
    event TableCreated(uint256 indexed tableId, uint256 entryFee);
    event PlayerJoined(uint256 indexed tableId, address indexed player, uint256 amount);
    event PotUpdated(uint256 indexed tableId, uint256 newPot);
    event WinnerPaid(uint256 indexed tableId, address indexed winner, uint256 amount);
    event TableClosed(uint256 indexed tableId);
    event StateChanged(uint256 indexed tableId, GameState newState);

    function initialize(string memory name, string memory symbol, address _backendServer) public initializer {
        __ERC20_init(name, symbol);
        __Ownable_init(msg.sender);
        __UUPSUpgradeable_init();
        backendServer = _backendServer;
        _mint(msg.sender, 1_000_000 * 10 ** 18);
    }

    function _authorizeUpgrade(address newImplementation) internal override onlyOwner {}

    modifier onlyBackend() {
        require(msg.sender == backendServer, "Only backend server can call this");
        _;
    }

    // Criar uma nova mesa com estado WAITING
    function createTable(uint256 tableId, uint256 entryFee) external onlyBackend {
        require(tables[tableId].entryFee == 0, "Table already exists");
        tables[tableId] = Table(tableId, entryFee, true, new address[](0), 0, GameState.WAITING);
        emit TableCreated(tableId, entryFee);
        emit StateChanged(tableId, GameState.WAITING);
    }

    // Função para atualizar o estado da mesa
    function setTableState(uint256 tableId, GameState newState) external onlyBackend {
        require(tables[tableId].isActive, "Table not active");
        tables[tableId].state = newState;
        emit StateChanged(tableId, newState);
    }

    // Função para adicionar jogador à mesa
    function joinTable(uint256 tableId, address player, uint256 amount) external onlyBackend {
        require(tables[tableId].isActive, "Table not active");
        tables[tableId].players.push(player);
        emit PlayerJoined(tableId, player, amount);
    }

    // Função para atualizar o pote
    function updatePot(uint256 tableId, uint256 newPot) external onlyBackend {
        require(tables[tableId].isActive, "Table not active");
        tables[tableId].pot = newPot;
        emit PotUpdated(tableId, newPot);
    }

    // Função para finalizar a mesa e pagar o vencedor
    function finalizeTable(uint256 tableId, address winner, uint256 amount) external onlyBackend {
        require(tables[tableId].isActive, "Table not active");
        tables[tableId].isActive = false;
        emit WinnerPaid(tableId, winner, amount);
        emit TableClosed(tableId);
    }
}