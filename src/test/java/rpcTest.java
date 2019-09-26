import java.io.IOException;

import org.fsnj.jsonrpc.HttpProvider;
import org.fsnj.jsonrpc.Rep;
import org.junit.Test;

/**
 * @author zhijie.li
 * @date 2019/9/26
 **/
public class rpcTest {
    private static HttpProvider rpc = new HttpProvider("http://47.52.229.70:8881");

    @Test
    public void ss() throws IOException {
        Rep<Long> ticketPrice = rpc.getTicketPrice();
        System.out.println(ticketPrice.getResult());
    }
}
