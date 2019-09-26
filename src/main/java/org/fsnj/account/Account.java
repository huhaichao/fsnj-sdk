package org.fsnj.account;

import org.bouncycastle.util.encoders.Hex;
import org.ethereum.crypto.ECKey;
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

    /**
     * 创建地址
     *
     * @param privateKey hex格式私钥
     * @throws NoSuchAlgorithmException
     */
    public Account(String privateKey) throws NoSuchAlgorithmException {
        this.ecKey = ECKey.fromPrivate(Hex.decode(privateKey));
        this.address =Hex.toHexString(ecKey.getAddress());
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

}
