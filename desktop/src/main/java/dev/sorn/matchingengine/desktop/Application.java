package dev.sorn.matchingengine.desktop;

import javax.swing.*;

/**
 * Matching engine desktop application used for manual testing.
 */
public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Window("Matching Engine - Manual Testing Environment");
        });
    }
}