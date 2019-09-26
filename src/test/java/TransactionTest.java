
import java.io.IOException;

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

    @Test
    public  void transfer() throws Exception {
        ECKey ecKey = ECKey.fromPrivate(Hex.decode("d3985ac48d96f6d47af9433284c19538b4bf67fe6c7ff20184ed05f76d6fc30c"));
        String from = "0x180569e160fbd6181bea8cbea774e3534573ed40";
        String to = "0x180569e160fbd6181bea8cbea774e3534573ed40";
        //如果转token,此处填合约地址
        TxParams txParams = TxParams.builder().ID("")
            .fromAddr(from)
            .toAddr(to)
            .nonce("9")
            .decimal(18)
            .gasLimit(0.000000000000021)
            .gasPrice(0.00000005d)
            .value(0.1)
            .data("0x").build();
        Transaction transaction = TransactionFactory.buildTransaction(txParams,new HttpProvider("https://fsn.dev/api"));
        transaction.transfer(ecKey);
        String txid = TransactionFactory.sendRawTransaction(transaction);
        log.info("sendRawTrabsaction txid = {}",txid);
    }
}
