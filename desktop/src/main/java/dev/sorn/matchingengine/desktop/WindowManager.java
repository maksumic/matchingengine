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

    public WindowManager(Window window) {
        this.window = window;
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
        int cols = 2;
        int rows = 2;
        int index = 0;
        int availableW = window.getWidth();
        int availableH = window.getHeight();
        int w = availableW / cols;
        int h = availableH / rows - P.Y16;
        for (final var panel : views.values()) {
            int col = index % cols;
            int row = index / cols;
            int x = col * w;
            int y = row * h;
            panel.setBounds(x, y, w, h);
            index++;
        }
    }
}