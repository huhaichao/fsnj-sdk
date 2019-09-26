package org.fsnj.transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TxParams {
    private String ID;
    private String version;
    private String nonce;
    private String value;
    private String gasPrice;
    private String gasLimit;
    private String signature;
    private String fromAddr;
    private String toAddr;
    private String data;
}
