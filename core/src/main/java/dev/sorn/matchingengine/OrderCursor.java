package dev.sorn.matchingengine;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Read-only forward cursor for intrusive order chains.
 */
final class OrderCursor implements Iterator<Order> {
    private Order current;

    public OrderCursor(Order head) {
        this.current = head;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public Order next() {
        if (current == null) {
            throw new NoSuchElementException("No more orders in cursor traversal");
        }
        final var out = current;
        current = current.next;
        return out;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Removal not supported in OrderCursor");
    }
}