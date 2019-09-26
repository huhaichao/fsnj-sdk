package org.fsnj.blockchain;

import lombok.Data;

@Data
public class Contract {
    private String address;
    private State[] state;

    @Data
    public static class State {
        private String type;
        private String value;
        private String vname;
    }
}
