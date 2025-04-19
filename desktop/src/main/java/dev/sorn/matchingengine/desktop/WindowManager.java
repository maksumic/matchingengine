package dev.sorn.matchingengine.desktop;

import dev.sorn.matchingengine.CurrencyPair;
import dev.sorn.matchingengine.desktop.style.Color;
import dev.sorn.matchingengine.desktop.style.Font;
import dev.sorn.matchingengine.desktop.style.P;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class WindowManager {
    private static final int MAX_WINDOWS = 4;
    private final Window window;
    private final Map<CurrencyPair, JPanel> views = new LinkedHashMap<>();
    private CurrencyPair selected = null;

    public WindowManager(Window window) {
        this.window = window;
    }

    public CommandResult select(CurrencyPair pair) {
        if (!views.containsKey(pair)) {
            return new CommandResult(false, "select: " + pair + " is not visible");
        }
        selected = pair;
        layoutPanels();
        window.repaint();
        return new CommandResult(true, "select: " + pair);
    }

    public CommandResult show(CurrencyPair pair) {
        if (views.containsKey(pair)) {
            return new CommandResult(false, "show: " + pair + " is already visible");
        }
        if (views.size() == MAX_WINDOWS) {
            return new CommandResult(false, "show: maximum of 4 order books reached");
        }
        final var panel = createPanel(pair);
        views.put(pair, panel);
        selected = pair;
        layoutPanels();
        window.add(panel);
        window.repaint();
        return new CommandResult(true, "show: " + pair);
    }

    public CommandResult hide(CurrencyPair pair) {
        if (!views.containsKey(pair)) {
            return new CommandResult(false, "hide: " + pair + " not found");
        }
        final var panel = views.remove(pair);
        window.remove(panel);
        if (pair.equals(selected)) {
            selected = views.keySet().stream().findFirst().orElse(null);
        }
        layoutPanels();
        window.repaint();
        return new CommandResult(true, "hide: " + pair);
    }

    public CommandResult quit() {
        System.exit(0);
        return new CommandResult(false, "quitting...");
    }

    private JPanel createPanel(CurrencyPair pair) {
        final var titledBorder = BorderFactory.createTitledBorder(pair.toString());
        titledBorder.setTitleFont(Font.TEXT_PRIMARY);
        titledBorder.setTitleColor(Color.GRAY_DARK);
        final var panel = new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setBorder(titledBorder);
        return panel;
    }

    private void layoutPanels() {
        int count = views.size();
        int width = window.getWidth();
        int height = window.getHeight() - P.Y32 * 2; // exclude command bar
        int index = 0;
        for (final var e : views.entrySet()) {
            var pair = e.getKey();
            var panel = e.getValue();
            int x = 0, y = 0, w = width, h = height;
            switch (count) {
                case 1 -> {
                    // Full screen
                    x = 0;
                    y = P.Y32;
                    w = width;
                    h = height;
                }
                case 2 -> {
                    // Horizontal split
                    w = width / 2;
                    h = height;
                    x = index * w;
                    y = P.Y32;
                }
                case 3, 4 -> {
                    // 2x2 grid
                    w = width / 2;
                    h = height / 2;
                    x = (index % 2) * w;
                    y = P.Y32 + (index / 2) * h;
                }
            }
            final var border = BorderFactory.createTitledBorder(pair.toString());
            if (pair.equals(selected)) {
                border.setTitleColor(Color.WHITE);
                border.setTitleFont(Font.MONO_BOLD_12);
                panel.setBorder(border);
            } else {
                border.setTitleColor(Color.GRAY_DARK);
                border.setTitleFont(Font.MONO_PLAIN_12);
                panel.setBorder(border);
            }
            panel.setBounds(x, y, w, h);
            index++;
        }
    }
}