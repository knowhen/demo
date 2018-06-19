package com.example.demo.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Produces sequence of Strings. Thread safe.
 * 
 * @author when
 *
 */
public class RandomGenerator {

    static final char[] WEB64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
            .toCharArray();

    private final Random random;

    /**
     * @param random
     *            Pseudo-random generator, use random for speed.
     */
    public RandomGenerator(Random random) {
        this.random = random;
    }

    /**
     * Default constructor using Random. SecureRandom for cryptographic strength.
     */
    public RandomGenerator() {
        this(new SecureRandom());
    }

    /**
     * @param length
     *            The requested number of random characters.
     * @return specified length random characters.
     */
    public String next(int length) {
        StringBuilder result = new StringBuilder(length);
        int bits = 0;
        int bitCount = 0;
        while (result.length() < length) {
            if (bitCount < 6) {
                bits = random.nextInt();
                bitCount = 32;
            }
            result.append(WEB64_ALPHABET[bits & 0x3F]);
            bits >>= 6;
            bitCount -= 6;
        }
        return result.toString();
    }

    /**
     * @param length
     *            The requested number of random integer.
     * @return specified length random integer string.
     */
    public String randomCode(int length) {
        StringBuilder result = new StringBuilder(length);
        while (result.length() < length) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

}
