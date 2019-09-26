import java.security.NoSuchAlgorithmException;

import org.fsnj.account.Account;
import org.junit.Test;

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
