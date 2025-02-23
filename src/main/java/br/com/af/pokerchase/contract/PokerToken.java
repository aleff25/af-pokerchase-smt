package br.com.af.pokerchase.contract;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/hyperledger-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.2.
 */
@SuppressWarnings("rawtypes")
public class PokerToken extends Contract {
  public static final String BINARY = "Bin file was not provided";

  public static final String FUNC_APPROVE = "approve";

  public static final String FUNC_CREATETABLE = "createTable";

  public static final String FUNC_FINALIZETABLE = "finalizeTable";

  public static final String FUNC_INITIALIZE = "initialize";

  public static final String FUNC_JOINTABLE = "joinTable";

  public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

  public static final String FUNC_SETTABLESTATE = "setTableState";

  public static final String FUNC_TRANSFER = "transfer";

  public static final String FUNC_TRANSFERFROM = "transferFrom";

  public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

  public static final String FUNC_UPDATEPOT = "updatePot";

  public static final String FUNC_UPGRADETOANDCALL = "upgradeToAndCall";

  public static final String FUNC_ALLOWANCE = "allowance";

  public static final String FUNC_BACKENDSERVER = "backendServer";

  public static final String FUNC_BALANCEOF = "balanceOf";

  public static final String FUNC_DECIMALS = "decimals";

  public static final String FUNC_NAME = "name";

  public static final String FUNC_OWNER = "owner";

  public static final String FUNC_PROXIABLEUUID = "proxiableUUID";

  public static final String FUNC_SYMBOL = "symbol";

  public static final String FUNC_TABLES = "tables";

  public static final String FUNC_TOTALSUPPLY = "totalSupply";

  public static final String FUNC_UPGRADE_INTERFACE_VERSION = "UPGRADE_INTERFACE_VERSION";

