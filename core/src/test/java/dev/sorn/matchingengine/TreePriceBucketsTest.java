package dev.sorn.matchingengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreePriceBucketsTest {
    @Test
    public void add_singleOrder_addsAtPriceLevel_andBestMatches() {
        final var buckets = new TreePriceBuckets(true); // bid-side
        final var order = new Order(1L, null, null, OrderSide.BUY, 1, 100L, 0L, null, null);
        buckets.add(order);
        Assertions.assertEquals(order, buckets.get(100));
        Assertions.assertEquals(order, buckets.best());
    }

    @Test
    public void add_multipleOrdersSamePrice_preservesPriceTimePriority() {
        final var buckets = new TreePriceBuckets(false); // ask-side
        final var o1 = new Order(1L, null, null, OrderSide.SELL, 1, 105L, 0L, null, null);
        final var o2 = new Order(2L, null, null, OrderSide.SELL, 1, 105L, 0L, null, null);
        final var o3 = new Order(3L, null, null, OrderSide.SELL, 1, 105L, 0L, null, null);
        buckets.add(o1);
        buckets.add(o2);
        buckets.add(o3);
        final var head = buckets.get(105);
        Assertions.assertEquals(o1, head);
        Assertions.assertEquals(o2, head.next);
        Assertions.assertEquals(o3, head.next.next);
        Assertions.assertEquals(o2, o3.prev);
        Assertions.assertEquals(o1, o2.prev);
        Assertions.assertNull(o3.next);
    }

    @Test
    public void add_multiplePrices_bestReturnsHighestForBids() {
        final var buckets = new TreePriceBuckets(true); // bid-side
        final var o1 = new Order(1L, null, null, OrderSide.BUY, 1, 101L, 0L, null, null);
        final var o2 = new Order(2L, null, null, OrderSide.BUY, 1, 110L, 0L, null, null);
        final var o3 = new Order(3L, null, null, OrderSide.BUY, 1, 105L, 0L, null, null);
        buckets.add(o1);
        buckets.add(o2);
        buckets.add(o3);
        Assertions.assertEquals(o2, buckets.best());
    }

    @Test
    public void add_multiplePrices_bestReturnsLowestForAsks() {
        final var buckets = new TreePriceBuckets(false); // ask-side
        final var o1 = new Order(1L, null, null, OrderSide.SELL, 1, 101L, 0L, null, null);
        final var o2 = new Order(2L, null, null, OrderSide.SELL, 1, 110L, 0L, null, null);
        final var o3 = new Order(3L, null, null, OrderSide.SELL, 1, 99L, 0L, null, null);
        buckets.add(o1);
        buckets.add(o2);
        buckets.add(o3);
        Assertions.assertEquals(o3, buckets.best());
    }

    @Test
    public void best_returnsNull_whenEmpty() {
        final var bids = new TreePriceBuckets(true);
        final var asks = new TreePriceBuckets(false);
        Assertions.assertNull(bids.best());
        Assertions.assertNull(asks.best());
    }

    @Test
    public void get_returnsNull_whenLevelMissing() {
        final var buckets = new TreePriceBuckets(true);
        Assertions.assertNull(buckets.get(999L));
    }

    @Test
    public void add_preservesPointerIntegrity_afterMultipleAdds() {
        final var buckets = new TreePriceBuckets(true);
        final var o1 = new Order(1L, null, null, OrderSide.BUY, 1, 200L, 0L, null, null);
        final var o2 = new Order(2L, null, null, OrderSide.BUY, 1, 200L, 0L, null, null);
        final var o3 = new Order(3L, null, null, OrderSide.BUY, 1, 200L, 0L, null, null);
        buckets.add(o1);
        buckets.add(o2);
        buckets.add(o3);
        Assertions.assertSame(o1.next, o2);
        Assertions.assertSame(o2.prev, o1);
        Assertions.assertSame(o2.next, o3);
        Assertions.assertSame(o3.prev, o2);
        Assertions.assertNull(o3.next);
        Assertions.assertEquals(o1, buckets.get(200));
    }
}