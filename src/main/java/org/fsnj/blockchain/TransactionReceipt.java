package org.fsnj.blockchain;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionReceipt {
    private boolean success;
    private String cumulative_gas;
    private String epoch_num;
}
