package dev.sorn.matchingengine;

@FunctionalInterface
interface OrderFactory {
    Order create(long id, CurrencyPair pair, OrderType type, OrderSide side, int quantity, long price, long timestamp);

    default void recycle(Order order) {
        throw new UnsupportedOperationException();
    }
}