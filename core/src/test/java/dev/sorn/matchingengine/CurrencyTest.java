package dev.sorn.matchingengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CurrencyTest {
    @Test
    public void currency_validCodeAndMinorUnit_constructsSuccessfully() {
        final var currency = Assertions.assertDoesNotThrow(() -> new Currency("BTC", 8));
        Assertions.assertEquals("BTC", currency.code());
        Assertions.assertEquals(8, currency.minorUnit());
    }

    @Test
    public void currency_nullCode_throwsIllegalArgumentException() {
        final var e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Currency(null, 2));
        Assertions.assertEquals("Currency code must be 2–10 uppercase letters or digits (e.g., USD, BTC, USDT), but was: null", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",              // empty
            "A",             // too short
            "TOOLONGCODE123",// too long
            "bt$",           // invalid characters
            "usd",           // lowercase
            "BTC!",          // special character
            "12345678901",   // 11 digits
            "US D",          // space inside
            "BTC-",          // dash
            "BTC.",          // punctuation
            "BTC\n",         // control char
    })
    public void currency_invalidCodeFormat_throwsIllegalArgumentException() {
        final var e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Currency("bt$", 2));
        Assertions.assertEquals("Currency code must be 2–10 uppercase letters or digits (e.g., USD, BTC, USDT), but was: bt$", e.getMessage());
    }

    @Test
    public void currency_codeTooShort_throwsIllegalArgumentException() {
        final var e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Currency("A", 2));
        Assertions.assertEquals("Currency code must be 2–10 uppercase letters or digits (e.g., USD, BTC, USDT), but was: A", e.getMessage());
    }

    @Test
    public void currency_codeTooLong_throwsIllegalArgumentException() {
        final var e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Currency("TOOLONGCODE123", 2));
        Assertions.assertEquals("Currency code must be 2–10 uppercase letters or digits (e.g., USD, BTC, USDT), but was: TOOLONGCODE123", e.getMessage());
    }

    @Test
    public void currency_minorUnitBelowZero_throwsIllegalArgumentException() {
        final var e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Currency("USD", -1));
        Assertions.assertEquals("Minor unit must be between 0 and 18 (inclusive), but was: -1 for currency: USD", e.getMessage());
    }

    @Test
    public void currency_minorUnitAboveEighteen_throwsIllegalArgumentException() {
        final var e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Currency("ETH", 19));
        Assertions.assertEquals("Minor unit must be between 0 and 18 (inclusive), but was: 19 for currency: ETH", e.getMessage());
    }
}