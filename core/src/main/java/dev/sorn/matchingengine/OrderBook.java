package dev.sorn.matchingengine;

public final class OrderBook {
    private final CurrencyPair currencyPair;

    public OrderBook(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public CurrencyPair currencyPair() {
        return currencyPair;
    }
}