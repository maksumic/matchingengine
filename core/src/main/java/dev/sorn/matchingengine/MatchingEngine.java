package dev.sorn.matchingengine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MatchingEngine {
    private final Map<CurrencyPair, OrderBook> books;
    private final OrderFactory orderFactory;

    public MatchingEngine(Set<CurrencyPair> supportedCurrencyPairs) {
        this.books = new HashMap<>();
        this.orderFactory = new PooledOrderFactory(100_000);
        for (CurrencyPair pair : supportedCurrencyPairs) {
            books.put(pair, new OrderBook(orderFactory, pair));
        }
    }

    public OrderBook get(CurrencyPair currencyPair) {
        OrderBook book = books.get(currencyPair);
        if (book == null) {
            throw new IllegalArgumentException("Unsupported currency pair: " + currencyPair);
        }
        return book;
    }
}