package org.fsnj.blockchain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockTx {
    private Tx tx;
    private TxReceipt receipt;
    private boolean receiptFound;
    @Data
    @Builder
    public static class Tx {
        private String blockHash;
        private String blockNumber;
        private String from;
        private String gasPrice;
        private String gasLimit;
        private String hash;
        private String input;
        private String nonce;
        private String to;
        private String transactionIndex;
        private String value;
        private String v;
        private String r;
        private String s;
    }

    @Data
    @Builder
    public static class TxReceipt {
        private String blockHash;
        private String blockNumber;
        private String contractAddress;
        private String cumulativeGasUsed;
        private String gasLimit;
        private String from;
        private String gasUsed;
        private Object logs;
        private String logsBloom;
        private String transactionIndex;
        private String status;
        private String to;
        private String transactionHash;
    }
}
