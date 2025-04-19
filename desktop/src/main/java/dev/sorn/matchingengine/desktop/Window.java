package dev.sorn.matchingengine.desktop;

import dev.sorn.matchingengine.desktop.style.P;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window(String title) {
        super(title);
        super.setLayout(null);
        super.setResizable(false);
        super.setSize(P.X_MAX, P.Y_MAX);
        super.setBackground(Color.BLACK);
        super.setMinimumSize(new Dimension(P.X_MAX, P.Y_MAX));
        super.setMaximumSize(new Dimension(P.X_MAX, P.Y_MAX));
        super.setUndecorated(true);
        final var orderBookManager = new WindowManager(this);
        final var commandParser = new CommandParser(orderBookManager);
        final var commandBar = new CommandBar(commandParser);
        super.add(commandBar);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setVisible(true);
        super.setFocusable(false);
        super.setFocusTraversalKeysEnabled(false);
        Logger.info(this.getClass().getSimpleName() + " initialized.");
    }
}