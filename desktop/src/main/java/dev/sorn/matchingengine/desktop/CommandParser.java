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
        if (input.startsWith("show")) {
            return parseShow(input);
        }
        if (input.startsWith("hide")) {
            return parseHide(input);
        }
        return error("unrecognized command: " + input);
    }

    private Command error(String input) {
        return () -> new CommandResult(false, input);
    }

    private Command parseSelect(String input) {
        return parseCurrencyPair(manager::select, input);
    }

    private Command parseShow(String input) {
        return parseCurrencyPair(manager::show, input);
    }

    private Command parseHide(String input) {
        return parseCurrencyPair(manager::hide, input);
    }

    private Command parseCurrencyPair(Function<CurrencyPair, CommandResult> f, String input) {
        final var prefix = "show: ";
        final var parts1 = input.split(" ");
        if (parts1.length != 2) {
            return error(prefix + "expects a currency pair, e.g., BTC/USD");
        }
        final var parts2 = parts1[1].split("/");
        if (parts2.length != 2) {
            return error(prefix + "expects a currency pair, e.g., BTC/USD");
        }
        final var base = CurrencyCache.get(parts2[0].toUpperCase());
        if (base == null) {
            return error(prefix + "invalid base currency: " + parts2[0]);
        }
        final var quote = CurrencyCache.get(parts2[1].toUpperCase());
        if (quote == null) {
            return error(prefix + "invalid quote currency: " + parts2[1]);
        }
        try {
            final var pair = new CurrencyPair(base, quote);
            return () -> f.apply(pair);
        } catch (IllegalArgumentException e) {
            return error(prefix + "invalid currency pair");
        }
    }
}