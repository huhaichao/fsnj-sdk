package org.fsnj.account;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.fsnj.crypto.KeyTools;
import org.fsnj.crypto.Schnorr;
import org.fsnj.crypto.Signature;
import org.fsnj.jsonrpc.HttpProvider;
import org.fsnj.transaction.Transaction;
import org.fsnj.transaction.TxParams;

public class Wallet {
    private Map<String, Account> accounts = new HashMap<>();
    private HttpProvider provider;
    private Optional<Account> defaultAccount;

    public Wallet() {
        defaultAccount = Optional.empty();
        provider = new HttpProvider("https://127.0.0.1/");
    }

    public void setProvider(HttpProvider provider) {
        this.provider = provider;
    }

    public Wallet(Map<String, Account> accounts, HttpProvider provider) {
        this.accounts = accounts;
        this.provider = provider;

        if (accounts.size() > 0) {
            defaultAccount = Optional.of(accounts.values().iterator().next());
        } else {
            Optional.empty();
        }
    }

    public String createAccount() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        Account account = new Account(KeyTools.generateKeyPair());
        this.accounts.put(account.getAddress(), account);

        if (!defaultAccount.isPresent()) {
            defaultAccount = Optional.of(account);
        }
        return account.getAddress();
    }

    public String addByPrivateKey(String privateKey) throws NoSuchAlgorithmException {
        Account account = new Account(privateKey);
        this.accounts.put(account.getAddress(), account);
        if (!defaultAccount.isPresent()) {
            defaultAccount = Optional.of(account);
        }
        return account.getAddress();
    }

    public String addByKeyStore(String keystore, String passphrase) throws Exception {
        Account account = Account.fromFile(keystore, passphrase);
        this.accounts.put(account.getAddress(), account);

        if (!defaultAccount.isPresent()) {
            defaultAccount = Optional.of(account);
        }
        return account.getAddress();
    }

    public void setDefault(String address) {
        this.defaultAccount = Optional.of(accounts.get(address));
    }

    public void remove(String address) {
        Account toRemove = accounts.get(address);
        if (null != toRemove) {
            accounts.remove(address);
            if (defaultAccount.isPresent() && defaultAccount.get().getAddress().equals(toRemove.getAddress())) {
                if (!accounts.values().isEmpty()) {
                    defaultAccount = Optional.of(accounts.values().iterator().next());
                } else {
                    defaultAccount = Optional.empty();
                }
            }
        }
    }

    public static int pack(int a, int b) {
        return (a << 16) + b;
    }

}
