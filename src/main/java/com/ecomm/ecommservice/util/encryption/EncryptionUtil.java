package com.ecomm.ecommservice.util.encryption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
public class EncryptionUtil {
    @Value("${spring.app.encryption.secret}")
    private String key;

    @Value("${spring.app.encryption.initVector}")
    private String initVector;

    private final String algo = "AES/CBC/PKCS5PADDING";

    public String encrypt (String data) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("Error occurred while encrypting: {}",  e.getMessage());
        }

        return null;
    }

    public String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original);
        } catch (Exception e) {
            log.error("Error occurred while decrypting: {}",  e.getMessage());
        }
        return null;
    }
}
