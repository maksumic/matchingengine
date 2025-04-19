package dev.sorn.matchingengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreePriceBucketsTest {
    @Test
    public void putAndGet_returnsCorrectOrder() {
        var buckets = new TreePriceBuckets(true);
        var o1 = new Order(1L, null, null, null, 1, 100, 0L, null, null);
        buckets.put(100, o1);
        Assertions.assertEquals(o1, buckets.get(100));
        Assertions.assertNull(buckets.get(101));
    }

    @Test
    public void remove_removesPriceLevel() {
        var buckets = new TreePriceBuckets(false);
        var o1 = new Order(1L, null, null, null, 1, 105, 0L, null, null);
        buckets.put(105, o1);
        buckets.remove(105);
        Assertions.assertNull(buckets.get(105));
    }

    @Test
    public void put_null_removesLevel() {
        var buckets = new TreePriceBuckets(true);
        var o1 = new Order(1L, null, null, null, 1, 99, 0L, null, null);
        buckets.put(99, o1);
        buckets.put(99, null);
        Assertions.assertNull(buckets.get(99));
    }

    @Test
    public void best_returnsHighestForBids() {
        var buckets = new TreePriceBuckets(true);
        var o1 = new Order(1L, null, null, null, 1, 100, 0L, null, null);
        var o2 = new Order(2L, null, null, null, 1, 105, 0L, null, null);
        buckets.put(100, o1);
        buckets.put(105, o2);
        Assertions.assertEquals(o2, buckets.best());
    }

    @Test
    public void best_returnsLowestForAsks() {
        var buckets = new TreePriceBuckets(false);
        var o1 = new Order(1L, null, null, null, 1, 104, 0L, null, null);
        var o2 = new Order(2L, null, null, null, 1, 101, 0L, null, null);
        buckets.put(104, o1);
        buckets.put(101, o2);
        Assertions.assertEquals(o2, buckets.best());
    }

    @Test
    public void best_returnsNullWhenEmpty() {
        var buckets = new TreePriceBuckets(true);
        Assertions.assertNull(buckets.best());
    }
}