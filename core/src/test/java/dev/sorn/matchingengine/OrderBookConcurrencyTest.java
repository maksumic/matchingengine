package dev.sorn.matchingengine;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class OrderBookConcurrencyTest {
    @Nested
    class Insert {
        @Test public void insert_concurrentBuyAndSellOrders_returnsTrue_andMatchesInCorrectOrder() {}
        @Test public void insert_concurrentBuyOrdersAtSamePrice_returnsTrue_andPreservesPriceTimePriority() {}
        @Test public void insert_concurrentSellOrdersAtSamePrice_returnsTrue_andPreservesPriceTimePriority() {}
    }

    @Nested
    class Amend {
        @Test public void amend_concurrentAmendsOnSameOrder_returnsTrue_andAppliesLastWriteWins() {}
    }

    @Nested
    class Pull {
        @Test public void pull_concurrentPullsOnSameOrder_onlyOneReturnsTrue_andPreservesBookState() {}
    }
}
