
import java.io.IOException;
import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.ethereum.crypto.ECKey;
import org.fsnj.jsonrpc.HttpProvider;
import org.fsnj.transaction.Transaction;
import org.fsnj.transaction.TransactionFactory;
import org.fsnj.transaction.TxParams;
import org.junit.Test;
@Slf4j
public class TransactionTest {

    public static HttpProvider rpc = new HttpProvider("https://fsn.dev/api");
    @Test
    public  void transfer() throws Exception {
        String from = "0x1b5287fccb3aadc634f2212cdeebc133ca393022";
        String to = "0x180569e160fbd6181bea8cbea774e3534573ed40";

        String hex = transferFsn("86e2ca92713dfd8ea6522e55263072d261a5d9ab887d1093c7ffe30141eca03b", "",from, to, 8, 18,
            new BigDecimal("9E-14").doubleValue(), new BigDecimal("1E-9").doubleValue(), 0.001, "0x");

        String txid = rpc.sendRawTransaction(hex).getResult();
        log.info("sendRawTrabsaction txid = {}",txid);
    }

    /**
     *
     * @param hexPrivateKey 可以在AccountTest生成hex私钥
     * @param contracAddr
     * @param from
     * @param to
     * @param nonce
     * @param decimal
     * @param gasLimit
     * @param gasPrice
     * @param value
     * @param data
     * @return
     * @throws IOException
     */
    public String transferFsn(String hexPrivateKey,String contracAddr,String from,String to,int nonce,int decimal,double gasLimit,double gasPrice,double value,String data)

        throws IOException {
        //如果转fsn contracAddr 为"",如果是token,放入合约地址
        ECKey ecKey = ECKey.fromPrivate(Hex.decode(hexPrivateKey));
        TxParams txParams = TxParams.builder().ID(contracAddr)
            .fromAddr(from)
            .toAddr(to)
            .nonce(nonce)
            .decimal(decimal)
            .gasLimit(gasLimit)
            .gasPrice(gasPrice)
            .value(value)
            .data(data).build();
        Transaction transaction = TransactionFactory.buildTransaction(txParams,rpc);
        transaction.transfer(ecKey);
        System.out.println("签好名的hex: " + transaction.getSignature());
        return transaction.getSignature();
    }
}
