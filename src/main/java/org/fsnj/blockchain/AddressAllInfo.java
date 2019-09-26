package org.fsnj.blockchain;

import java.math.BigInteger;
import java.util.Map;

import lombok.Builder;
import lombok.Data;
import org.fsnj.blockchain.Ticket.TicketBody;

@Data
@Builder
public class AddressAllInfo {

    private Map<String,TicketBody> tickets ;

    private Map<String,TimeLockBalance> timeLockBalances;

    private Map<String,BigInteger> balances;
}
