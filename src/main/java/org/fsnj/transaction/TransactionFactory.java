package org.fsnj.transaction;

import org.fsnj.jsonrpc.HttpProvider;
import org.fsnj.utils.HexUtil;

public class TransactionFactory {

    public static Transaction buildTransaction(TxParams params, HttpProvider provider) {
        return Transaction.builder()
                .ID(params.getID())
                .version(params.getVersion())
                .nonce(Integer.toHexString(params.getNonce()))
                .value(HexUtil.doubleToHex(params.getValue(),params.getDecimal()))
                .gasPrice(HexUtil.doubleToHex(params.getGasPrice(),params.getDecimal()))
                .gasLimit(HexUtil.doubleToHex(params.getGasLimit(),params.getDecimal()))
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
