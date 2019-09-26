package org.fsnj.blockchain;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MakeSwap {

     private String ID;

     private String Owner;

     private String FromAssetID;

     private BigInteger FromStartTime;

     private BigInteger FromEndTime;
     private BigInteger  MinFromAmount;
    private String ToAssetID;
    private BigInteger ToStartTime;
    private BigInteger ToEndTime;
    private BigInteger MinToAmount;
    private int SwapSize;
    private String[] Targes;
    private BigInteger Time;
    private String Description;
    private int Notation;

}
