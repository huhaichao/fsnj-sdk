package org.fsnj.blockchain;

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
        private  long StartTime ;
        private  long ExpireTime ;
        private  long Value ;

    }

}
