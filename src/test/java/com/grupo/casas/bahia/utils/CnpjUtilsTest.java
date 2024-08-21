package com.grupo.casas.bahia.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CnpjUtilsTest {
    @Test
    public void testValidCnpjWithoutFormatting() {
        assertTrue(CnpjUtils.isValidCnpj("12345678000195"));
    }

    @Test
    public void testValidCnpjWithFormatting() {
        assertTrue(CnpjUtils.isValidCnpj("12.345.678/0001-95"));
    }

    @Test
    public void testInvalidCnpjWithoutFormatting() {
        assertFalse(CnpjUtils.isValidCnpj("12345678000196"));
    }

    @Test
    public void testInvalidCnpjWithFormatting() {
        assertFalse(CnpjUtils.isValidCnpj("12.345.678/0001-96"));
    }

    @Test
    public void testCnpjWithInvalidLength() {
        assertFalse(CnpjUtils.isValidCnpj("12345678"));
        assertFalse(CnpjUtils.isValidCnpj("123456789012345678"));
    }

    @Test
    public void testCnpjWithRepeatingDigits() {
        assertFalse(CnpjUtils.isValidCnpj("00000000000000"));
        assertFalse(CnpjUtils.isValidCnpj("11111111111111"));
    }
}
