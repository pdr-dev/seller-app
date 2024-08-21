package com.grupo.casas.bahia.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CpfUtilsTest {

    @Test
    public void testValidCpf() {
        assertTrue(CpfUtils.isValidCpf("123.456.789-09"));
        assertTrue(CpfUtils.isValidCpf("12345678909"));
    }

    @Test
    public void testInvalidCpf() {
        assertFalse(CpfUtils.isValidCpf("123.456.789-00"));
        assertFalse(CpfUtils.isValidCpf("12345678900"));
        assertFalse(CpfUtils.isValidCpf("000.000.000-00"));
        assertFalse(CpfUtils.isValidCpf("1234567890"));
    }

    @Test
    public void testInvalidCpfLength() {
        assertFalse(CpfUtils.isValidCpf("1234567890"));
        assertFalse(CpfUtils.isValidCpf("123456789012"));
        assertFalse(CpfUtils.isValidCpf("12345"));
    }

    @Test
    public void testCpfWithSpecialCharacters() {
        assertTrue(CpfUtils.isValidCpf("123.456.789-09"));
        assertTrue(CpfUtils.isValidCpf("123-456-789.09"));
        assertFalse(CpfUtils.isValidCpf("!@#$%^&*()"));
    }

    @Test
    public void testCpfWithLeadingZeros() {
        assertTrue(CpfUtils.isValidCpf("082.770.500-03"));
        assertFalse(CpfUtils.isValidCpf("000.123.456-79"));
    }
}
