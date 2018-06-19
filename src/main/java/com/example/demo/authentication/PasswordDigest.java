package com.example.demo.authentication;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class PasswordDigest {

    private static final int DEFAULT_SALT_LENGTH = 16;
    private static final String DIGEST_ALGORITHM = "SHA-512";

    private final byte[] salt;
    private final byte[] digest;

    private static ThreadLocal<SecureRandom> random = new ThreadLocal<SecureRandom>() {
        @Override
        protected SecureRandom initialValue() {
            return new SecureRandom();
        }
    };

    public PasswordDigest(char[] password) {
        salt = generateSalt();
        digest = createDigest(password, salt);
    }


    public boolean verify(char[] providedPassword) {
        if(providedPassword == null) {
            throw new NullPointerException("Password is null.");
        }
        return digest != null && Arrays.equals(digest, createDigest(providedPassword, salt));
    }

    private static byte[] createDigest(char[] password, byte[] salt) {
        MessageDigest hash;
        try {
            hash = MessageDigest.getInstance(DIGEST_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Unsupportted algorithm: " + DIGEST_ALGORITHM, e);
        }
        hash.update(salt);
        ByteBuffer passwordBytes = Charset.forName("UTF8").encode(CharBuffer.wrap(password));
        hash.update(passwordBytes);
        return hash.digest();
    }

    private static byte[] generateSalt(int length) {
        byte[] newSalt = new byte[length];
        random.get().nextBytes(newSalt);
        return newSalt;
    }

    private static byte[] generateSalt() {
        return generateSalt(DEFAULT_SALT_LENGTH);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof PasswordDigest))
            return false;
        PasswordDigest other = (PasswordDigest) obj;
        if (!Arrays.equals(this.salt, other.salt))
            return false;
        if (!Arrays.equals(this.digest, other.digest))
            return false;
        return true;
    }

}
