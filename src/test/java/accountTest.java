import java.security.NoSuchAlgorithmException;

import org.bouncycastle.jce.interfaces.ECKey;
import org.fsnj.account.Account;
import org.junit.Test;
import org.spongycastle.util.encoders.Hex;

/**
 * @author zhijie.li
 * @date 2019/9/26
 **/
public class accountTest {


    @Test
    public void createAddress(){
        try {
            Account account = new Account("86e2ca92713dfd8ea6522e51b03072d261a5d9ab887d1093c7ffe30141eca03b");
            System.out.println(account.getAddress());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
