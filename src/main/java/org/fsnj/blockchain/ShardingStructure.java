package org.fsnj.blockchain;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShardingStructure {
    private int[] NumPeers;
}
