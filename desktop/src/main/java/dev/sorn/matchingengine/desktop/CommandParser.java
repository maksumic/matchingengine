package dev.sorn.matchingengine.desktop;

import dev.sorn.matchingengine.CurrencyCache;
import dev.sorn.matchingengine.CurrencyPair;
import dev.sorn.matchingengine.desktop.cmd.HideOrderBookCommand;
import dev.sorn.matchingengine.desktop.cmd.ShowOrderBookCommand;

public final class CommandParser {
    Command parse(String input) {
        if (input == null || input.isBlank()) {
            return error("unrecognized command: none");
        }
        if (input.startsWith("show ")) {
            return parseShow(input);
        }
        if (input.startsWith("hide ")) {
            return parseHide(input);
        }
        return error("unrecognized command: " + input);
    }

    private Command error(String input) {
        return () -> new CommandResult(false, input);
    }

    private Command parseShow(String input) {
        final var parts1 = input.split(" ");
        if (parts1.length != 2) {
            return error("show: expects a currency pair, e.g., BTC/USD");
        }
        final var parts2 = parts1[1].split("/");
        if (parts2.length != 2) {
            return error("show: expects a currency pair, e.g., BTC/USD");
        }
        final var base = CurrencyCache.get(parts2[0].toUpperCase());
        if (base == null) {
            return error("show: invalid base currency: " + parts2[0]);
        }
        final var quote = CurrencyCache.get(parts2[1].toUpperCase());
        if (quote == null) {
            return error("show: invalid quote currency: " + parts2[1]);
        }
        final var pair = new CurrencyPair(base, quote);
        return new ShowOrderBookCommand(pair);
    }

    private Command parseHide(String input) {
        final var parts1 = input.split(" ");
        if (parts1.length != 2) {
            return error("hide: expects a currency pair, e.g., BTC/USD");
        }
        final var parts2 = parts1[1].split("/");
        if (parts2.length != 2) {
            return error("hide: expects a currency pair, e.g., BTC/USD");
        }
        final var base = CurrencyCache.get(parts2[0].toUpperCase());
        if (base == null) {
            return error("hide: invalid base currency: " + parts2[0]);
        }
        final var quote = CurrencyCache.get(parts2[1].toUpperCase());
        if (quote == null) {
            return error("hide: invalid quote currency: " + parts2[1]);
        }
        final var pair = new CurrencyPair(base, quote);
        return new HideOrderBookCommand(pair);
    }
}