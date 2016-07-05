package com.confucian.framework.encode;

import org.apache.commons.codec.binary.Hex;

/**
 * sprintPropertyCoder
 *
 * @author ice
 */
public class SpringPropertyCoder {

    private static final byte[] CIPHER_KEY = "ice_general_key".getBytes();

    public static String encode(String password) {
        try {
            byte[] encryptData = DESCoder.encrypt(password.getBytes(), CIPHER_KEY);
            return Hex.encodeHexString(encryptData);
        } catch (Exception e) {
            return password;
        }
    }

    public static String decode(String encryptedPassword) {
        try {
            byte[] decryptData = DESCoder.decrypt(Hex.decodeHex(encryptedPassword.toCharArray()), CIPHER_KEY);
            return new String(decryptData);
        } catch (Exception e) {
            return encryptedPassword;
        }
    }
}
