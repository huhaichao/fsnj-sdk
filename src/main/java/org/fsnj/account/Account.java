package org.fsnj.account;

import org.bouncycastle.util.encoders.Hex;
import org.ethereum.crypto.ECKey;
import org.fsnj.crypto.KDFType;
import org.fsnj.crypto.KeyTools;
import org.fsnj.utils.ByteUtil;
import org.fsnj.utils.HashUtil;
import org.fsnj.utils.Validation;
import lombok.Data;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

@Data
public class Account {

    private ECKey ecKey;

    private String address;

    public Account(ECKey ecKey) throws NoSuchAlgorithmException {
        this.ecKey = ecKey;
        this.address = Hex.toHexString(ecKey.getAddress());
    }

    public Account(String privateKey) throws NoSuchAlgorithmException {
        this.ecKey = ECKey.fromPrivate(Hex.decode(privateKey));
        this.address =Hex.toHexString(ecKey.getAddress());
    }

    ;

    public static Account fromFile(String file, String passphrase) throws Exception {
        String privateKey = KeyTools.decryptPrivateKey(file, passphrase);
        return new Account(privateKey);
    }

    public String toFile(String privateKey, String passphrase, KDFType type) throws Exception {
        return KeyTools.encryptPrivateKey(privateKey, passphrase, type);
    }

    public String getPublicKey() {
        return ByteUtil.byteArrayToHexString(this.ecKey.getPubKey());
    }

    public String getPrivateKey() {
        return ByteUtil.byteArrayToHexString(this.ecKey.getPrivKeyBytes());
    }

    public static String toCheckSumAddress(String address)  {
        if (!Validation.isAddress(address)) {
            throw new RuntimeException("not a valid base 16 address");
        }
        address = address.toLowerCase().replace("0x", "");
        String hash = ByteUtil.byteArrayToHexString(HashUtil.sha256(ByteUtil.hexStringToByteArray(address)));
        StringBuilder ret = new StringBuilder("0x");
        BigInteger v = new BigInteger(ByteUtil.hexStringToByteArray(hash));
        for (int i = 0; i < address.length(); i++) {
            if ("1234567890".indexOf(address.charAt(i)) != -1) {
                ret.append(address.charAt(i));
            } else {
                BigInteger checker = v.and(BigInteger.valueOf(2l).pow(255 - 6 * i));
                ret.append(checker.compareTo(BigInteger.valueOf(1l)) < 0 ? String.valueOf(address.charAt(i)).toLowerCase() : String.valueOf(address.charAt(i)).toUpperCase());
            }
        }
        return ret.toString();
    }

    public  static  void main(String[] args) throws NoSuchAlgorithmException {
        Account account = new Account("86e2ca92713dfd8ea6522e51b03072d261a5d9ab887d1093c7ffe30141eca03b");

        System.out.println(account.address);
        System.out.println(account.getPrivateKey());
        System.out.println(account.getPublicKey());
    }
}
