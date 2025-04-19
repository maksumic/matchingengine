package dev.sorn.matchingengine;

public class PooledOrderFactory implements OrderFactory {
    private final ObjectPool<Order> pool;

    public PooledOrderFactory(int capacity) {
        pool = new ObjectPool<>(Order::new, capacity);
    }

    @Override
    public Order create(long id, CurrencyPair pair, OrderType type, OrderSide side, int quantity, long price, long timestamp) {
        final var o = pool.acquire();
        o.clear();
        o.id = id;
        o.pair = pair;
        o.type = type;
        o.side = side;
        o.quantity = quantity;
        o.price = price;
        o.timestamp = timestamp;
        return o;
    }

    @Override
    public void recycle(Order o) {
        pool.release(o);
    }
}