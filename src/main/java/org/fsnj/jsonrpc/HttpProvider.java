
package org.fsnj.jsonrpc;

import com.google.gson.JsonObject;
import org.fsnj.blockchain.Asset;
import org.fsnj.blockchain.BlockList;
import org.fsnj.blockchain.Contract;
import org.fsnj.blockchain.MakeSwap;
import org.fsnj.blockchain.ShardingStructure;
import org.fsnj.blockchain.StakeInfo;
import org.fsnj.blockchain.Ticket;
import org.fsnj.blockchain.TimeLockBalance;
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
import java.util.Map;
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

    public Rep<Ticket> allTicketsByAddress(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_allTicketsByAddress").params(new String[]{address,"latest"}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Ticket>>() {
        }.getType();
        Rep<Ticket> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<Integer> getTotalNumberOfTickets() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_totalNumberOfTickets").params(new String[]{"latest"}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Integer>>() {
        }.getType();
        Rep<Integer> rep = gson.fromJson(resultString, type);

        return rep;
    }

    public Rep<Integer> getotalNumberOfTicketsByAddress(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_totalNumberOfTicketsByAddress").params(new String[]{address,"latest"}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Integer>>() {
        }.getType();
        Rep<Integer> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> assetToTimeLock(String asset,String from ,String to ,String start ,String end ,String value) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("asset",asset);
        param.addProperty("from",from);
        param.addProperty("to",to);
        param.addProperty("start",start);
        param.addProperty("end",end);
        param.addProperty("value",value);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_assetToTimeLock").params(new String[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> timeLockToTimeLock(String asset,String from ,String to ,String start ,String end ,String value) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("asset",asset);
        param.addProperty("from",from);
        param.addProperty("to",to);
        param.addProperty("start",start);
        param.addProperty("end",end);
        param.addProperty("value",value);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_timeLockToTimeLock").params(new String[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }


    public Rep<String> timeLockToAsset(String asset,String from ,String to ,String start ,String end ,String value) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("asset",asset);
        param.addProperty("from",from);
        param.addProperty("to",to);
        param.addProperty("start",start);
        param.addProperty("end",end);
        param.addProperty("value",value);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_timeLockToAsset").params(new String[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<TimeLockBalance> getTimeLockBalan(String asset, String address , Object state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getTimeLockBalance").params(new Object[]{asset,address,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<TimeLockBalance>>() {
        }.getType();
        Rep<TimeLockBalance> rep = gson.fromJson(resultString, type);
        return rep;
    }


    public Rep<Map<String,TimeLockBalance>> getAllTimeLockBalances(String address , Object state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getAllTimeLockBalances").params(new Object[]{address,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Map<String,TimeLockBalance>>>() {
        }.getType();
        Rep<Map<String,TimeLockBalance>> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Generate new asset.(Account has been unlocked)
     * @param from
     * @param name
     * @param symbol
     * @param decimals
     * @param total
     * @param canChange
     * @return
     * @throws IOException
     */
    public Rep<String> genAsset(String from ,String name,String symbol ,String decimals ,String total ,boolean canChange) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("name",name);
        param.addProperty("from",from);
        param.addProperty("symbol",symbol);
        param.addProperty("decimals",decimals);
        param.addProperty("total",total);
        param.addProperty("canChange",canChange);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_genAsset").params(new String[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Reduction the total amount of asset.(Account has been unlocked && "canChange" is true for the asset)
     * @param from
     * @param to
     * @param value
     * @param asset
     * @return
     * @throws IOException
     */
    public Rep<String> decAsset(String from ,String to,String value ,String asset) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("from",from);
        param.addProperty("to",to);
        param.addProperty("value",value);
        param.addProperty("asset",asset);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_decAsset").params(new String[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Increase the total amount of asset.(Account has been unlocked && "canChange" is true for the asset)
     * @param from
     * @param to
     * @param value
     * @param asset
     * @return
     * @throws IOException
     */
    public Rep<String> incAsset(String from ,String to,String value ,String asset) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("from",from);
        param.addProperty("to",to);
        param.addProperty("value",value);
        param.addProperty("asset",asset);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_incAsset").params(new String[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Send assets to other accounts.(Account has been unlocked)
     * @param from
     * @param to
     * @param value
     * @param asset
     * @return
     * @throws IOException
     */
    public Rep<String> sendAsset(String from ,String to,String value ,String asset) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("from",from);
        param.addProperty("to",to);
        param.addProperty("value",value);
        param.addProperty("asset",asset);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_sendAsset").params(new String[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Get all assets balances of account.
     * @param address
     * @param state
     * @return
     * @throws IOException
     */
    public Rep<Map<String,String>> getAllBalances(String address , Object state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getAllBalances").params(new Object[]{address,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Map<String,String>>>() {
        }.getType();
        Rep<Map<String,String>> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Create a notation for a account.
     * @param from
     * @param gas
     * @param gasPrice
     * @param nonce
     * @return
     * @throws IOException
     */
    public Rep<String> genNotation(String from,Long gas ,Long gasPrice,Long nonce) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("from",from);
        if(gas!=null)param.addProperty("gas",gas);
        if(gasPrice!=null)param.addProperty("gasPrice",gasPrice);
        if(nonce!=null)param.addProperty("nonce",nonce);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_genNotation").params(new String[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Get notation of account
     * @param address
     * @param state
     * @return
     * @throws IOException
     */
    public Rep<Long> getNotation(String address , Object state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getNotation").params(new Object[]{address,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Long>>() {
        }.getType();
        Rep<Long> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Return the address of the notation.
     * @param notation
     * @param state
     * @return
     * @throws IOException
     */
    public Rep<String> getNotation(long notation , Object state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getNotation").params(new Object[]{notation,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }


    /**
     * Get the asset information.
     * @param assetID
     * @param state
     * @return
     * @throws IOException
     */
    public Rep<Asset> getAsset(String assetID , Object state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getAsset").params(new Object[]{assetID,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Asset>>() {
        }.getType();
        Rep<Asset> rep = gson.fromJson(resultString, type);
        return rep;
    }


    /**
     * Get the balance of account.
     * @param assetID
     * @param state
     * @return
     * @throws IOException
     */
    public Rep<Long> getBalance(String assetID ,String address, Object state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getBalance").params(new Object[]{assetID,address,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Long>>() {
        }.getType();
        Rep<Long> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Create a quantum swap order.(Account has been unlocked)
     * @param from
     * @param gas
     * @param gasPrice
     * @param nonce
     * @param FromAssetID
     * @param FromStartTime
     * @param FromEndTime
     * @param MinFromAmount
     * @param ToAssetID
     * @param ToStartTime
     * @param ToEndTime
     * @param MinToAmount
     * @param SwapSize
     * @param Targes
     * @return
     * @throws IOException
     */
    public Rep<String> makeSwap(String from ,long gas,long  gasPrice ,long nonce,
                                 String FromAssetID,String FromStartTime,String FromEndTime,
                                 String MinFromAmount,String ToAssetID,String ToStartTime,
                                 String ToEndTime,String MinToAmount,int SwapSize,String[] Targes) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("from",from);
        param.addProperty("gas",gas);
        param.addProperty("nonce",nonce);
        param.addProperty("gasPrice",gasPrice);
        param.addProperty("FromAssetID",FromAssetID);
        param.addProperty("FromStartTime",FromStartTime);
        param.addProperty("FromEndTime",FromEndTime);
        param.addProperty("MinFromAmount",MinFromAmount);
        param.addProperty("ToStartTime",ToStartTime);
        param.addProperty("ToStartTime",ToStartTime);
        param.addProperty("SwapSize",SwapSize);
        //param.addProperty("Targes",Targes);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_makeSwap").params(new String[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Get the balance of account.
     * @param hash
     * @param state
     * @return
     * @throws IOException
     */
    public Rep<MakeSwap> getSwap(String hash , Object state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getSwap").params(new Object[]{hash,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<MakeSwap>>() {
        }.getType();
        Rep<MakeSwap> rep = gson.fromJson(resultString, type);
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
