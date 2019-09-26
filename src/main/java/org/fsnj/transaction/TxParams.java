package org.fsnj.transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TxParams {
    private String ID;
    private String version;
    private String nonce;
    private double value;
    private int decimal;
    private double gasPrice;
    private double gasLimit;
    private String signature;
    private String fromAddr;
    private String toAddr;
    private String data;
}
