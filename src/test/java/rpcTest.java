import java.io.IOException;
import java.math.BigInteger;

import org.fsnj.blockchain.StakeInfo;
import org.fsnj.blockchain.Ticket;
import org.fsnj.jsonrpc.HttpProvider;
import org.fsnj.jsonrpc.Rep;
import org.junit.Test;

/**
 * @author zhijie.li
 * @date 2019/9/26
 **/
public class rpcTest {
    private static HttpProvider rpc = new HttpProvider("https://fsn.dev/api");

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
}
