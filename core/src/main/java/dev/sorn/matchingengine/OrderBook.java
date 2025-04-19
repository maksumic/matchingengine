package dev.sorn.matchingengine;

import java.util.HashMap;
import java.util.Map;

final class OrderBook {
    private final OrderFactory factory;
    private final CurrencyPair pair;
    private final Map<Long, Order> orders;
    private final PriceBuckets bids;
    private final PriceBuckets asks;

    public OrderBook(OrderFactory factory, CurrencyPair pair) {
        this(factory, pair, new TreePriceBuckets(true), new TreePriceBuckets(false));
    }

    public OrderBook(OrderFactory factory, CurrencyPair pair, PriceBuckets bids, PriceBuckets asks) {
        this.factory = factory;
        this.pair = pair;
        this.orders = new HashMap<>();
        this.bids = bids;
        this.asks = asks;
    }

    public CurrencyPair currencyPair() {
        return pair;
    }

    public boolean insert(long id, CurrencyPair pair, OrderType type, OrderSide side, int quantity, long price, long timestamp) {
        if (orders.containsKey(id)) {
            return false; // duplicate
        }
        final var order = factory.create(id, pair, type, side, quantity, price, timestamp);
        orders.put(id, order);
        if (side == OrderSide.BUY) {
            bids.add(order);
        } else { // OrderSide.SELL
            asks.add(order);
        }
        // TODO: business logic
        return true;
    }

    public boolean amend(long id, int quantity, long price) {
        if (!orders.containsKey(id)) {
            return false; // not found
        }
        final var order = orders.get(id);
        // TODO: business logic
        order.quantity = quantity;
        order.price = price;
        return true;
    }

    public boolean pull(long id) {
        if (!orders.containsKey(id)) {
            return false; // not found
        }
        final var order = orders.get(id);
        // TODO: business logic
        order.unlink();
        factory.recycle(order);
        return true;
    }

    public Order bestBid() {
        return bids.best();
    }

    public Order bestAsk() {
        return asks.best();
    }
}