  public static final Event APPROVAL_EVENT = new Event("Approval",
    Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
    }, new TypeReference<Address>(true) {
    }, new TypeReference<Uint256>() {
    }));
  ;

  public static final Event INITIALIZED_EVENT = new Event("Initialized",
    Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {
    }));
  ;

  public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
    Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
    }, new TypeReference<Address>(true) {
    }));
  ;

  public static final Event PLAYERJOINED_EVENT = new Event("PlayerJoined",
    Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
    }, new TypeReference<Address>(true) {
    }, new TypeReference<Uint256>() {
    }));
  ;

  public static final Event POTUPDATED_EVENT = new Event("PotUpdated",
    Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
    }, new TypeReference<Uint256>() {
    }));
  ;

  public static final Event STATECHANGED_EVENT = new Event("StateChanged",
    Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
    }, new TypeReference<Uint8>() {
    }));
  ;

  public static final Event TABLECLOSED_EVENT = new Event("TableClosed",
    Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
    }));
  ;

  public static final Event TABLECREATED_EVENT = new Event("TableCreated",
    Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
    }, new TypeReference<Uint256>() {
    }));
  ;

  public static final Event TRANSFER_EVENT = new Event("Transfer",
    Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
    }, new TypeReference<Address>(true) {
    }, new TypeReference<Uint256>() {
    }));
  ;

  public static final Event UPGRADED_EVENT = new Event("Upgraded",
    Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
    }));
  ;

  public static final Event WINNERPAID_EVENT = new Event("WinnerPaid",
    Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
    }, new TypeReference<Address>(true) {
    }, new TypeReference<Uint256>() {
    }));
  ;

  @Deprecated
  protected PokerToken(String contractAddress, Web3j web3j, Credentials credentials,
                       BigInteger gasPrice, BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected PokerToken(String contractAddress, Web3j web3j, Credentials credentials,
                       ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected PokerToken(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                       BigInteger gasPrice, BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected PokerToken(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                       ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static List<ApprovalEventResponse> getApprovalEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
    ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      ApprovalEventResponse typedResponse = new ApprovalEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
      typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static ApprovalEventResponse getApprovalEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
    ApprovalEventResponse typedResponse = new ApprovalEventResponse();
    typedResponse.log = log;
    typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
    typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }

  public static List<InitializedEventResponse> getInitializedEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(INITIALIZED_EVENT, transactionReceipt);
    ArrayList<InitializedEventResponse> responses = new ArrayList<InitializedEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      InitializedEventResponse typedResponse = new InitializedEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static InitializedEventResponse getInitializedEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(INITIALIZED_EVENT, log);
    InitializedEventResponse typedResponse = new InitializedEventResponse();
    typedResponse.log = log;
    typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }

  public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
    ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
    OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
    typedResponse.log = log;
    typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
    return typedResponse;
  }

  public static List<PlayerJoinedEventResponse> getPlayerJoinedEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PLAYERJOINED_EVENT, transactionReceipt);
    ArrayList<PlayerJoinedEventResponse> responses = new ArrayList<PlayerJoinedEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      PlayerJoinedEventResponse typedResponse = new PlayerJoinedEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.tableId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.player = (String) eventValues.getIndexedValues().get(1).getValue();
      typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static PlayerJoinedEventResponse getPlayerJoinedEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PLAYERJOINED_EVENT, log);
    PlayerJoinedEventResponse typedResponse = new PlayerJoinedEventResponse();
    typedResponse.log = log;
    typedResponse.tableId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.player = (String) eventValues.getIndexedValues().get(1).getValue();
    typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }

  public static List<PotUpdatedEventResponse> getPotUpdatedEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(POTUPDATED_EVENT, transactionReceipt);
    ArrayList<PotUpdatedEventResponse> responses = new ArrayList<PotUpdatedEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      PotUpdatedEventResponse typedResponse = new PotUpdatedEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.tableId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.newPot = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static PotUpdatedEventResponse getPotUpdatedEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(POTUPDATED_EVENT, log);
    PotUpdatedEventResponse typedResponse = new PotUpdatedEventResponse();
    typedResponse.log = log;
    typedResponse.tableId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.newPot = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }

  public static List<StateChangedEventResponse> getStateChangedEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(STATECHANGED_EVENT, transactionReceipt);
    ArrayList<StateChangedEventResponse> responses = new ArrayList<StateChangedEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      StateChangedEventResponse typedResponse = new StateChangedEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.tableId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.newState = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static StateChangedEventResponse getStateChangedEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(STATECHANGED_EVENT, log);
    StateChangedEventResponse typedResponse = new StateChangedEventResponse();
    typedResponse.log = log;
    typedResponse.tableId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.newState = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }

  public static List<TableClosedEventResponse> getTableClosedEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TABLECLOSED_EVENT, transactionReceipt);
    ArrayList<TableClosedEventResponse> responses = new ArrayList<TableClosedEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      TableClosedEventResponse typedResponse = new TableClosedEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.tableId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static TableClosedEventResponse getTableClosedEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TABLECLOSED_EVENT, log);
    TableClosedEventResponse typedResponse = new TableClosedEventResponse();
    typedResponse.log = log;
    typedResponse.tableId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
    return typedResponse;
  }

  public static List<TableCreatedEventResponse> getTableCreatedEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TABLECREATED_EVENT, transactionReceipt);
    ArrayList<TableCreatedEventResponse> responses = new ArrayList<TableCreatedEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      TableCreatedEventResponse typedResponse = new TableCreatedEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.tableId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.entryFee = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static TableCreatedEventResponse getTableCreatedEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TABLECREATED_EVENT, log);
    TableCreatedEventResponse typedResponse = new TableCreatedEventResponse();
    typedResponse.log = log;
    typedResponse.tableId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.entryFee = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }

  public static List<TransferEventResponse> getTransferEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
    ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      TransferEventResponse typedResponse = new TransferEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
      typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static TransferEventResponse getTransferEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
    TransferEventResponse typedResponse = new TransferEventResponse();
    typedResponse.log = log;
    typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
    typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }

  public static List<UpgradedEventResponse> getUpgradedEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(UPGRADED_EVENT, transactionReceipt);
    ArrayList<UpgradedEventResponse> responses = new ArrayList<UpgradedEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      UpgradedEventResponse typedResponse = new UpgradedEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.implementation = (String) eventValues.getIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static UpgradedEventResponse getUpgradedEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(UPGRADED_EVENT, log);
    UpgradedEventResponse typedResponse = new UpgradedEventResponse();
    typedResponse.log = log;
    typedResponse.implementation = (String) eventValues.getIndexedValues().get(0).getValue();
    return typedResponse;
  }

  public static List<WinnerPaidEventResponse> getWinnerPaidEvents(
    TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(WINNERPAID_EVENT, transactionReceipt);
    ArrayList<WinnerPaidEventResponse> responses = new ArrayList<WinnerPaidEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      WinnerPaidEventResponse typedResponse = new WinnerPaidEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.tableId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.winner = (String) eventValues.getIndexedValues().get(1).getValue();
      typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static WinnerPaidEventResponse getWinnerPaidEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(WINNERPAID_EVENT, log);
    WinnerPaidEventResponse typedResponse = new WinnerPaidEventResponse();
    typedResponse.log = log;
    typedResponse.tableId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.winner = (String) eventValues.getIndexedValues().get(1).getValue();
    typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }

  @Deprecated
  public static PokerToken load(String contractAddress, Web3j web3j, Credentials credentials,
                                BigInteger gasPrice, BigInteger gasLimit) {
    return new PokerToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static PokerToken load(String contractAddress, Web3j web3j,
                                TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
    return new PokerToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static PokerToken load(String contractAddress, Web3j web3j, Credentials credentials,
                                ContractGasProvider contractGasProvider) {
    return new PokerToken(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static PokerToken load(String contractAddress, Web3j web3j,
                                TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return new PokerToken(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public RemoteFunctionCall<TransactionReceipt> approve(String spender, BigInteger value) {
    final Function function = new Function(
      FUNC_APPROVE,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, spender),
        new org.web3j.abi.datatypes.generated.Uint256(value)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> createTable(BigInteger tableId,
                                                            BigInteger entryFee) {
    final Function function = new Function(
      FUNC_CREATETABLE,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tableId),
        new org.web3j.abi.datatypes.generated.Uint256(entryFee)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> finalizeTable(BigInteger tableId, String winner,
                                                              BigInteger amount) {
    final Function function = new Function(
      FUNC_FINALIZETABLE,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tableId),
        new org.web3j.abi.datatypes.Address(160, winner),
        new org.web3j.abi.datatypes.generated.Uint256(amount)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> initialize(String name, String symbol,
                                                           String _backendServer) {
    final Function function = new Function(
      FUNC_INITIALIZE,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name),
        new org.web3j.abi.datatypes.Utf8String(symbol),
        new org.web3j.abi.datatypes.Address(160, _backendServer)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> joinTable(BigInteger tableId, String player,
                                                          BigInteger amount) {
    final Function function = new Function(
      FUNC_JOINTABLE,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tableId),
        new org.web3j.abi.datatypes.Address(160, player),
        new org.web3j.abi.datatypes.generated.Uint256(amount)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
    final Function function = new Function(
      FUNC_RENOUNCEOWNERSHIP,
      Arrays.<Type>asList(),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> setTableState(BigInteger tableId,
                                                              BigInteger newState) {
    final Function function = new Function(
      FUNC_SETTABLESTATE,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tableId),
        new org.web3j.abi.datatypes.generated.Uint8(newState)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> transfer(String to, BigInteger value) {
    final Function function = new Function(
      FUNC_TRANSFER,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to),
        new org.web3j.abi.datatypes.generated.Uint256(value)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to,
                                                             BigInteger value) {
    final Function function = new Function(
      FUNC_TRANSFERFROM,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from),
        new org.web3j.abi.datatypes.Address(160, to),
        new org.web3j.abi.datatypes.generated.Uint256(value)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
    final Function function = new Function(
      FUNC_TRANSFEROWNERSHIP,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> updatePot(BigInteger tableId, BigInteger newPot) {
    final Function function = new Function(
      FUNC_UPDATEPOT,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tableId),
        new org.web3j.abi.datatypes.generated.Uint256(newPot)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getApprovalEventFromLog(log));
  }

  public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock,
                                                               DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
    return approvalEventFlowable(filter);
  }

  public Flowable<InitializedEventResponse> initializedEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getInitializedEventFromLog(log));
  }

  public Flowable<InitializedEventResponse> initializedEventFlowable(
    DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(INITIALIZED_EVENT));
    return initializedEventFlowable(filter);
  }

  public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
    EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
  }

  public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
    DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
    return ownershipTransferredEventFlowable(filter);
  }

  public Flowable<PlayerJoinedEventResponse> playerJoinedEventFlowable() {
    return web3j.ethLogFlowable(new EthFilter()).map(log -> getPlayerJoinedEventFromLog(log));
  }

  public Flowable<PlayerJoinedEventResponse> playerJoinedEventFlowable(
    DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(PLAYERJOINED_EVENT));
    return playerJoinedEventFlowable();
  }

  public Flowable<PotUpdatedEventResponse> potUpdatedEventFlowable() {
    return web3j.ethLogFlowable(new EthFilter()).map(log -> getPotUpdatedEventFromLog(log));
  }

  public Flowable<PotUpdatedEventResponse> potUpdatedEventFlowable(
    DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(POTUPDATED_EVENT));
    return potUpdatedEventFlowable();
  }

  public Flowable<StateChangedEventResponse> stateChangedEventFlowable() {
    return web3j.ethLogFlowable(new EthFilter()).map(log -> getStateChangedEventFromLog(log));
  }

  public Flowable<StateChangedEventResponse> stateChangedEventFlowable(
    DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(STATECHANGED_EVENT));
    return stateChangedEventFlowable();
  }

  public Flowable<TableClosedEventResponse> tableClosedEventFlowable() {
    return web3j.ethLogFlowable(new EthFilter()).map(log -> getTableClosedEventFromLog(log));
  }

  public Flowable<TableClosedEventResponse> tableClosedEventFlowable(
    DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(TABLECLOSED_EVENT));
    return tableClosedEventFlowable();
  }

  public Flowable<TableCreatedEventResponse> tableCreatedEventFlowable() {
    return web3j.ethLogFlowable(new EthFilter()).map(log -> getTableCreatedEventFromLog(log));
  }

  public Flowable<TableCreatedEventResponse> tableCreatedEventFlowable(
    DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(TABLECREATED_EVENT));
    return tableCreatedEventFlowable();
  }

  public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
  }

  public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock,
                                                               DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
    return transferEventFlowable(filter);
  }

  public Flowable<UpgradedEventResponse> upgradedEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getUpgradedEventFromLog(log));
  }

  public Flowable<UpgradedEventResponse> upgradedEventFlowable(DefaultBlockParameter startBlock,
                                                               DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(UPGRADED_EVENT));
    return upgradedEventFlowable(filter);
  }

  public RemoteFunctionCall<TransactionReceipt> upgradeToAndCall(String newImplementation,
                                                                 byte[] data, BigInteger weiValue) {
    final Function function = new Function(
      FUNC_UPGRADETOANDCALL,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newImplementation),
        new org.web3j.abi.datatypes.DynamicBytes(data)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function, weiValue);
  }

  public Flowable<WinnerPaidEventResponse> winnerPaidEventFlowable() {
    return web3j.ethLogFlowable(new EthFilter()).map(log -> getWinnerPaidEventFromLog(log));
  }

  public Flowable<WinnerPaidEventResponse> winnerPaidEventFlowable(
    DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(WINNERPAID_EVENT));
    return winnerPaidEventFlowable();
  }

  public RemoteFunctionCall<BigInteger> allowance(String owner, String spender) {
    final Function function = new Function(FUNC_ALLOWANCE,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner),
        new org.web3j.abi.datatypes.Address(160, spender)),
      Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
      }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<String> backendServer() {
    final Function function = new Function(FUNC_BACKENDSERVER,
      Arrays.<Type>asList(),
      Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
      }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<BigInteger> balanceOf(String account) {
    final Function function = new Function(FUNC_BALANCEOF,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account)),
      Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
      }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<BigInteger> decimals() {
    final Function function = new Function(FUNC_DECIMALS,
      Arrays.<Type>asList(),
      Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
      }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<String> name() {
    final Function function = new Function(FUNC_NAME,
      Arrays.<Type>asList(),
      Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
      }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<String> owner() {
    final Function function = new Function(FUNC_OWNER,
      Arrays.<Type>asList(),
      Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
      }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<byte[]> proxiableUUID() {
    final Function function = new Function(FUNC_PROXIABLEUUID,
      Arrays.<Type>asList(),
      Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
      }));
    return executeRemoteCallSingleValueReturn(function, byte[].class);
  }

  public RemoteFunctionCall<String> symbol() {
    final Function function = new Function(FUNC_SYMBOL,
      Arrays.<Type>asList(),
      Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
      }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<Tuple5<BigInteger, BigInteger, Boolean, BigInteger, BigInteger>> tables(
    BigInteger param0) {
    final Function function = new Function(FUNC_TABLES,
      Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
      Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
      }, new TypeReference<Uint256>() {
      }, new TypeReference<Bool>() {
      }, new TypeReference<Uint256>() {
      }, new TypeReference<Uint8>() {
      }));
    return new RemoteFunctionCall<Tuple5<BigInteger, BigInteger, Boolean, BigInteger, BigInteger>>(function,
      new Callable<Tuple5<BigInteger, BigInteger, Boolean, BigInteger, BigInteger>>() {
        @Override
        public Tuple5<BigInteger, BigInteger, Boolean, BigInteger, BigInteger> call()
          throws Exception {
          List<Type> results = executeCallMultipleValueReturn(function);
          return new Tuple5<BigInteger, BigInteger, Boolean, BigInteger, BigInteger>(
            (BigInteger) results.get(0).getValue(),
            (BigInteger) results.get(1).getValue(),
            (Boolean) results.get(2).getValue(),
            (BigInteger) results.get(3).getValue(),
            (BigInteger) results.get(4).getValue());
        }
      });
  }

  public RemoteFunctionCall<BigInteger> totalSupply() {
    final Function function = new Function(FUNC_TOTALSUPPLY,
      Arrays.<Type>asList(),
      Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
      }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<String> UPGRADE_INTERFACE_VERSION() {
    final Function function = new Function(FUNC_UPGRADE_INTERFACE_VERSION,
      Arrays.<Type>asList(),
      Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
      }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public static class ApprovalEventResponse extends BaseEventResponse {
    public String owner;

    public String spender;

    public BigInteger value;
  }

  public static class InitializedEventResponse extends BaseEventResponse {
    public BigInteger version;
  }

  public static class OwnershipTransferredEventResponse extends BaseEventResponse {
    public String previousOwner;

    public String newOwner;
  }

  public static class PlayerJoinedEventResponse extends BaseEventResponse {
    public BigInteger tableId;

    public String player;

    public BigInteger amount;
  }

  public static class PotUpdatedEventResponse extends BaseEventResponse {
    public BigInteger tableId;

    public BigInteger newPot;
  }

  public static class StateChangedEventResponse extends BaseEventResponse {
    public BigInteger tableId;

    public BigInteger newState;
  }

  public static class TableClosedEventResponse extends BaseEventResponse {
    public BigInteger tableId;
  }

  public static class TableCreatedEventResponse extends BaseEventResponse {
    public BigInteger tableId;

    public BigInteger entryFee;
  }

  public static class TransferEventResponse extends BaseEventResponse {
    public String from;

    public String to;

    public BigInteger value;
  }

  public static class UpgradedEventResponse extends BaseEventResponse {
    public String implementation;
  }

  public static class WinnerPaidEventResponse extends BaseEventResponse {
    public BigInteger tableId;

    public String winner;

    public BigInteger amount;
  }
}
