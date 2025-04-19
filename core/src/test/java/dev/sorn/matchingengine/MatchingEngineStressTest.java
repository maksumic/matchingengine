package dev.sorn.matchingengine;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MatchingEngineStressTest {
    @Nested
    class Insert {
        @Test public void insert_burstOfBuyOrders_returnsTrue_andMaintainsBestBidContinuity() {}
        @Test public void insert_burstOfSellOrders_returnsTrue_andMaintainsBestAskContinuity() {}
        @Test public void insert_alternatingBuyAndSellOrders_returnsTrue_andExecutesMatchesWithCorrectPricesAndQuantities() {}
    }

    @Nested
    class Amend {
        @Test public void amend_bulkOrdersAcrossMultipleOrders_returnsTrue_andMaintainsBookIntegrity() {}
    }

    @Nested
    class Pull {
        @Test public void pull_bulkOrders_returnsTrue_andMaintainsCorrectBookState() {}
    }
}