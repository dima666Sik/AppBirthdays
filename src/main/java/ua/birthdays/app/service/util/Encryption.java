package ua.birthdays.app.service.util;

import ua.birthdays.app.service.exceptions.CryptographicAlgorithmException;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public final class Encryption {
    private Encryption() {
    }

    /**
     * A method for encrypting a password using the SHA-256 algorithm.
     *
     * @param password an array of characters representing the password to be encrypted.
     * @return the encrypted password as a string with hexadecimal values.
     */
    public static String encryptionSHA256(char[] password) {
        try {
            var md5 = MessageDigest.getInstance("SHA-256");
            var stringBuilder = new StringBuilder();
            byte[] bytes = md5.digest(toBytes(password));
            for (byte b : bytes) {
                stringBuilder.append(String.format("%02X", b));
            }
            return new String(stringBuilder);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptographicAlgorithmException("Cryptographic algorithm isn't available!", e);
        }
    }

    /**
     * Private method to convert an array of characters to an array of bytes using UTF-8 encoding.
     * Used to secure and clean sensitive data.
     *
     * @param chars the char array to convert.
     * @return byte array representing characters in UTF-8 encoding.
     */
    private static byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

}
