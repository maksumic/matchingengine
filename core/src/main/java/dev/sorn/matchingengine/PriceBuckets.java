package dev.sorn.matchingengine;

/**
 * A price-indexed container for intrusive FIFO order chains.
 * Each price level maps to the head of a doubly-linked {@link Order} chain.
 * <p>
 * Used internally by {@link OrderBook} to manage one side of the book (bids or asks).
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
     * Inserts the given order into its appropriate price level,
     * preserving price–time priority (FIFO within level).
     *
     * @param order the {@link Order} to insert
     */
    void add(Order order);

    /**
     * Returns the head {@link Order} at the best price level:
     * highest for bids, lowest for asks. Returns {@code null} if empty.
     *
     * @return the best price level’s head {@link Order}, or {@code null} if none exist
     */
    Order best();
}