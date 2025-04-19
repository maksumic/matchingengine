package dev.sorn.matchingengine.desktop;

import dev.sorn.matchingengine.CurrencyCache;
import dev.sorn.matchingengine.CurrencyPair;

import java.util.function.Function;

public final class CommandParser {
    private final WindowManager manager;

    public CommandParser(WindowManager manager) {
        this.manager = manager;
    }

    public Command parse(String input) {
        if (input == null || input.isBlank()) {
            return error("unrecognized command: none");
        }
        input = input.trim();
        if (input.equals("quit")) {
            return manager::quit;
        }
        if (input.startsWith("select")) {
            return parseSelect(input);
        }
        if (input.startsWith("swap")) {
            return parseSwap(input);
        }
        if (input.startsWith("show")) {
            return parseShow(input);
        }
        if (input.startsWith("hide")) {
            return parseHide(input);
        }
        return error("unrecognized command: " + input);
    }

    private Command parseSelect(String input) {
        return parseCurrencyPair(manager::select, input);
    }

    private Command parseSwap(String input) {
        return parseDoublePair(input, pairs -> () -> manager.swap(pairs[0], pairs[1]));
    }

    private Command parseShow(String input) {
        return parseCurrencyPair(manager::show, input);
    }

    private Command parseHide(String input) {
        return parseCurrencyPair(manager::hide, input);
    }

    private Command parseCurrencyPair(Function<CurrencyPair, CommandResult> f, String input) {
        return parseSinglePair(input, pair -> () -> f.apply(pair));
    }

    private Command parseSinglePair(String input, Function<CurrencyPair, Command> onValid) {
        final var parts = input.trim().split(" ");
        if (parts.length != 2) {
            return error("expects a currency pair, e.g., BTC/USD");
        }
        final var pair = toPair(parts[1]);
        if (pair == null) {
            return error("invalid currency pair: " + parts[1]);
        }
        return onValid.apply(pair);
    }

    private Command parseDoublePair(String input, Function<CurrencyPair[], Command> onValid) {
        final var parts = input.trim().split(" ");
        if (parts.length != 3) {
            return error("expects two currency pairs, e.g., swap BTC/USD ETH/USD");
        }
        final var pair1 = toPair(parts[1]);
        final var pair2 = toPair(parts[2]);
        if (pair1 == null || pair2 == null) {
            return error("invalid currency pair(s): " + parts[1] + " " + parts[2]);
        }
        return onValid.apply(new CurrencyPair[]{pair1, pair2});
    }

    private CurrencyPair toPair(String symbol) {
        final var parts = symbol.split("/");
        if (parts.length != 2) return null;
        final var base = CurrencyCache.get(parts[0].toUpperCase());
        final var quote = CurrencyCache.get(parts[1].toUpperCase());
        if (base == null || quote == null) return null;
        try {
            return new CurrencyPair(base, quote);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Command error(String input) {
        return () -> new CommandResult(false, input);
    }
}