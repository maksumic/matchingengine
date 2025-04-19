package dev.sorn.matchingengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderCursorTest {
    @Test
    public void cursor_emptyList_hasNextFalse_andThrowsOnNext() {
        final var cursor = new OrderCursor(null);
        Assertions.assertFalse(cursor.hasNext());
        final var e = Assertions.assertThrows(NoSuchElementException.class, cursor::next);
        Assertions.assertEquals("No more orders in cursor traversal", e.getMessage());
    }

    @Test
    public void cursor_remove_throwsUnsupportedOperationException() {
        final var order = new Order(); order.id = 42L;
        final var cursor = new OrderCursor(order);
        final var e = Assertions.assertThrows(UnsupportedOperationException.class, cursor::remove);
        Assertions.assertEquals("Removal not supported in OrderCursor", e.getMessage());
    }

    @Test
    public void cursor_singleOrder_returnsOnce_thenThrows() {
        final var order = new Order();
        order.id = 1L;
        final var cursor = new OrderCursor(order);
        Assertions.assertTrue(cursor.hasNext());
        Assertions.assertEquals(order, cursor.next());
        Assertions.assertFalse(cursor.hasNext());
    }

    @Test
    public void cursor_multipleOrders_returnsInSequence_thenThrows() {
        final var o1 = new Order(); o1.id = 1L;
        final var o2 = new Order(); o2.id = 2L;
        final var o3 = new Order(); o3.id = 3L;
        o1.next = o2;
        o2.next = o3;
        final var cursor = new OrderCursor(o1);
        Assertions.assertTrue(cursor.hasNext());
        Assertions.assertEquals(o1, cursor.next());
        Assertions.assertTrue(cursor.hasNext());
        Assertions.assertEquals(o2, cursor.next());
        Assertions.assertTrue(cursor.hasNext());
        Assertions.assertEquals(o3, cursor.next());
        Assertions.assertFalse(cursor.hasNext());
        Assertions.assertThrows(NoSuchElementException.class, cursor::next);
    }

    @Test
    public void cursor_forEachRemaining_visitsAllOrdersInSequence() {
        final var o1 = new Order(); o1.id = 1L;
        final var o2 = new Order(); o2.id = 2L;
        final var o3 = new Order(); o3.id = 3L;
        o1.next = o2; o2.next = o3;
        final var cursor = new OrderCursor(o1);
        final var seen = new ArrayList<>();
        cursor.forEachRemaining(order -> seen.add(order.id));
        Assertions.assertEquals(List.of(1L, 2L, 3L), seen);
    }
}