package dev.sorn.matchingengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class OrderBookTest {
    private static final Currency BTC = new Currency("BTC", 8);
    private static final Currency USD = new Currency("USD", 2);
    private static final CurrencyPair BTC_USD = new CurrencyPair(BTC, USD);
    private static final OrderFactory ORDER_FACTORY = new PooledOrderFactory(100);

    @Nested
    class Insert {
        // <editor-fold desc="Buy orders">
        @Test
        public void insert_buyOrder_returnsTrue_andUpdatesBestBid() {
            final var book = new OrderBook(ORDER_FACTORY, BTC_USD);
            Assertions.assertNull(book.bestBid());
            final var inserted = book.insert(42, BTC_USD, OrderType.LIMIT, OrderSide.BUY, 100, 100_000_00, 1);
            Assertions.assertTrue(inserted);
            final var bestBid = book.bestBid();
            Assertions.assertNotNull(bestBid, "bids were not updated");
            Assertions.assertEquals(42, bestBid.id, "wrong best bid ID");
            Assertions.assertEquals(BTC_USD, bestBid.pair);
            Assertions.assertEquals(OrderType.LIMIT, bestBid.type);
            Assertions.assertEquals(OrderSide.BUY, bestBid.side);
            Assertions.assertEquals(100, bestBid.quantity);
            Assertions.assertEquals(100_000_00, bestBid.price);
            Assertions.assertEquals(1, bestBid.timestamp);
        }

        @Test public void insert_buyOrderAtBetterPrice_returnsTrue_andRanksAheadOfRestingBuys() {}
        @Test public void insert_buyOrderCrossingAsk_returnsTrue_andPartiallyMatchesRestingSell_andRestsWithRemainingQuantity() {}
        @Test public void insert_buyOrderCrossingAsk_returnsTrue_andEmitsTradeEvent_withMatchedQuantityAndPrice() {}
        @Test public void insert_buyOrderCrossingMultipleAsks_returnsTrue_andMatchesInPriceTimePriorityOrder() {}
        @Test public void insert_multipleBuysSamePrice_returnsTrue_andPreservesPriceTimePriority() {}
        // </editor-fold>

        // <editor-fold desc="Sell orders">
        @Test public void insert_sellOrder_returnsTrue_andUpdatesBestAsk() {
            final var book = new OrderBook(ORDER_FACTORY, BTC_USD);
            Assertions.assertNull(book.bestAsk());
            final var inserted = book.insert(42, BTC_USD, OrderType.LIMIT, OrderSide.SELL, 100, 100_000_00, 1);
            Assertions.assertTrue(inserted);
            final var bestAsk = book.bestAsk();
            Assertions.assertNotNull(bestAsk, "asks were not updated");
            Assertions.assertEquals(42, bestAsk.id, "wrong best ask ID");
            Assertions.assertEquals(BTC_USD, bestAsk.pair);
            Assertions.assertEquals(OrderType.LIMIT, bestAsk.type);
            Assertions.assertEquals(OrderSide.SELL, bestAsk.side);
            Assertions.assertEquals(100, bestAsk.quantity);
            Assertions.assertEquals(100_000_00, bestAsk.price);
            Assertions.assertEquals(1, bestAsk.timestamp);
        }

        @Test public void insert_sellOrderAtBetterPrice_returnsTrue_andRanksAheadOfRestingSells() {}
        @Test public void insert_sellOrderCrossingBid_returnsTrue_andPartiallyMatchesRestingBuy_andRestsWithRemainingQuantity() {}
        @Test public void insert_sellOrderCrossingBid_returnsTrue_andEmitsTradeEvent_withMatchedQuantityAndPrice() {}
        @Test public void insert_sellOrderCrossingMultipleBids_returnsTrue_andMatchesInPriceTimePriorityOrder() {}
        @Test public void insert_multipleSellsSamePrice_returnsTrue_andPreservesPriceTimePriority() {}
        // </editor-fold>

        // <editor-fold desc="Neutral and edge cases">
        @Test public void insert_ordersBeyondCapacity_returnsFalse_andDoesNotMutateBook() {}
        // </editor-fold>
    }

    @Nested
    class Amend {
        // <editor-fold desc="Buy orders">
        @Test public void amend_toHigherBuyPrice_returnsTrue_andRanksAheadOfLowerBuys() {}
        @Test public void amend_buyOrderToCrossingAsk_returnsTrue_andEmitsTradeEvent_withMatchedQuantityAndPrice() {}
        // </editor-fold>

        // <editor-fold desc="Sell orders">
        @Test public void amend_toLowerSellPrice_returnsTrue_andRanksAheadOfHigherSells() {}
        @Test public void amend_sellOrderToCrossingBid_returnsTrue_andEmitsTradeEvent_withMatchedQuantityAndPrice() {}
        // </editor-fold>

        // <editor-fold desc="Quantity changes">
        @Test public void amend_decreaseQuantityAtSamePrice_returnsTrue_andPreservesPriority() {}
        @Test public void amend_increaseQuantityAtSamePrice_returnsTrue_andLosesPriority() {}
        // </editor-fold>

        // <editor-fold desc="Price changes">
        @Test public void amend_changePrice_returnsTrue_andRestoresPriceTimePriority() {}
        @Test public void amend_nonCrossingPrice_returnsTrue_andRestsAtNewPrice() {}
        // </editor-fold>

        // <editor-fold desc="Neutral and edge cases">
        @Test public void amend_noEffectiveChange_returnsTrue_andHasNoSideEffect() {}
        @Test public void amend_partiallyFilledOrder_returnsTrue_andUpdatesRemainingQuantity() {}
        // </editor-fold>
    }

    @Nested
    class Pull {
        // <editor-fold desc="Position removal">
        @Test public void pull_nonFirstOrderAtPrice_returnsTrue_andPreservesOtherOrders() {}
        @Test public void pull_middleOrder_returnsTrue_andPreservesOtherOrders() {}
        @Test public void pull_middleOrder_returnsTrue_andDoesNotTriggerMatchOrReorder() {}
        // </editor-fold>

        // <editor-fold desc="Position promotion">
        @Test public void pull_bestBidOrder_returnsTrue_andPromotesNextBid_andUpdatesBestBid() {}
        @Test public void pull_bestAskOrder_returnsTrue_andPromotesNextAsk_andUpdatesBestAsk() {}
        // </editor-fold>
    }
}