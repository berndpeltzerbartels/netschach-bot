package de.netschach.security;

import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class HmacUtilTest {

    private static final String DATA = "Huhu!";
    private static final String KEY = "123";

    @Test
    void test() throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(KEY.getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        System.out.println(HexFormat.of().formatHex(mac.doFinal(DATA.getBytes())).toUpperCase());
    }
}
