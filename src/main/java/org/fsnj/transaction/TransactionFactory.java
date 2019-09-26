package org.fsnj.transaction;

import org.fsnj.jsonrpc.HttpProvider;

public class TransactionFactory {

    public static Transaction buildTransaction(TxParams params, HttpProvider provider) {
        return Transaction.builder()
                .ID(params.getID())
                .version(params.getVersion())
                .nonce(params.getNonce())
                .value(params.getValue())
                .gasPrice(params.getGasPrice())
                .gasLimit(params.getGasLimit())
                .signature(params.getSignature())
                .toAddr(params.getToAddr())
                .data(params.getData())
                .provider(provider)
                .build();
    }

    public static String sendRawTransaction(Transaction signedTx) throws Exception {
        return signedTx.getProvider().sendRawTransaction(signedTx.getSignature()).getResult();
    }
}
