import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import org.fsnj.blockchain.AddressAllInfo;
import org.fsnj.blockchain.Asset;
import org.fsnj.blockchain.BlockTx;
import org.fsnj.blockchain.MakeSwap;
import org.fsnj.blockchain.MakeSwaps;
import org.fsnj.blockchain.StakeInfo;
import org.fsnj.blockchain.Ticket;
import org.fsnj.blockchain.TimeLockBalance;
import org.fsnj.jsonrpc.HttpProvider;
import org.fsnj.jsonrpc.Rep;
import org.junit.Test;

public class RpcTest {
    public static HttpProvider rpc = new HttpProvider("https://fsn.dev/api");

    //***************************fsn***************************
    @Test
    public void getTicketPrice() throws IOException {
        Rep<BigInteger> ticketPrice = rpc.getTicketPrice();
        System.out.println(ticketPrice.getResult());
    }

    @Test
    public void getStakeInfo() throws IOException {
        Rep<StakeInfo> stakeInfo = rpc.getStakeInfo();
        System.out.println(stakeInfo);
    }


    @Test
    public void buyTicket() throws IOException {
        Rep<String> stringRep = rpc.buyTicket("");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void allTickets() throws IOException {
        Rep<Ticket> ticketRep = rpc.allTickets();
        System.out.println(ticketRep.getResult());
    }

    @Test
    public void allTicketsByAddress() throws IOException {
        Rep<Ticket> allTikets = rpc.allTicketsByAddress("0x88817ef0545ca562530f9347b20138edecfd8e30", "latest");
        System.out.println(allTikets.getResult());
    }

    @Test
    public void getTotalNumberOfTickets() throws IOException {
        Rep<Integer> totalNumberOfTickets = rpc.getTotalNumberOfTickets();
        System.out.println(totalNumberOfTickets.getResult());
    }

    @Test
    public void getotalNumberOfTicketsByAddress() throws IOException {
        Rep<Integer> numberTicket = rpc.getotalNumberOfTicketsByAddress("0x88817ef0545ca562530f9347b20138edecfd8e30",
            "latest");
        System.out.println(numberTicket.getResult());
    }

    @Test
    public void getTimeLockBalance() throws IOException {
        Rep<TimeLockBalance> timeLockBalan = rpc.getTimeLockBalan(
            "0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
            "0x88817ef0545ca562530f9347b20138edecfd8e30", "latest");
        System.out.println(timeLockBalan.getResult());
    }

    @Test
    public void getAllTimeLockBalances() throws IOException {
        Rep<Map<String, TimeLockBalance>> allTimeLockBalancesRep = rpc.getAllTimeLockBalances(
            "0x88817ef0545ca562530f9347b20138edecfd8e30", "latest");
        System.out.println(allTimeLockBalancesRep.getResult());
    }

    @Test
    public void getAllBalances() throws IOException {
        Rep<Map<String, String>> allBalances = rpc.getAllBalances("0x88817ef0545ca562530f9347b20138edecfd8e30",
            "latest");
        System.out.println(allBalances.getResult());
    }

    @Test
    public void fsn_getNotation() throws IOException {
        Rep<Long> latest = rpc.getNotation("0x88817ef0545ca562530f9347b20138edecfd8e30", "latest");
        System.out.println(latest.getResult());
    }

    @Test
    public void getAddressByNotation() throws IOException{
        Rep<String> natationReq = rpc.getAddressByNotation(1, "latest");
        System.out.println(natationReq.getResult());
    }

    @Test
    public void getAsset() throws IOException {
        Rep<Asset> asset = rpc.getAsset("0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", "latest");
        System.out.println(asset.getResult());

    }

    @Test
    public void getBalance() throws IOException {
        Rep<BigInteger> balanceReq = rpc.getBalance(
            "0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
            "0x88817ef0545ca562530f9347b20138edecfd8e30", "latest");
        System.out.println(balanceReq.getResult());
    }

    @Test
    public void getSwap() throws Exception {
        Rep<MakeSwap> swapRep = rpc.getSwap("0x2bd4929f673c53bd4ff90ee640f8d3ccd6b756977893009ca6ef5561e54af535",
            "latest");
        System.out.println(swapRep.getResult());
    }

    @Test
    public void getTransactionAndReceipt() throws IOException{
        Rep<BlockTx> transactionAndReceipt = rpc.getTransactionAndReceipt("0x3925f643816f5a3660abee8d1cfabc7d322d580cc82d376897ee2b07142e3915");
        System.out.println(transactionAndReceipt.getResult());
    }

    @Test
    public void allInfoByAddress() throws IOException{
        Rep<AddressAllInfo> addressAllInfoRep = rpc.allInfoByAddress("0x88817ef0545ca562530f9347b20138edecfd8e30","latest");
        System.out.println(addressAllInfoRep.getResult());
    }

    @Test
    public void getMultiSwap() throws IOException{
        Rep<MakeSwaps> multiSwapReq = rpc.getMultiSwap(
            "0xf616d50440414ce2bfd2204ace993a34e53e58cc656c82b339be935670f2070e",
            "latest");
        System.out.println(multiSwapReq.getResult());
    }
    //***************************fsntx***************************

    @Test
    public void assetToTimeLock() throws IOException {
        Rep<String> stringRep = rpc.assetToTimeLock("0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", "0x5b15a29274c74cd7cae59cabf656873a0ea706ac", "0x5b15a29274c74cd7cae59cabf656873a0ea706ac", "0x5D6CD2FC", "0x5D945FFC", "0x1BC16D674EC80000");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void timeLockToTimeLock() throws IOException {
        Rep<String> stringRep = rpc.timeLockToTimeLock("0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", "0x5b15a29274c74cd7cae59cabf656873a0ea706ac", "0x5b15a29274c74cd7cae59cabf656873a0ea706ac", "0x5D6CD2FC", "0x5D945FFC", "0x1BC16D674EC80000");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void timeLockToAsset() throws IOException {
        Rep<String> stringRep = rpc.timeLockToAsset("0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", "0x5b15a29274c74cd7cae59cabf656873a0ea706ac", "0x5b15a29274c74cd7cae59cabf656873a0ea706ac", "0x5D6CD2FC", "0x5D945FFC", "0x1BC16D674EC80000");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void genAsset() throws IOException {
        Rep<String> stringRep = rpc.genAsset("0x5b15a29274c74cd7cae59cabf656873a0ea706ac", "FusionTest", "FT", "1",
            "0x200", true);
        System.out.println(stringRep.getResult());
    }

    @Test
    public void decAsset() throws IOException {
        Rep<String> stringRep = rpc.decAsset("0x5b15a29274c74cd7cae59cabf656873a0ea706ac",
            "0x5b15a29274c74cd7cae59cabf656873a0ea706ac", "0x10",
            "0x530566afdbc2e3e4192fb561a1032fba189571bd65abb823e0b0d0ae023dfbbd");

        System.out.println(stringRep.getResult());
    }

    @Test
    public void incAsset() throws IOException {
        Rep<String> stringRep = rpc.incAsset("0x5b15a29274c74cd7cae59cabf656873a0ea706ac",
            "0x5b15a29274c74cd7cae59cabf656873a0ea706ac", "0x10",
            "0x530566afdbc2e3e4192fb561a1032fba189571bd65abb823e0b0d0ae023dfbbd");
        System.out.println(stringRep);
    }

    @Test
    public void sendAsset() throws IOException {
        Rep<String> stringRep = rpc.sendAsset("0x5b15a29274c74cd7cae59cabf656873a0ea706ac",
            "0x07718f21f889b84451727ada8c65952a597b2e78", "0x10",
            "0x530566afdbc2e3e4192fb561a1032fba189571bd65abb823e0b0d0ae023dfbbd");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void genNotation() throws IOException {
        Rep<String> stringRep = rpc.genNotation("0x88817ef0545ca562530f9347b20138edecfd8e30", 210000L, 25000000000L,
            0L);
        System.out.println(stringRep.getResult());
    }

    @Test
    public void makeSwap() throws IOException {
        Rep<String> stringRep = rpc.makeSwap("0x5b15a29274c74cd7cae59cabf656873a0ea706ac", 420000L, 25000000000L, 0L,
            "0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", "0x5D6CE866", "0x5D9475B9",
            "0x29A2241AF62C0000", "0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", "", "",
            "0x1BC16D674EC80000", 2, new String[] {"0x07718f21f889b84451727ada8c65952a597b2e78"});
        System.out.println(stringRep.getResult());
    }

    @Test
    public void takeSwap() throws IOException {
        Rep<String> stringRep = rpc.takeSwap("0x07718f21f889b84451727ada8c65952a597b2e78", 210000L, 25000000000L, 0L,
            "0x3be968fd7368b73d2cd8ccac12fedb0a9a3b85b8b86f10bdb69b4a8e3285dbab", 1);
        System.out.println(stringRep.getResult());
    }

    @Test
    public void recallSwap() throws IOException {
        Rep<String> stringRep = rpc.recallSwap("0x5b15a29274c74cd7cae59cabf656873a0ea706ac", 210000L, 25000000000L, 0L,
            "0x3be968fd7368b73d2cd8ccac12fedb0a9a3b85b8b86f10bdb69b4a8e3285dbab");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void makeMultiSwap() throws IOException {
        Rep<String> stringRep = rpc.makeMultiSwap("0x3a1b3b81ed061581558a81f11d63e03129347437",
            new String[] {"0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"},
            new String[] {"0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"},
            new String[] {"0x1BC16D674EC80000"}, new String[] {"0x1BC16D674EC80000"}, new String[] {"0x5D6CE866"},
            new String[] {"0x5D6CE866"}, 2, new String[] {});
        System.out.println(stringRep.getResult());
    }

    @Test
    public void takeMultiSwap() throws IOException {
        Rep<String> stringRep = rpc.takeMultiSwap("0xb49edfcd6ab3dac4cc908f31fc4b3f7772773113", 210000L, 25000000000L,
            0L, "0xac05ff3d0d7ad5440eba0ee751ba19f876105b790dec63051c4db0b434cefa47", 1);
        System.out.println(stringRep.getResult());

    }

    @Test
    public void recallMultiSwap() throws IOException{
        Rep<String> stringRep = rpc.recallMultiSwap("0x3a1b3b81ed061581558a81f11d63e03129347437", 210000L, 25000000000L,
            0L, "0xac05ff3d0d7ad5440eba0ee751ba19f876105b790dec63051c4db0b434cefa47");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void isAutoBuyTicket() throws IOException{
        Rep<Boolean> autoTicket = rpc.isAutoBuyTicket("latest");
        System.out.println(autoTicket.getResult());
    }

    @Test
    public void startAutoBuyTicket() throws IOException{
        Rep<String> stringRep = rpc.startAutoBuyTicket();
        System.out.println(stringRep.getResult());
    }

    @Test
    public void stopAutoBuyTicket() throws IOException{
        Rep<String> stringRep = rpc.stopAutoBuyTicket();
        System.out.println(stringRep.getResult());
    }

    @Test
    public void unlockAccount() throws IOException{
        Rep<String> stringRep = rpc.unlockAccount("0x88817ef0545ca562530f9347b20138edecfd8e30","123");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void lockAccount() throws IOException{
        Rep<String> stringRep = rpc.lockAccount("0x88817ef0545ca562530f9347b20138edecfd8e30","123");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void createAccount() throws IOException{
        Rep<String> account = rpc.createAccount("123");
        System.out.println(account.getResult());
    }

    @Test
    public void sendTransaction() throws IOException{
        Rep<String> stringRep = rpc.sendTransaction("0x88817ef0545ca562530f9347b20138edecfd8e30", "0x88817ef0545ca562530f9347b20138edecfd8e30", 0.1, 0.000000000021, 0.0000000000052,"123456");
        System.out.println(stringRep.getResult());
    }
}