package dev.sorn.matchingengine;

/**
 * A price-indexed container for intrusive FIFO order chains.
 * Each price level ("bucket") maps to the head of a singly-linked {@link Order} chain.
 * <p>
 * Used internally by {@link OrderBook} to manage price levels for one side (bid or ask).
 */
interface PriceBuckets {
    /**
     * Returns the head order at the specified price level.
     *
     * @param price the price level (in ticks)
     * @return the head {@link Order}, or {@code null} if no orders exist at this level
     */
    Order get(long price);

    /**
     * Associates the specified order chain with the given price level.
     * Replaces any existing chain at this price.
     *
     * @param price the price level (in ticks)
     * @param head the head of the order chain, or {@code null} to remove the level
     */
    void put(long price, Order head);

    /**
     * Removes the order chain at the specified price level, if present.
     *
     * @param price the price level (in ticks)
     */
    void remove(long price);

    /**
     * Returns the head order at the best price level. For bids, this is the highest price. For asks, the lowest.
     *
     * @return the head {@link Order} at the best price level, or {@code null} if none exist
     */
    Order best();
}