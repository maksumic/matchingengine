package dev.sorn.matchingengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CurrencyPairTest {
    @Test
    public void currencyPair_validBaseAndQuote_constructsSuccessfully() {
        final var base = new Currency("BTC", 8);
        final var quote = new Currency("USD", 2);
        final var pair = Assertions.assertDoesNotThrow(() -> new CurrencyPair(base, quote));
        Assertions.assertEquals(base, pair.base());
        Assertions.assertEquals(quote, pair.quote());
    }

    @Test
    public void currencyPair_nullBaseCurrency_throwsIllegalArgumentException() {
        final var e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CurrencyPair(null, new Currency("USD", 2)));
        Assertions.assertEquals("Base currency cannot be null", e.getMessage());
    }

    @Test
    public void currencyPair_nullQuoteCurrency_throwsIllegalArgumentException() {
        final var e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CurrencyPair(new Currency("BTC", 2), null));
        Assertions.assertEquals("Quote currency cannot be null", e.getMessage());
    }

    @Test
    public void currencyPair_sameBaseAndQuote_throwsIllegalArgumentException() {
        final var currency = new Currency("BTC", 2);
        final var e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CurrencyPair(currency, currency));
        Assertions.assertEquals("Base and quote currencies must differ, but both were: " + currency, e.getMessage());
    }

    @Test
    public void currencyPair_toString_returnsBaseSlashQuoteFormat() {
        final var base = new Currency("BTC", 8);
        final var quote = new Currency("USD", 2);
        final var pair = new CurrencyPair(base, quote);
        Assertions.assertEquals("BTC/USD", pair.toString());
    }
}