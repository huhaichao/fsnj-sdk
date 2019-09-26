package org.fsnj.blockchain;

import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TimeLockBalance {

    private List<TimeLockBalanceBody> Items ;

    @Data
    @Builder
    public static class TimeLockBalanceBody {

        private long StartTime;

        private  long EndTime ;

        private long Value ;

    }
}
