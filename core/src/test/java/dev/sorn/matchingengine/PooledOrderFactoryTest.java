package dev.sorn.matchingengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PooledOrderFactoryTest {
    @Test
    public void create_whenPoolIsEmpty_returnsNewOrder_withCorrectFields() {
        final var factory = new PooledOrderFactory(10);
        final var o = factory.create(1L, null, null, null, 10, 100L, 999L);
        Assertions.assertNotNull(o);
        Assertions.assertEquals(1L, o.id);
        Assertions.assertEquals(10, o.quantity);
        Assertions.assertEquals(100L, o.price);
        Assertions.assertEquals(999L, o.timestamp);
    }

    @Test
    public void create_afterRecycle_returnsSameInstance_andClearsFields() {
        final var factory = new PooledOrderFactory(10);
        final var dirty = new Order();
        dirty.prev = new Order();
        dirty.next = new Order();
        dirty.quantity = 99;
        dirty.price = 88L;
        factory.recycle(dirty);
        final var reused = factory.create(2L, null, null, null, 5, 6L, 7L);
        Assertions.assertSame(dirty, reused);
        Assertions.assertEquals(5, reused.quantity);
        Assertions.assertEquals(6L, reused.price);
        Assertions.assertEquals(7L, reused.timestamp);
        Assertions.assertNull(reused.prev);
        Assertions.assertNull(reused.next);
    }

    @Test
    public void recycle_addsOrderToFront_ofPool() {
        final var factory = new PooledOrderFactory(10);
        final var o1 = new Order();
        final var o2 = new Order();
        factory.recycle(o1);
        factory.recycle(o2);
        final var reused = factory.create(3L, null, null, null, 1, 1L, 1L);
        Assertions.assertSame(o2, reused); // LIFO
    }

    @Test
    public void create_andRecycle_multipleTimes_preservesObjectIdentity() {
        final var factory = new PooledOrderFactory(100);
        final var recycled = new Order[100];
        for (int i = 0; i < 100; i++) {
            recycled[i] = new Order();
            factory.recycle(recycled[i]);
        }
        for (int i = 99; i >= 0; i--) {
            final var actual = factory.create(1000 + i, null, null, null, 1, 1L, 1L);
            Assertions.assertSame(recycled[i], actual, "did not reuse recycled order at index " + i);
        }
    }

    @Test
    public void recycle_beyondCapacity_doesNotExceedLimit() {
        final var factory = new PooledOrderFactory(2); // pool capacity is 2
        final var o1 = new Order(); o1.id = 1;
        final var o2 = new Order(); o2.id = 2;
        final var o3 = new Order(); o3.id = 3;
        factory.recycle(o1);
        factory.recycle(o2);
        factory.recycle(o3); // should be discarded
        final var reused1 = factory.create(100, null, null, null, 1, 1L, 1L);
        final var reused2 = factory.create(101, null, null, null, 1, 1L, 1L);
        final var fresh = factory.create(102, null, null, null, 1, 1L, 1L);
        Assertions.assertTrue(reused1 == o2 || reused1 == o1);
        Assertions.assertTrue(reused2 == o2 || reused2 == o1);
        Assertions.assertNotSame(o3, fresh, "o3 should have been discarded due to capacity limit");
    }
}