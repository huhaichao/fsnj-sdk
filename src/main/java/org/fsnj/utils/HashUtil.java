package org.fsnj.utils;

import com.google.common.hash.Hashing;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;

public class HashUtil {


    public static byte[] sha256(byte[] bytes) {
        return Hashing.sha256().hashBytes(bytes).asBytes();
    }

    public static byte[] hash256(byte[] message, int ofs, int len) {
        Digest d = new SHA256Digest();;
        byte[] res = new byte[d.getDigestSize()];
        d.update(message, ofs, len);
        d.doFinal(res, 0);
        return res;
    }

    public static byte[] sha3(byte[] bytes) {
        SHA256Digest digest = new SHA256Digest();
        digest.update(bytes, 0, bytes.length);
        byte[] result = new byte[digest.getDigestSize()];
        digest.doFinal(result, 0);
        return result;
    }

//    public static byte[] sha256(byte[] bytes) {
//        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256();
//        return digestSHA3.digest(bytes);
//    }

    public static byte[] generateMac(byte[] derivedKey, byte[] cipherText) {
        byte[] result = new byte[16 + cipherText.length];

        System.arraycopy(derivedKey, 16, result, 0, 16);
        System.arraycopy(cipherText, 0, result, 16, cipherText.length);

        return HashUtil.sha256(result);
    }
}
