package org.fsnj.blockchain;

import java.math.BigInteger;

/**
 * Created by huchao on 2019/9/26.
 */
public class MakeSwaps {
    private String ID;
    private String Owner;
    private String[] FromAssetID;
    private BigInteger[] FromStartTime;
    private BigInteger[] FromEndTime;
    private BigInteger[]  MinFromAmount;
    private String[] ToAssetID;
    private BigInteger[] ToStartTime;
    private BigInteger[] ToEndTime;
    private BigInteger[] MinToAmount;
    private Integer[] SwapSize;
    private String[] Targes;
    private long Time;
    private String Description;
    private int Notation;
}
