package dev.sorn.matchingengine;

import java.util.regex.Pattern;

public record Currency(String code, int minorUnit) {
    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z0-9]{2,10}$");

    public Currency {
        if (code == null || !CODE_PATTERN.matcher(code).matches()) {
            throw new IllegalArgumentException("Currency code must be 2–10 uppercase letters or digits (e.g., USD, BTC, USDT), but was: " + code);
        }
        if (minorUnit < 0 || minorUnit > 18) {
            throw new IllegalArgumentException("Minor unit must be between 0 and 18 (inclusive), but was: " + minorUnit + " for currency: " + code);
        }
    }

    @Override
    public String toString() {
        return code;
    }
}