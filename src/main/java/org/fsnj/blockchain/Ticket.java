package org.fsnj.blockchain;

import java.math.BigInteger;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * Created by huchao on 2019/9/26.
 */
@Data
@Builder
public class Ticket {

    private Map<String,TicketBody> tickets ;

    @Data
    @Builder
    public static class TicketBody {

        private  String Owner ;
        private  long Height ;
        private  BigInteger StartTime ;
        private  BigInteger ExpireTime ;
        private BigInteger Value ;

    }

}
