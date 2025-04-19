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
    Order prev;
    Order next;

    public Order() {
        // No-args constructor
    }

    public Order(long id, CurrencyPair pair, OrderType type, OrderSide side, int quantity, long price, long timestamp, Order prev, Order next) {
        // All-args constructor
        this.id = id;
        this.pair = pair;
        this.type = type;
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
        this.prev = prev;
        this.next = next;
    }

    public void clear() {
        this.id = 0;
        this.pair = null;
        this.type = null;
        this.side = null;
        this.quantity = 0;
        this.price = 0;
        this.timestamp = 0;
        this.prev = null;
        this.next = null;
    }

    public void unlink() {
        if (this.prev != null) {
            this.prev.next = next;
        }
        if (this.next != null) {
            this.next.prev = prev;
        }
        this.prev = null;
        this.next = null;
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