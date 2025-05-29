package com.ecomm.ecommservice.util.encryption;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;

@Converter
@AllArgsConstructor
public class EncryptField implements AttributeConverter<String, String> {
    private final EncryptionUtil encryptionUtil;

    @Override
    public String convertToDatabaseColumn(String s) {
        return  encryptionUtil.encrypt(s);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return encryptionUtil.decrypt(s);
    }
}
