package dev.sorn.matchingengine.desktop.cmd;

import dev.sorn.matchingengine.CurrencyPair;
import dev.sorn.matchingengine.desktop.Command;
import dev.sorn.matchingengine.desktop.CommandResult;

public class HideOrderBookCommand implements Command {
    private final CurrencyPair pair;

    public HideOrderBookCommand(CurrencyPair pair) {
        this.pair = pair;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(true, "hide: " + pair.toString());
    }
}