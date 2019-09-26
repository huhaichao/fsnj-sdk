package org.fsnj.transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ethereum.crypto.ECKey;
import org.fsnj.jsonrpc.HttpProvider;
import org.fsnj.utils.HexUtil;
import lombok.Builder;
import lombok.Data;
import org.spongycastle.util.encoders.Hex;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Slf4j
public class Transaction {

    private  static final Integer chainId =32659 ;

    private String ID;
    private String version;
    private String nonce;
    private String value;
    private String gasPrice;
    private String gasLimit;
    private String signature;
    private String fromAddr;
    private String toAddr;
    private String data;
    private Integer decimal;

    private HttpProvider provider;
    private TxStatus status;


    public void transfer(ECKey ecKey) throws IOException {
        org.ethereum.core.Transaction tx = null ;
        if (!StringUtils.isEmpty(this.getID())) {
            List<Type> input = new ArrayList<>();
            input.add(new Address(this.toAddr));
            input.add(new Uint256(HexUtil.doubleToBigInteger(this.value, decimal)));
            List<TypeReference<?>> output = new ArrayList<>();
            Function function = new Function("transfer", input, output);
            String encodedFunction = FunctionEncoder.encode(function);
            try {
                    tx = new org.ethereum.core.Transaction(
                        HexUtil.StringHexToByteArray(this.nonce),
                        HexUtil.StringHexToByteArray(this.gasPrice),
                        HexUtil.StringHexToByteArray(this.gasLimit),
                        HexUtil.StringHexToByteArray(this.getID()),
                        HexUtil.StringHexToByteArray("0"),
                        HexUtil.StringHexToByteArray(encodedFunction), chainId);

            } catch (final Exception e) {
                log.error("Sign fail address:{}", this.fromAddr, e);
            }
        } else {
            try {
                    tx = new org.ethereum.core.Transaction(
                        HexUtil.StringHexToByteArray(this.nonce),
                        HexUtil.StringHexToByteArray(this.gasPrice),
                        HexUtil.StringHexToByteArray(this.gasLimit),
                        HexUtil.StringHexToByteArray(this.toAddr),
                        HexUtil.StringHexToByteArray(this.value),
                        HexUtil.StringHexToByteArray(this.data), chainId);

            } catch (final Exception e) {
                log.error("Sign fail address:{}", this.getFromAddr(), e);
            }
        }
        // 签名
        tx.sign(ecKey);
        this.signature = "0x" + Hex.toHexString(tx.getEncoded());
    }
    public boolean isPending() {
        return this.status.equals(TxStatus.Pending);
    }

    public boolean isInitialised() {
        return this.status.equals(TxStatus.Initialised);
    }

    public boolean isConfirmed() {
        return this.status.equals(TxStatus.Confirmed);
    }

    public boolean isRejected() {
        return this.status.equals(TxStatus.Rejected);
    }

}
