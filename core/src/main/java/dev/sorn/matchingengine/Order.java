package dev.sorn.matchingengine;

class Order {
    /**
     * Globally unique identifier, used to reference the order during amends and
     * pulls.
     */
    public long id;

    /**
     * @see CurrencyPair
     */
    public CurrencyPair pair;

    /**
     * @see OrderType
     */
    public OrderType type;

    /**
     * @see OrderSide
     */
    public OrderSide side;

    /**
     * Quantity in base currency units. Must be positive.
     */
    public int quantity;

    /**
     * Order price expressed in quote currency units.
     */
    public long price;

    /**
     * Engine-assigned timestamp in nanoseconds.
     */
    public long timestamp;

    // Intrusive pointers (used internally by OrderBook)
    Order next;
    Order prev;

    public void clear() {
        this.id = 0;
        this.pair = null;
        this.type = null;
        this.side = null;
        this.quantity = 0;
        this.price = 0;
        this.timestamp = 0;
        this.next = null;
        this.prev = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Order other)) {
            return false;
        }
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}