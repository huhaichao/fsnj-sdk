package org.fsnj.crypto;

import org.bouncycastle.util.encoders.Hex;
import org.ethereum.crypto.ECKey;
import org.fsnj.utils.ByteUtil;
import org.fsnj.utils.HashUtil;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.regex.Pattern;

public class KeyTools {

    /**
     * The parameters of the secp256k1 curve that Bitcoin uses.
     */
    public static final ECDomainParameters CURVE;
    private static final X9ECParameters CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1");
    private static final KeyStore keystore = KeyStore.defaultKeyStore();
    private static final Pattern pattern = Pattern.compile("^(0x)?[0-9a-f]");


    static {
        CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(),
                CURVE_PARAMS.getH());
    }

    public static ECKey generateKeyPair() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        return new ECKey();
    }

    public static String getAddressFromPrivateKey(String privateKey){
        String publicKey = getPublicKeyFromPrivateKey(privateKey, true);
        return getAddressFromPublicKey(publicKey);
    }

    /**
     * @param privateKey hex string without 0x
     * @return
     */
    public static String getPublicKeyFromPrivateKey(String privateKey, boolean compressed) {
        byte[] pk = ByteUtil.hexStringToByteArray(privateKey);
        BigInteger bigInteger = new BigInteger(1, pk);
        ECKey ecKey = ECKey.fromPrivate(bigInteger);
        ECPoint point = ecKey.getPubKeyPoint();
        return ByteUtil.byteArrayToHexString(point.getEncoded(compressed));
    }

    public static String getAddressFromPublicKey(String publicKey) {
        ECKey ecKey = ECKey.fromPublicOnly(Hex.decode(publicKey));
        return Hex.toHexString(ecKey.getAddress());
    }

    public static byte[] getAddressFromPublicKey(byte[] publicKey) {
        return HashUtil.sha256(publicKey);
    }

    public static byte[] generateRandomBytes(int size) {
        byte[] bytes = new byte[size];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    public static String decryptPrivateKey(String file, String passphrase) throws Exception {
        return keystore.decryptPrivateKey(file, passphrase);
    }

    public static String encryptPrivateKey(String privateKey, String passphrase, KDFType type) throws Exception {
        return keystore.encryptPrivateKey(privateKey, passphrase, type);
    }
}
