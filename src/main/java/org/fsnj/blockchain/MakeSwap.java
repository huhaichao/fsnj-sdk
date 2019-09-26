package org.fsnj.blockchain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MakeSwap {

     private String ID;

     private String Owner;

     private String FromAssetID;

     private long FromStartTime;

     private long FromEndTime;
     private long  MinFromAmount;
    private String ToAssetID;
    private long ToStartTime;
    private long ToEndTime;
    private long MinToAmount;
    private int SwapSize;
    private String[] Targes;
    private long Time;
    private String Description;
    private int Notation;

}
