import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import org.fsnj.blockchain.Asset;
import org.fsnj.blockchain.MakeSwap;
import org.fsnj.blockchain.StakeInfo;
import org.fsnj.blockchain.Ticket;
import org.fsnj.blockchain.TimeLockBalance;
import org.fsnj.jsonrpc.HttpProvider;
import org.fsnj.jsonrpc.Rep;
import org.junit.Test;

/**
 * @author zhijie.li
 * @date 2019/9/26
 **/
public class rpcTest {
    private static HttpProvider rpc = new HttpProvider("https://fsn.dev/api");

    //***************************fsn***************************
    @Test
    public void getTicketPrice() throws IOException {
        Rep<BigInteger> ticketPrice = rpc.getTicketPrice();
        System.out.println(ticketPrice.getResult());
    }
    
    @Test
    public void getStakeInfo() throws IOException{
        Rep<StakeInfo> stakeInfo = rpc.getStakeInfo();
        System.out.println(stakeInfo);
    }

    //TODO 解锁
    @Test
    public void buyTicket()  throws IOException{
        Rep<String> stringRep = rpc.buyTicket("");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void allTickets() throws IOException{
        Rep<Ticket> ticketRep = rpc.allTickets();
        System.out.println(ticketRep.getResult());
    }

    @Test
    public void allTicketsByAddress() throws IOException{
        Rep<Ticket> allTikets = rpc.allTicketsByAddress("0x88817ef0545ca562530f9347b20138edecfd8e30", "latest");
        System.out.println(allTikets.getResult());
    }

    @Test
    public void getTotalNumberOfTickets() throws IOException{
        Rep<Integer> totalNumberOfTickets = rpc.getTotalNumberOfTickets();
        System.out.println(totalNumberOfTickets.getResult());
    }

    @Test
    public void getotalNumberOfTicketsByAddress() throws IOException{
        Rep<Integer> numberTicket = rpc.getotalNumberOfTicketsByAddress("0x88817ef0545ca562530f9347b20138edecfd8e30",
            "latest");
        System.out.println(numberTicket.getResult());
    }

    @Test
    public void getTimeLockBalance() throws IOException{
        Rep<TimeLockBalance> timeLockBalan = rpc.getTimeLockBalan("0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", "0x88817ef0545ca562530f9347b20138edecfd8e30", "latest");
        System.out.println(timeLockBalan.getResult());
    }

    @Test
    public void getAllTimeLockBalances() throws IOException{
        Rep<Map<String, TimeLockBalance>> allTimeLockBalancesRep = rpc.getAllTimeLockBalances(
            "0x88817ef0545ca562530f9347b20138edecfd8e30", "latest");
        System.out.println(allTimeLockBalancesRep.getResult());
    }


    @Test
    public void getAllBalances() throws IOException{
        Rep<Map<String, String>> allBalances = rpc.getAllBalances("0x88817ef0545ca562530f9347b20138edecfd8e30",
            "latest");
        System.out.println(allBalances.getResult());
    }

    @Test
    public void fsn_getNotation() throws IOException{
        Rep<Long> latest = rpc.getNotation("0x88817ef0545ca562530f9347b20138edecfd8e30", "latest");
        System.out.println(latest.getResult());
    }

    //TODO 漏了一个
    //@Test
    //public void getAddressByNotation() throws IOException{
    //    rpc.getAddressByNotation();
    //}

    @Test
    public void getAsset() throws IOException{
        Rep<Asset> asset = rpc.getAsset("0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff","latest");
        System.out.println(asset.getResult());

    }

    @Test
    public void getBalance() throws IOException{
        Rep<BigInteger> balanceReq = rpc.getBalance("0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
            "0x88817ef0545ca562530f9347b20138edecfd8e30", "latest");
        System.out.println(balanceReq.getResult());
    }

    @Test
    public void getSwap() throws Exception{
        Rep<MakeSwap> swapRep = rpc.getSwap("0x2bd4929f673c53bd4ff90ee640f8d3ccd6b756977893009ca6ef5561e54af535",
            "latest");
        System.out.println(swapRep.getResult());
    }
    //***************************fsntx***************************

    //TODO 测试锁定资产
    @Test
    public void assetToTimeLock() throws IOException{
        Rep<String> stringRep = rpc.assetToTimeLock("","","","","","");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void timeLockToTimeLock() throws IOException{
        Rep<String> stringRep = rpc.timeLockToTimeLock("", "", "", "", "", "");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void timeLockToAsset() throws IOException{
        Rep<String> stringRep = rpc.timeLockToAsset("", "", "", "", "", "");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void genAsset() throws IOException{
        Rep<String> stringRep = rpc.genAsset("0x5b15a29274c74cd7cae59cabf656873a0ea706ac", "FusionTest", "FT", "1",
            "0x200", true);
        System.out.println(stringRep.getResult());
    }

    @Test
    public void decAsset() throws IOException{
        Rep<String> stringRep = rpc.decAsset("0x5b15a29274c74cd7cae59cabf656873a0ea706ac","0x5b15a29274c74cd7cae59cabf656873a0ea706ac","0x10","0x530566afdbc2e3e4192fb561a1032fba189571bd65abb823e0b0d0ae023dfbbd");

        System.out.println(stringRep.getResult());
    }

    @Test
    public void incAsset() throws IOException{
        Rep<String> stringRep = rpc.incAsset("0x5b15a29274c74cd7cae59cabf656873a0ea706ac", "0x5b15a29274c74cd7cae59cabf656873a0ea706ac", "0x10", "0x530566afdbc2e3e4192fb561a1032fba189571bd65abb823e0b0d0ae023dfbbd");
        System.out.println(stringRep);
    }

    @Test
    public void sendAsset() throws IOException{
        Rep<String> stringRep = rpc.sendAsset("0x5b15a29274c74cd7cae59cabf656873a0ea706ac",
            "0x07718f21f889b84451727ada8c65952a597b2e78", "0x10",
            "0x530566afdbc2e3e4192fb561a1032fba189571bd65abb823e0b0d0ae023dfbbd");
        System.out.println(stringRep.getResult());
    }

    @Test
    public void genNotation() throws IOException{
        Rep<String> stringRep = rpc.genNotation("0x88817ef0545ca562530f9347b20138edecfd8e30",210000L,25000000000L,0L);
        System.out.println(stringRep.getResult());
    }

    @Test
    public void makeSwap() throws IOException{
        Rep<String> stringRep = rpc.makeSwap("0x5b15a29274c74cd7cae59cabf656873a0ea706ac",420000L,25000000000L,0L,"0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff","0x5D6CE866","0x5D9475B9","0x29A2241AF62C0000","0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff","","","0x1BC16D674EC80000",2,new String[]{"0x07718f21f889b84451727ada8c65952a597b2e78"});
        System.out.println(stringRep.getResult());
    }

    //@Test
    //public void takeSwap() throws IOException{
    //    rpc.takeSwap();
    //}

}
