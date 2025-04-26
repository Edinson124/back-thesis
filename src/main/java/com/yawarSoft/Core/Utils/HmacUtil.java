package com.yawarSoft.Core.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class HmacUtil {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private final SecretKeySpec secretKeySpec;

    public HmacUtil(@Value("${hmac.secret.key}") String secretKey) {
        this.secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HMAC_ALGORITHM);
    }

    public String generateHmac(String data) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error generating HMAC", e);
        }
    }
}
