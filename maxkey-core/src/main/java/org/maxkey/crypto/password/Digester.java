package org.maxkey.crypto.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public  class Digester {

    private final String algorithm;

    private int iterations;

    /**
     * Create a new Digester.
     * @param algorithm the digest algorithm; for example, "SHA-1" or "SHA-256".
     * @param iterations the number of times to apply the digest algorithm to the input
     */
    Digester(String algorithm, int iterations) {
        // eagerly validate the algorithm
        createDigest(algorithm);
        this.algorithm = algorithm;
        setIterations(iterations);
    }

    public byte[] digest(byte[] value) {
        MessageDigest messageDigest = createDigest(algorithm);
        for (int i = 0; i < iterations; i++) {
            value = messageDigest.digest(value);
        }
        return value;
    }

    void setIterations(int iterations) {
        if (iterations <= 0) {
            throw new IllegalArgumentException("Iterations value must be greater than zero");
        }
        this.iterations = iterations;
    }

    private static MessageDigest createDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No such hashing algorithm", e);
        }
    }
}

