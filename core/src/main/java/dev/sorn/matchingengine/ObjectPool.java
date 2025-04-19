package dev.sorn.matchingengine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Supplier;

public class ObjectPool<T> {
    private final Deque<T> pool;
    private final Supplier<T> allocator;
    private final int capacity;

    public ObjectPool(Supplier<T> allocator, int capacity) {
        this.allocator = allocator;
        this.capacity = capacity;
        this.pool = new ArrayDeque<>(capacity);
    }

    public T acquire() {
        final T obj = pool.pollFirst();
        if (obj != null) {
            return obj;
        }
        return allocator.get();
    }

    public void release(T obj) {
        if (pool.size() < capacity) {
            pool.addFirst(obj);
        }
    }

    public int size() {
        return pool.size();
    }
}
