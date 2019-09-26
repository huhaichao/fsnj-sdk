package org.fsnj.blockchain;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StakeInfo {

    private Map<String,Integer> stakeInfo;

    private Summary summary;

    @Data
    @Builder
    public static class Summary {

        private  Integer totalMiners ;
        private  Integer totalTickets ;
    }


}
