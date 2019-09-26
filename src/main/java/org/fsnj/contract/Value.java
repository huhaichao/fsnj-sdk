package org.fsnj.contract;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Value {
    private String vname;
    private String type;
    private String value;
}

