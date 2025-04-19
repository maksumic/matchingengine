package dev.sorn.matchingengine.desktop;

import dev.sorn.matchingengine.desktop.style.P;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(String title) {
        super(title);
        super.setLayout(null);
        super.setResizable(false);
        super.setSize(P.X_MAX, P.Y_MAX);
        super.setMinimumSize(new Dimension(P.X_MAX, P.Y_MAX));
        super.setMaximumSize(new Dimension(P.X_MAX, P.Y_MAX));
        final var commandBar = new CommandBar();
        super.add(commandBar);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setVisible(true);
        super.setFocusable(false);
        super.setFocusTraversalKeysEnabled(false);
        Logger.info(this.getClass().getSimpleName() + " initialized.");
    }
}