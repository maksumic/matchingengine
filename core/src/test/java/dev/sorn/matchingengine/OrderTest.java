package dev.sorn.matchingengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderTest {
    @Test
    public void clear_resetsAllFieldsToDefault() {
        final var order = new Order();

        // Set all fields to non-defaults
        order.id = 42L;
        order.pair = new CurrencyPair(new Currency("BTC", 8), new Currency("USD", 2));
        order.type = OrderType.LIMIT;
        order.side = OrderSide.BUY;
        order.quantity = 1000;
        order.price = 25_000_000L;
        order.timestamp = System.nanoTime();
        order.next = new Order();
        order.prev = new Order();

        // Clear the order
        order.clear();

        // Assertions
        Assertions.assertEquals(0L, order.id);
        Assertions.assertNull(order.pair);
        Assertions.assertNull(order.type);
        Assertions.assertNull(order.side);
        Assertions.assertEquals(0, order.quantity);
        Assertions.assertEquals(0L, order.price);
        Assertions.assertEquals(0L, order.timestamp);
        Assertions.assertNull(order.next);
        Assertions.assertNull(order.prev);
    }

    @Test
    public void equals_returnsTrueForSameIdRegardlessOfFields() {
        final var o1 = new Order();
        o1.id = 42L;
        o1.quantity = 10;

        final var o2 = new Order();
        o2.id = 42L;
        o2.quantity = 999;

        Assertions.assertEquals(o1, o2);
    }

    @Test
    public void equals_returnsFalseForDifferentIds() {
        final var o1 = new Order();
        o1.id = 1L;
        final var o2 = new Order();
        o2.id = 2L;
        Assertions.assertNotEquals(o1, o2);
    }

    @Test
    public void hashCode_matchesIdHashCode() {
        final var order = new Order();
        order.id = 123456789L;
        Assertions.assertEquals(Long.hashCode(123456789L), order.hashCode());
    }
}
