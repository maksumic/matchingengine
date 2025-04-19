package dev.sorn.matchingengine;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * TreeMap-backed implementation of {@link PriceBuckets}.
 */
class TreePriceBuckets implements PriceBuckets {
    private final NavigableMap<Long, Order> levels;

    /**
     * Constructs a price bucket map with the specified price sorting order.
     *
     * @param descending true for bids (highest price first), false for asks
     */
    TreePriceBuckets(boolean descending) {
        this.levels = new TreeMap<>(descending ? (a, b) -> Long.compare(b, a) : Long::compare);
    }

    @Override
    public Order get(long price) {
        return levels.get(price);
    }

    /**
     * Adds the order into its price level list, preserving price-time priority.
     * If the level does not exist, it is created with this order as head.
     */
    @Override
    public void add(Order order) {
        final long price = order.price;
        final Order head = levels.get(price);
        if (head == null) {
            levels.put(price, order);
        } else {
            // Traverse to tail and link
            OrderCursor cursor = new OrderCursor(head);
            Order tail = null;
            while (cursor.hasNext()) {
                tail = cursor.next();
            }
            tail.next = order;
            order.prev = tail;
        }
    }

    @Override
    public Order best() {
        return levels.isEmpty() ? null : levels.firstEntry().getValue();
    }
}