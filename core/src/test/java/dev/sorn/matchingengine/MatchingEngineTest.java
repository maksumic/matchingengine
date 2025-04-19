package dev.sorn.matchingengine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class MatchingEngineTest {
    @Nested
    class Insert {
        // <editor-fold desc="Success">
        @Test public void insert_orderIntoEmptyBook_returnsTrue_andRests() {}
        @Test public void insert_ordersWithDifferentSymbols_returnsTrue_andRoutesToCorrectBook() {}
        // </editor-fold>

        // <editor-fold desc="Failure">
        @Test public void insert_invalidSymbol_returnsFalse() {}
        @Test public void insert_invalidOrderId_returnsFalse_andDoesNotMutateBook() {}
        @Test public void insert_duplicateOrderId_returnsFalse_andDoesNotMutateBook() {}
        @Test public void insert_zeroOrNegativeQuantity_returnsFalse_andDoesNotMutateBook() {}
        @Test public void insert_zeroOrNegativePrice_returnsFalse_andDoesNotMutateBook() {}
        // </editor-fold>
    }

    @Nested
    class Amend {
        // <editor-fold desc="Failure">
        @Test public void amend_invalidOrderId_returnsFalse_andDoesNotMutateBook() {}
        @Test public void amend_zeroOrNegativeQuantity_returnsFalse_andDoesNotMutateBook() {}
        @Test public void amend_zeroOrNegativePrice_returnsFalse_andDoesNotMutateBook() {}
        @Test public void amend_filledOrder_returnsFalse_andDoesNotMutateBook() {}
        @Test public void amend_beforeInsert_returnsFalse_andDoesNotMutateBook() {}
        // </editor-fold>
    }

    @Nested
    class Pull {
        // <editor-fold desc="Success">
        @Test public void pull_existingOrder_returnsTrue_andRemovesOrder() {}
        @Test public void pull_partiallyFilledOrder_returnsTrue_andRemovesUnfilledQuantity() {}
        // </editor-fold>

        // <editor-fold desc="Failure">
        @Test public void pull_invalidOrderId_returnsFalse_andDoesNotMutateBook() {}
        @Test public void pull_sameOrderTwice_returnsFalse_andDoesNotMutateBook() {}
        @Test public void pull_beforeInsert_returnsFalse_andDoesNotMutateBook() {}
        // </editor-fold>
    }

    @Nested
    class ViewOf {
        @Test public void viewOf_returnsImmutableView_andExternalModificationThrowsException() {}

        @Test
        public void viewOf_unknownCurrencyPair_throwsIllegalArgumentException() {
            final var currencyPair = new CurrencyPair(new Currency("BTC", 8), new Currency("USD", 2));
            final var engine = new MatchingEngine(Set.of());
            final var e = Assertions.assertThrows(IllegalArgumentException.class, () -> engine.viewOf(currencyPair));
            Assertions.assertEquals("Unsupported currency pair: " + currencyPair, e.getMessage());
        }
    }
}