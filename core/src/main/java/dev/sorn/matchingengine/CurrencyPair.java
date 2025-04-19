package dev.sorn.matchingengine;

public record CurrencyPair(Currency base, Currency quote) {
    public CurrencyPair {
        if (base == null) {
            throw new IllegalArgumentException("Base currency cannot be null");
        }
        if (quote == null) {
            throw new IllegalArgumentException("Quote currency cannot be null");
        }
        if (base.equals(quote)) {
            throw new IllegalArgumentException("Base and quote currencies must differ, but both were: " + base);
        }
    }

    @Override
    public String toString() {
        return base + "/" + quote;
    }
}