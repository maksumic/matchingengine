package dev.sorn.matchingengine.desktop;

import dev.sorn.matchingengine.desktop.style.P;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CommandBar extends JTextField {
    private boolean commandMode;

    public CommandBar() {
        super.setEditable(false);
        super.setSize(P.X_MAX, P.Y32);
        super.setFocusable(true);
        super.setRequestFocusEnabled(true);
        super.requestFocusInWindow();
        super.addKeyListener(commandModeKeyEventListener());
    }

    private void enterCommandMode() {
        if (commandMode) {
            return;
        }
        Logger.info("Entered command mode.");
        commandMode = true;
    }

    private void exitCommandMode() {
        if (!commandMode) {
            return;
        }
        Logger.info("Exited command mode.");
        commandMode = false;
    }

    private KeyAdapter commandModeKeyEventListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_BACK_QUOTE -> enterCommandMode();
                    case KeyEvent.VK_ESCAPE -> exitCommandMode();
                    default -> super.keyPressed(e);
                }
            }
        };
    }
}