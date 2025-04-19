package dev.sorn.matchingengine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MatchingEngine {
    private final Map<CurrencyPair, OrderBook> books;

    public MatchingEngine(Set<CurrencyPair> supportedCurrencyPairs) {
        this.books = new HashMap<>();
        for (CurrencyPair pair : supportedCurrencyPairs) {
            books.put(pair, new OrderBook(pair));
        }
    }

    public OrderBook viewOf(CurrencyPair currencyPair) {
        OrderBook book = books.get(currencyPair);
        if (book == null) {
            throw new IllegalArgumentException("Unsupported currency pair: " + currencyPair);
        }
        // TODO: return immutable book (view)
        return book;
    }
}