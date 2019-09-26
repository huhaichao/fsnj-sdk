package org.fsnj.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.spongycastle.util.encoders.Hex;

public class HexUtil {

    /**
     * double to
     *
     * @param value
     * @param decimal
     * @return
     */
    public static String doubleToHex(Object value, int decimal) {
        // to hex
        BigInteger temp = doubleToBigInteger(value, decimal);
        return "0x" + temp.toString(16);
    }

    /**
     * double to bigInteger
     *
     * @param value
     * @param decimal
     * @return
     */
    public static BigInteger doubleToBigInteger(Object value, int decimal) {

        BigDecimal bd1 = null;
        if (value instanceof Double) {
            bd1 = new BigDecimal(Double.toString((double)value));
        } else if (value instanceof BigDecimal) {
            bd1 = (BigDecimal)value;
        }else if (value instanceof String) {
            bd1 = new BigDecimal((String)value);
        }

        // to hex
        BigDecimal bd2 = new BigDecimal(Double.toString(Math.pow(10, decimal)));
        return bd1.multiply(bd2).toBigInteger();
    }

    /**
     * double to
     *
     * @param x
     * @return
     */
    public static String intToHex(int x) {
        return Integer.toHexString(x);
    }

    /**
     * hex to byteArray
     *
     * @param x
     * @return
     * @throws Exception
     */
    public static byte[] StringHexToByteArray(String x) throws Exception {
        if (x.startsWith("0x")) {
            x = x.substring(2);
        }
        if (x.length() % 2 != 0) { x = "0" + x; }
        return Hex.decode(x);
    }

    public static String removeOx(String x) {
        if (x.startsWith("0x")) {
            x = x.substring(2);
        }
        return x;
    }
}
