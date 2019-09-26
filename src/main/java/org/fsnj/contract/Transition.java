package org.fsnj.contract;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transition {
    private String name;
    private Field[] params;
}
