package org.fsnj.blockchain;

import java.math.BigInteger;
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

        private BigInteger StartTime;

        private  BigInteger EndTime ;

        private BigInteger Value ;

    }
}
