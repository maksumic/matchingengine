package dev.sorn.matchingengine.desktop;

@FunctionalInterface
public interface Command {
    CommandResult execute();
}