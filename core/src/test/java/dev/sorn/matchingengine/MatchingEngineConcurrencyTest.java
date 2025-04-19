package dev.sorn.matchingengine;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MatchingEngineConcurrencyTest {
    @Nested
    class Insert {
        @Test public void insert_concurrentOrdersAcrossSymbols_returnsTrue_andPreservesBookIsolation() {}
        @Test public void insert_concurrentOrdersWithSameId_onlyFirstReturnsTrue_andOthersReturnFalse() {}
    }

    @Nested
    class Pull {
        @Test public void pull_concurrentOrdersAcrossSymbols_returnsTrue_andDoesNotLeakState() {}
    }

    @Nested
    class Mixed {
        @Test public void mixed_concurrentInsertAmendPullOnSameOrder_returnsConsistentFinalState() {}
    }
}