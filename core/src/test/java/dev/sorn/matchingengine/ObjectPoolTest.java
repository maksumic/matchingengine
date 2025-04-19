package dev.sorn.matchingengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class ObjectPoolTest {
    @Test
    public void acquire_whenPoolIsEmpty_createsNewObject() {
        final var counter = new AtomicInteger();
        ObjectPool<Integer> pool = new ObjectPool<>(counter::incrementAndGet, 10);
        Integer obj = pool.acquire();
        Assertions.assertEquals(1, obj);
    }

    @Test
    public void acquire_afterRelease_reusesObject() {
        ObjectPool<String> pool = new ObjectPool<>(() -> "pooled", 1);
        String obj1 = pool.acquire();
        pool.release(obj1);
        String obj2 = pool.acquire();
        Assertions.assertSame(obj1, obj2);
        Assertions.assertEquals(0, pool.size());
    }

    @Test
    public void release_whenPoolIsFull_doesNotAddObject() {
        ObjectPool<String> pool = new ObjectPool<>(() -> "X", 1);
        pool.release("A");
        pool.release("B"); // should be ignored
        Assertions.assertEquals(1, pool.size());
    }

    @Test
    public void size_afterMultipleReleases_reportsCorrectSize() {
        ObjectPool<Integer> pool = new ObjectPool<>(() -> 0, 5);
        pool.release(1);
        pool.release(2);
        pool.release(3);
        Assertions.assertEquals(3, pool.size());
    }

    @Test
    public void acquire_reusesInLifoOrder() {
        ObjectPool<String> pool = new ObjectPool<>(() -> "x", 10);
        String o1 = "A";
        String o2 = "B";
        pool.release(o1);
        pool.release(o2);
        Assertions.assertSame(o2, pool.acquire());
        Assertions.assertSame(o1, pool.acquire());
    }

    @Test
    public void release_andAcquire_largeVolume_preservesReuseBehavior() {
        ObjectPool<Integer> pool = new ObjectPool<>(() -> 1, 100);
        for (int i = 0; i < 100; i++) pool.release(i);
        for (int i = 0; i < 100; i++) pool.acquire();
        Assertions.assertEquals(0, pool.size());
    }
}