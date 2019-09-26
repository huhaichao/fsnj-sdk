package org.fsnj.blockchain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Asset {
    private String ID;
    private String Owner;
    private String Name;
    private Integer Decimals;
    private String Total;
    private boolean CanChange;
    private String Description;
}
