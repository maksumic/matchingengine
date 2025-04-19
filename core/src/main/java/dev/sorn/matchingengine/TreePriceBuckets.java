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

    @Override
    public void put(long price, Order head) {
        if (head == null) {
            levels.remove(price);
        } else {
            levels.put(price, head);
        }
    }

    @Override
    public void remove(long price) {
        levels.remove(price);
    }

    @Override
    public Order best() {
        return levels.isEmpty() ? null : levels.firstEntry().getValue();
    }
}