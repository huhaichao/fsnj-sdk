package org.fsnj.crypto;


import java.io.*;
import java.security.SecureRandomSpi;

/**
 * A SecureRandom implementation that is able to override the standard JVM provided implementation, and which simply
 * serves random numbers by reading /dev/urandom. That is, it delegates to the kernel on UNIX systems and is unusable on
 * other platforms. Attempts to manually set the seed are ignored. There is no difference between seed bytes and
 * non-seed bytes, they are all from the same source.
 */
public class LinuxSecureRandom extends SecureRandomSpi {
    private static final FileInputStream urandom;


    static {
        try {
            File file = new File("/dev/urandom");
            // This stream is deliberately leaked.
            urandom = new FileInputStream(file);
            if (urandom.read() == -1) {
                throw new RuntimeException("/dev/urandom not readable?");
            }
            // Now override the default SecureRandom implementation with this one.
//            int position = Security.insertProviderAt(new LinuxSecureRandomProvider(), 1);
//
//            if (position != -1)
//                log.info("Secure randomness will be read from {} only.", file);
//            else
//                log.info("Randomness is already secure.");
        } catch (FileNotFoundException e) {
            // Should never happen.
            System.out.println("zil sdk error : /dev/urandom does not appear to exist or is not openable");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("zil sdk error : /dev/urandom does not appear to be readable");
            throw new RuntimeException(e);
        }
    }

    private final DataInputStream dis;

    public LinuxSecureRandom() {
        // DataInputStream is not thread safe, so each random object has its own.
        dis = new DataInputStream(urandom);
    }

    @Override
    protected void engineSetSeed(byte[] bytes) {
        // Ignore.
    }

    @Override
    protected void engineNextBytes(byte[] bytes) {
        try {
            dis.readFully(bytes); // This will block until all the bytes can be read.
        } catch (IOException e) {
            throw new RuntimeException(e); // Fatal error. Do not attempt to recover from this.
        }
    }

    @Override
    protected byte[] engineGenerateSeed(int i) {
        byte[] bits = new byte[i];
        engineNextBytes(bits);
        return bits;
    }
}
