package org.fsnj.crypto;

import java.security.SecureRandom;

/**
 * @author hehaoxian
 * @date 2019-05-16
 */
public class MySecureRandom extends SecureRandom {

    public MySecureRandom() {
        super(new LinuxSecureRandom(), new LinuxSecureRandomProvider());
    }
}
