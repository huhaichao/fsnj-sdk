package org.fsnj.jsonrpc;

import lombok.Data;

@Data
public class Rep<R> {
    private String id;
    private String jsonrpc;
    private R result;
}
