package dev.sorn.matchingengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderTest {
    @Test
    public void clear_resetsAllFieldsToDefault() {
        final var order = new Order();
        order.id = 42L;
        order.pair = new CurrencyPair(new Currency("BTC", 8), new Currency("USD", 2));
        order.type = OrderType.LIMIT;
        order.side = OrderSide.BUY;
        order.quantity = 1000;
        order.price = 25_000_000L;
        order.timestamp = System.nanoTime();
        order.next = new Order();
        order.prev = new Order();
        order.clear();
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
    public void unlink_middleOrder_updatesPrevAndNextCorrectly() {
        Order o1 = new Order();
        Order o2 = new Order();
        Order o3 = new Order();
        o1.id = 1;
        o2.id = 2;
        o3.id = 3;
        o1.next = o2;
        o2.prev = o1;
        o2.next = o3;
        o3.prev = o2;
        Assertions.assertDoesNotThrow(o2::unlink);
        Assertions.assertEquals(o3, o1.next);
        Assertions.assertEquals(o1, o3.prev);
        Assertions.assertNull(o2.prev);
        Assertions.assertNull(o2.next);
    }

    @Test
    public void unlink_firstOrder_updatesNextPrevToNull() {
        Order o1 = new Order();
        Order o2 = new Order();
        o1.id = 1;
        o2.id = 2;
        o1.next = o2;
        o2.prev = o1;
        Assertions.assertDoesNotThrow(o1::unlink);
        Assertions.assertNull(o2.prev);
        Assertions.assertNull(o1.next);
        Assertions.assertNull(o1.prev);
    }

    @Test
    public void unlink_lastOrder_updatesPrevNextToNull() {
        Order o1 = new Order();
        Order o2 = new Order();
        o1.id = 1;
        o2.id = 2;
        o1.next = o2;
        o2.prev = o1;
        Assertions.assertDoesNotThrow(o2::unlink);
        Assertions.assertNull(o2.prev);
        Assertions.assertNull(o2.next);
        Assertions.assertNull(o2.prev);
    }

    @Test
    public void unlink_singleOrder_noSideEffects() {
        Order o = new Order();
        o.id = 99;
        Assertions.assertDoesNotThrow(o::unlink);
        Assertions.assertNull(o.prev);
        Assertions.assertNull(o.next);
    }

    @Test
    public void unlink_twice_isIdempotent() {
        Order o1 = new Order();
        Order o2 = new Order();
        o1.id = 1;
        o2.id = 2;
        o1.next = o2;
        o2.prev = o1;
        Assertions.assertDoesNotThrow(o2::unlink);
        Assertions.assertDoesNotThrow(o2::unlink);
        Assertions.assertNull(o1.next);
        Assertions.assertNull(o2.prev);
        Assertions.assertNull(o2.next);
    }

    @Test
    public void equals_returnsTrueForSameIdRegardlessOfFields() {
        final var o1 = new Order();
        final var o2 = new Order();
        o1.id = 42L;
        o1.quantity = 10;
        o2.id = 42L;
        o2.quantity = 999;
        Assertions.assertEquals(o1, o2);
    }

    @Test
    public void equals_returnsFalseForDifferentIds() {
        final var o1 = new Order();
        final var o2 = new Order();
        o1.id = 1L;
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
