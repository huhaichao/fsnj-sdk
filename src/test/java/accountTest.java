import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.ethereum.crypto.ECKey;
import org.fsnj.account.Account;
import org.junit.Test;

public class accountTest {

    /**
     * 根据hex私钥生成离线地址
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void createAddress() throws NoSuchAlgorithmException {
        Account account = new Account("d3985ac48d96f6d47af9433284c19538b4bf67fe6c7ff20184ed05f76d6fc30c");
        System.out.println("priKey: " + account.getPrivateKey().toLowerCase());
        System.out.println("prubey: " + account.getPublicKey().toLowerCase());
        System.out.println("address: " + account.getAddress());
    }

    /**
     *随机生成公私钥以及地址
     */
    @Test
    public void createPrivaKey(){
        ECKey ecKey = new ECKey();
        System.out.println("priKey: " + Hex.encodeHexString(ecKey.getPrivKeyBytes()));
        System.out.println("pubKey: " + Hex.encodeHexString(ecKey.getPubKey()));
        System.out.println("address: " + Hex.encodeHexString(ecKey.getAddress()));
    }
}
