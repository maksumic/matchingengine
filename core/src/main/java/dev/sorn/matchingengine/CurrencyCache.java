package dev.sorn.matchingengine;

public final class CurrencyCache {
    public static Currency get(String code) {
        return switch (code) {
            case "BTC" -> new Currency("BTC", 8);
            case "ETH" -> new Currency("ETH", 18);
            case "SOL" -> new Currency("SOL", 9);
            case "USD" -> new Currency("USD", 2);
            default -> null;
        };
    }
}