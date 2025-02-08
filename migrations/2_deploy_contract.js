const PokerTournament = artifacts.require("PokerTournament");
const VRFCoordinator = "0x8103B0A8A00be2DDC778e6e7eaa21791Cd364625"; // Endereço do Coordinator na Sepolia

module.exports = async function (deployer, network, accounts) {
    const subscriptionId = 1234; // ID da sua subscription no Chainlink

    await deployer.deploy(
        PokerTournament,
        VRFCoordinator,
        subscriptionId,
        "0x474e34a077df58807dbe9c96d3c009b23b3c6d0cce433e59bbf5b34f823bc56c" // Key Hash
    );

    const instance = await PokerTournament.deployed();
    console.log("Contract deployed at:", instance.address);
};