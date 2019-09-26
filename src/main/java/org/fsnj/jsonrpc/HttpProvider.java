
package org.fsnj.jsonrpc;

import com.google.gson.JsonObject;
import org.fsnj.blockchain.BlockList;
import org.fsnj.blockchain.Contract;
import org.fsnj.blockchain.ShardingStructure;
import org.fsnj.blockchain.StakeInfo;
import org.fsnj.blockchain.Ticket;
import org.fsnj.blockchain.TxBlock;
import org.fsnj.transaction.Transaction;
import org.fsnj.transaction.TransactionPayload;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.fsnj.blockchain.BlockchainInfo;
import org.fsnj.blockchain.TransactionList;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
@Slf4j
public class HttpProvider {

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String url;

    public HttpProvider(String url) {
        this.url = url;
    }


    public Rep<Long> getTicketPrice() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_ticketPrice").params(new String[]{"latest"}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Long>>() {
        }.getType();
        Rep<Long> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<StakeInfo> getStakeInfo() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getStakeInfo").params(new String[]{"latest"}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<StakeInfo>>() {
        }.getType();
        Rep<StakeInfo> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> buyTicket(String fromAddress) throws IOException {
        JsonObject param =  new JsonObject();param.addProperty("from",fromAddress);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_buyTicket").params(new String[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<Ticket> allTickets() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_allTickets").params(new String[]{"latest"}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Ticket>>() {
        }.getType();
        Rep<Ticket> rep = gson.fromJson(resultString, type);
        return rep;
    }


    public Rep<String> allTicketsByAddress(String fromAddress) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("from",fromAddress);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_allTicketsByAddress").
            params(new String[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    //Blockchain-related methods
    public Rep<String> getNetworkId() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNetworkId").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);

        return rep;
    }

    public Rep<BlockchainInfo> getBlockchainInfo() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetBlockchainInfo").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockchainInfo>>() {
        }.getType();
        Rep<BlockchainInfo> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<ShardingStructure> getShardingStructure() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetShardingStructure").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<ShardingStructure>>() {
        }.getType();
        Rep<ShardingStructure> rep = gson.fromJson(resultString, type);
        return rep;

    }

    public Rep<BlockList> getDSBlockListing(int pageNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("DSBlockListing").params(new Integer[]{pageNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<BlockList> getTxBlockListing(int pageNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("TxBlockListing").params(new Integer[]{pageNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> getNumDSBlocks() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumDSBlocks").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<Transaction> getTransaction32(String hash) throws Exception {
        Rep<Transaction> rep = getTransaction(hash);
        rep.getResult().setToAddr(rep.getResult().getToAddr());
        return rep;
    }

    public Rep<Double> getDSBlockRate() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetDSBlockRate").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Double>>() {
        }.getType();
        Rep<Double> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<BlockList> getDSBlockListing() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("DSBlockListing").params(new Object[]{1}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<TxBlock> getTxBlock(String blockNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTxBlock").params(new String[]{blockNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<TxBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<TxBlock>>() {
        }.getType());
        return rep;
    }

    public Rep<String> getNumTxBlocks() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumTxBlocks").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<Double> getTxBlockRate() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTxBlockRate").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Double>>() {
        }.getType();
        Rep<Double> rep = gson.fromJson(resultString, type);
        return rep;
    }
    public Rep<TxBlock> getLatestTxBlock() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetLatestTxBlock").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<TxBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<TxBlock>>() {
        }.getType());
        return rep;
    }

    public Rep<String> getNumTransactions() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumTransactions").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<Integer> getTransactionRate() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTransactionRate").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Integer>>() {
        }.getType();
        Rep<Integer> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> getCurrentMiniEpoch() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetCurrentMiniEpoch").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> getCurrentDSEpoch() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetCurrentDSEpoch").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<Integer> getPrevDifficulty() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetPrevDifficulty").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Integer>>() {
        }.getType();
        Rep<Integer> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<Integer> getPrevDSDifficulty() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetPrevDSDifficulty").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Integer>>() {
        }.getType();
        Rep<Integer> rep = gson.fromJson(resultString, type);
        return rep;
    }

    //Account-related methods
    public Rep<BalanceResult> getBalance(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetBalance").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        System.out.println(resultString);
        Type type = new TypeToken<Rep<BalanceResult>>() {
        }.getType();
        Rep<BalanceResult> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            BalanceResult balanceResult = new BalanceResult();
            balanceResult.setBalance("0");
            balanceResult.setNonce("0");
            rep.setResult(balanceResult);
        }
        return rep;
    }

    //Contract-related methods todo need test
    public Rep<ContractResult> getSmartContractCode(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractCode").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<ContractResult>>() {
        }.getType();
        Rep<ContractResult> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<List<Contract>> getSmartContracts(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContracts").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<Contract>>>() {
        }.getType();
        Rep<List<Contract>> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> getContractAddressFromTransactionID(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetContractAddressFromTransactionID").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<List<Contract.State>> getSmartContractState(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractState").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<Contract.State>>>() {
        }.getType();
        Rep<List<Contract.State>> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<List<Contract.State>> getSmartContractInit(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractInit").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<Contract.State>>>() {
        }.getType();
        Rep<List<Contract.State>> rep = gson.fromJson(resultString, type);
        return rep;
    }

    //Transaction-related methods
    public Rep<CreateTxResult> createTransaction(TransactionPayload payload) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("CreateTransaction").params(new Object[]{payload}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        log.info("zil response "+resultString);
        Type type = new TypeToken<Rep<CreateTxResult>>() {
        }.getType();
        Rep<CreateTxResult> rep = gson.fromJson(resultString, type);
        if (rep.getResult()==null){
            log.error("zil response error ="+resultString);
        }
        return rep;
    }

    public Rep<String> getMinimumGasPrice() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetMinimumGasPrice").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }


    public Rep<Transaction> getTransaction(String hash) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTransaction").params(new String[]{hash}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Transaction>>() {
        }.getType();
        Rep<Transaction> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public String getTransactionF(String hash) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTransaction").params(new String[]{hash}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        return resultString;
    }


    public Rep<TransactionList> getRecentTransactions() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetRecentTransactions").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<TransactionList>>() {
        }.getType();
        Rep<TransactionList> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<List<List<String>>> getTransactionsForTxBlock(String blockNum) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTransactionsForTxBlock").params(new String[]{blockNum}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<List<String>>>>() {
        }.getType();
        Rep<List<List<String>>> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> getNumTxnsTxEpoch() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumTxnsTxEpoch").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> getNumTxnsDSEpoch() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumTxnsDSEpoch").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }


    private Request buildRequest(Req req) throws MalformedURLException {
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));
        return new Request.Builder()
                .post(body)
                .url(new URL(this.url))
                .build();
    }

    @Data
    public static class BalanceResult {
        private String balance;
        private String nonce;
    }

    @Data
    public static class ContractResult {
        private String code;
    }

    @Data
    public static class CreateTxResult {
        private String Info;
        private String TranID;

        @Override
        public String toString() {
            return "CreateTxResult{" +
                    "Info='" + Info + '\'' +
                    ", TranID='" + TranID + '\'' +
                    '}';
        }
    }
}
