
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
        ECKey ecKey = ECKey.fromPrivate(Hex.decode(""));
        String from = "";
        String to = "";
        TxParams txParams = TxParams.builder().ID("")
            .fromAddr(from)
            .toAddr(to)
            .gasLimit("")
            .gasPrice("")
            .value("")
            .data("").build();
        Transaction transaction = TransactionFactory.buildTransaction(txParams,new HttpProvider(""));
        transaction.transfer(ecKey);
        String txid = TransactionFactory.sendRawTransaction(transaction);
        log.info("sendRawTrabsaction txid = {}",txid);
    }
}
