import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.ethereum.crypto.ECKey;
import org.fsnj.account.Account;
import org.fsnj.account.Wallet;
import org.junit.Assert;
import org.junit.Test;

public class AccountTest {

    /**
     * 随机生成助记词
     */
    @Test
    public void createSeed(){
       String seed =  Wallet.generateSeed();
       System.out.println(seed);
    }

    /**
     * 根据助记词生成地址
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void createAccountBySeed() throws NoSuchAlgorithmException {
        String seed = "parrot total weird tower warm dog thought toe when monitor glass rifle mistake cool shed";
        Wallet wallet = new Wallet();
        Account account = wallet.createAccount(seed);
        System.out.println(account.getPrivateKey());
        Assert.assertEquals("0x"+account.getAddress(),"0xde2a120f7abc67c6540c2f297981e2edc98bcb10");
    }

    /**
     * 根据hex私钥生成离线地址
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void createAddress() throws NoSuchAlgorithmException {
        Account account = new Account("d3985ac48d96f6d47af9433284c19538b4bf67fe6c7ff20184ed05f76d6fc30c");
        System.out.println("priKey: " + account.getPrivateKey().toLowerCase());
        System.out.println("prubey: " + account.getPublicKey().toLowerCase());
        System.out.println("address: 0x" + account.getAddress());
    }

    /**
     *随机生成公私钥以及地址
     */
    @Test
    public void createPrivaKey(){
        ECKey ecKey = new ECKey();
        System.out.println("priKey: " + Hex.encodeHexString(ecKey.getPrivKeyBytes()));
        System.out.println("pubKey: " + Hex.encodeHexString(ecKey.getPubKey()));
        System.out.println("address: 0x" + Hex.encodeHexString(ecKey.getAddress()));
    }
}
