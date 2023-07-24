package ua.birthdays.app.domain.util;

import ua.birthdays.app.domain.exceptions.DomainException;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Encryption {

    public static String encryptionSHA256(char[] password) throws DomainException {
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");
            StringBuilder stringBuilder = new StringBuilder();
            byte[] bytes = md5.digest(toBytes(password));
            for (byte b : bytes) {
                stringBuilder.append(String.format("%02X", b));
            }
            return new String(stringBuilder);
        } catch (NoSuchAlgorithmException e) {
            throw new DomainException("Cryptographic algorithm isn't available!", e);
        }
    }

    private static byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

}
