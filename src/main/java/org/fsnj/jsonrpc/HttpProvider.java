
package org.fsnj.jsonrpc;

import com.google.gson.JsonObject;
import org.fsnj.blockchain.AddressAllInfo;
import org.fsnj.blockchain.Asset;
import org.fsnj.blockchain.BlockTx;
import org.fsnj.blockchain.MakeSwap;
import org.fsnj.blockchain.MakeSwaps;
import org.fsnj.blockchain.StakeInfo;
import org.fsnj.blockchain.Ticket;
import org.fsnj.blockchain.TimeLockBalance;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.fsnj.transaction.Transaction;
import org.fsnj.utils.HexUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSONObject;

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

    /**
     * getTicketPrice
     *
     * @return
     * @throws IOException
     */
    public Rep<BigInteger> getTicketPrice() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_ticketPrice").params(new String[]{"latest"}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BigInteger>>() {
        }.getType();
        Rep<BigInteger> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * unlockAccount
     *
     * @return
     * @throws IOException
     */
    public Rep<String> unlockAccount(String account,String passWord) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("personal_unlockAccount").params(new Object[]{account,passWord}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * lockAccount
     *
     * @return
     * @throws IOException
     */
    public Rep<String> lockAccount(String account,String passWord) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("personal_lockAccount").params(new Object[]{account,passWord}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * sendTransaction
     *
     * @param from
     * @param to
     * @param value
     * @param gas
     * @param gasPrice
     * @return
     * @throws IOException
     */
    public Rep<String> sendTransaction(String from,String to,double value,double gas,double gasPrice,String password) throws IOException {

        unlockAccount(from,password);
        final JSONObject sendInfo = new JSONObject();
        sendInfo.put("from", from);
        sendInfo.put("to", to);
        sendInfo.put("value", HexUtil.doubleToHex(value,18));
        sendInfo.put("gas", HexUtil.doubleToHex(gas,18));
        sendInfo.put("gasPrice", HexUtil.doubleToHex(gasPrice,18));
        Req req = Req.builder().id("1").jsonrpc("2.0").method("eth_sendTransaction").params(new Object[]{sendInfo}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        lockAccount(from,password);
        return rep;
    }

    /**
     * createAccount
     *
     * @param passWord
     * @return
     * @throws IOException
     */
    public Rep<String> createAccount(String passWord) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("personal_newAccount").params(new Object[]{passWord}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }


    /**
     * getStakeInfo
     *
     * @return
     * @throws IOException
     */
    public Rep<StakeInfo> getStakeInfo() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getStakeInfo").params(new String[]{"latest"}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<StakeInfo>>() {
        }.getType();
        Rep<StakeInfo> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * buyTicket
     *
     * @param fromAddress
     * @return
     * @throws IOException
     */
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

    /**
     * allTickets
     *
     * @return
     * @throws IOException
     */
    public Rep<Ticket> allTickets() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_allTickets").params(new String[]{"latest"}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();

        JSONObject jsonObject = JSONObject.parseObject(resultString);

        JSONObject json = new JSONObject();
        json.put("tickets",jsonObject.getJSONObject("result"));

        jsonObject.put("result",json);

        Type type = new TypeToken<Rep<Ticket>>() {
        }.getType();
        Rep<Ticket> rep = gson.fromJson(jsonObject.toString(), type);
        return rep;
    }

    /**
     * allTicketsByAddress
     *
     * @param address
     * @param state
     * @return
     * @throws IOException
     */
    public Rep<Ticket> allTicketsByAddress(String address,String state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_allTicketsByAddress").params(new String[]{address,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        JSONObject jsonObject = JSONObject.parseObject(resultString);
        JSONObject json = new JSONObject();
        json.put("tickets",jsonObject.getJSONObject("result"));

        jsonObject.put("result",json);

        Type type = new TypeToken<Rep<Ticket>>() {
        }.getType();
        Rep<Ticket> rep = gson.fromJson(jsonObject.toString(), type);
        return rep;
    }

    /**
     * getTotalNumberOfTickets
     *
     * @return
     * @throws IOException
     */
    public Rep<Integer> getTotalNumberOfTickets() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_totalNumberOfTickets").params(new String[]{"latest"}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Integer>>() {
        }.getType();
        Rep<Integer> rep = gson.fromJson(resultString, type);

        return rep;
    }

    /**
     * getotalNumberOfTicketsByAddress
     *
     * @param address
     * @param state
     * @return
     * @throws IOException
     */
    public Rep<Integer> getotalNumberOfTicketsByAddress(String address,String state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_totalNumberOfTicketsByAddress").params(new String[]{address,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Integer>>() {
        }.getType();
        Rep<Integer> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * assetToTimeLock
     *
     * @param asset
     * @param from
     * @param to
     * @param start
     * @param end
     * @param value
     * @return
     * @throws IOException
     */
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

    /**
     * timeLockToTimeLock
     *
     * @param asset
     * @param from
     * @param to
     * @param start
     * @param end
     * @param value
     * @return
     * @throws IOException
     */
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

    /**
     * timeLockToTimeLock
     *
     * @param asset
     * @param from
     * @param to
     * @param start
     * @param end
     * @param value
     * @return
     * @throws IOException
     */
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

    /**
     * getTimeLockBalan
     *
     * @param asset
     * @param address
     * @param state
     * @return
     * @throws IOException
     */
    public Rep<TimeLockBalance> getTimeLockBalan(String asset, String address , String state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getTimeLockBalance").params(new Object[]{asset,address,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<TimeLockBalance>>() {
        }.getType();
        Rep<TimeLockBalance> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * getAllTimeLockBalances
     *
     * @param address
     * @param state
     * @return
     * @throws IOException
     */
    public Rep<Map<String,TimeLockBalance>> getAllTimeLockBalances(String address , String state) throws IOException {
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
    public Rep<String> getAddressByNotation(Integer notation , Object state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getAddressByNotation").params(new Object[]{notation,state}).build();
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
    public Rep<BigInteger> getBalance(String assetID ,String address, Object state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getBalance").params(new Object[]{assetID,address,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BigInteger>>() {
        }.getType();
        Rep<BigInteger> rep = gson.fromJson(resultString, type);
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
    public Rep<String> makeSwap(String from ,Long gas,Long  gasPrice ,Long nonce,
                                 String FromAssetID,String FromStartTime,String FromEndTime,
                                 String MinFromAmount,String ToAssetID,String ToStartTime,
                                 String ToEndTime,String MinToAmount,int SwapSize,String[] Targes) throws IOException {
        JSONObject param =  new JSONObject();
        param.put("from",from);
        if(gas!=null)param.put("gas",gas);
        if(gasPrice!=null)param.put("gasPrice",gasPrice);
        if(nonce!=null)param.put("nonce",nonce);
        param.put("FromAssetID",FromAssetID);
        param.put("FromStartTime",FromStartTime);
        param.put("FromEndTime",FromEndTime);
        param.put("MinFromAmount",MinFromAmount);
        param.put("ToStartTime",ToStartTime);
        param.put("ToStartTime",ToStartTime);
        param.put("SwapSize",SwapSize);
        param.put("targes", Targes);
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

    /**
     * Take the quantum swap order.(Account has been unlocked)
     * @param from
     * @param gas
     * @param gasPrice
     * @param nonce
     * @param swapID
     * @param size
     * @return
     * @throws IOException
     */
    public Rep<String> takeSwap(String from ,Long gas,Long gasPrice,Long nonce, String swapID,Integer size) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("from",from);
        param.addProperty("SwapID",swapID);
        param.addProperty("Size",size);
        if(gas!=null)param.addProperty("gas",gas);
        if(gasPrice!=null)param.addProperty("gasPrice",gasPrice);
        if(nonce!=null)param.addProperty("nonce",nonce);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_takeSwap").params(new Object[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Destroy Quantum swap order and Return Assets.(Account has been unlocked)
     * @param from
     * @param gas
     * @param gasPrice
     * @param nonce
     * @param swapID
     * @return
     * @throws IOException
     */
    public Rep<String> recallSwap(String from ,Long gas,Long gasPrice,Long nonce, String swapID) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("from",from);
        param.addProperty("SwapID",swapID);
        if(gas!=null)param.addProperty("gas",gas);
        if(gasPrice!=null)param.addProperty("gasPrice",gasPrice);
        if(nonce!=null)param.addProperty("nonce",nonce);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_recallSwap").params(new Object[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Create a multi swap order.(Account has been unlocked)
     * @param from
     * @param fromAssetID
     * @param toAssetID
     * @param minToAmount
     * @param minFromAmount
     * @param fromStartTime
     * @param fromEndTime
     * @param swapSize
     * @param targes
     * @return
     * @throws IOException
     */
    public Rep<String> makeMultiSwap(String from ,String[] fromAssetID,String[] toAssetID,String[] minToAmount,
                                     String[] minFromAmount,String[] fromStartTime,String[] fromEndTime,
                                     Integer swapSize,String[] targes) throws IOException {
        JSONObject param =  new JSONObject();
        param.put("from",from);
        param.put("FromAssetID",fromAssetID);
        param.put("ToAssetID",toAssetID);
        param.put("MinToAmount",minToAmount);
        param.put("MinFromAmount",minFromAmount);
        param.put("FromStartTime",fromStartTime);
        param.put("FromEndTime",fromEndTime);
        param.put("SwapSize",swapSize);
        param.put("Targes", targes);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_makeMultiSwap").params(new Object[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;

    }

    /**
     * Get the details of the multi MakeSwap.
     * @param hash
     * @param state
     * @return
     * @throws IOException
     */
    public Rep<MakeSwaps> getMultiSwap(String hash , Object state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getMultiSwap").params(new Object[]{hash,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<MakeSwaps>>() {
        }.getType();
        Rep<MakeSwaps> rep = gson.fromJson(resultString, type);
        return rep;
    }
    /**
     * Take the multi swap order.(Account has been unlocked)
     * @param from
     * @param gas
     * @param gasPrice
     * @param nonce
     * @param swapID
     * @param size
     * @return
     * @throws IOException
     */
    public Rep<String> takeMultiSwap(String from ,Long gas,Long gasPrice,Long nonce, String swapID,Integer size) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("from",from);
        param.addProperty("SwapID",swapID);
        param.addProperty("Size",size);
        if(gas!=null)param.addProperty("gas",gas);
        if(gasPrice!=null)param.addProperty("gasPrice",gasPrice);
        if(nonce!=null)param.addProperty("nonce",nonce);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_takeMultiSwap").params(new Object[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Destroy multi swap order and Return Assets.(Account has been unlocked)
     * @param from
     * @param gas
     * @param gasPrice
     * @param nonce
     * @param swapID
     * @return
     * @throws IOException
     */
    public Rep<String> recallMultiSwap(String from ,Long gas,Long gasPrice,Long nonce, String swapID) throws IOException {
        JsonObject param =  new JsonObject();
        param.addProperty("from",from);
        param.addProperty("SwapID",swapID);
        if(gas!=null)param.addProperty("gas",gas);
        if(gasPrice!=null)param.addProperty("gasPrice",gasPrice);
        if(nonce!=null)param.addProperty("nonce",nonce);
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_recallMultiSwap").params(new Object[]{param.toString()}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Return true if the ticket is purchased automatically.(Account has been unlocked)
     * @param state
     * @return
     * @throws IOException
     */
    public Rep<Boolean> isAutoBuyTicket(Object state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_isAutoBuyTicket").params(new Object[]{state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Boolean>>() {
        }.getType();
        Rep<Boolean> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Start buying tickets automatically.(Account has been unlocked)
     * @return
     * @throws IOException
     */
    public Rep<String> startAutoBuyTicket() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_startAutoBuyTicket").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Stop buying tickets automatically.(Account has been unlocked)
     * @return
     * @throws IOException
     */
    public Rep<String> stopAutoBuyTicket() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsntx_stopAutoBuyTicket").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * Return the tx and receipt of the transaction.
     * @param hash
     * @return
     * @throws IOException
     */
    public Rep<BlockTx> getTransactionAndReceipt(String hash) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_getTransactionAndReceipt").params(new String[]{hash}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockTx>>() {
        }.getType();
        Rep<BlockTx> rep = gson.fromJson(resultString, type);
        return rep;
    }


    /**
     * Returns all information about the address.
     * @param address
     * @return
     * @throws IOException
     */
    public Rep<AddressAllInfo> allInfoByAddress(String address,String state) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("fsn_allInfoByAddress").params(new String[]{address,state}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<AddressAllInfo>>() {
        }.getType();
        Rep<AddressAllInfo> rep = gson.fromJson(resultString, type);
        return rep;
    }

    /**
     * send hex
     * @param hex
     * @return txid
     * @throws IOException
     */
    public Rep<String> sendRawTransaction(String hex) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("eth_sendRawTransaction").params(new String[]{hex}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        log.info("fsn response "+resultString);
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        if (rep.getResult()==null){
            log.error("fsn response error ="+resultString);
        }
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
