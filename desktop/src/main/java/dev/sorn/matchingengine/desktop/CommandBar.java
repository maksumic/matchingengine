package dev.sorn.matchingengine.desktop;

import dev.sorn.matchingengine.desktop.style.Color;
import dev.sorn.matchingengine.desktop.style.Font;
import dev.sorn.matchingengine.desktop.style.P;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public final class CommandBar extends JTextField {
    private final CommandParser parser;
    private boolean commandMode;

    public CommandBar(CommandParser parser) {
        super.setBackground(Color.BLUE);
        super.setCaretColor(Color.WHITE);
        super.setFont(Font.TEXT_PRIMARY);
        super.setEditable(false);
        super.setSize(P.X_MAX, P.Y32);
        super.setFocusable(true);
        super.setRequestFocusEnabled(true);
        super.requestFocusInWindow();
        super.addKeyListener(commandModeKeyEventListener());
        super.setCaret(new BlockCaret());
        this.parser = parser;
    }

    private void handleCommand() {
        if (!commandMode) {
            return;
        }
        final var command = parser.parse(super.getText());
        final var result = command.execute();
        if (result.success()) {
            exitCommandMode();
            this.write(result.message());
        } else {
            exitCommandMode();
            this.writeError(result.message());
        }
    }

    private void enterCommandMode() {
        if (commandMode) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            this.write("");
            super.setFont(Font.TEXT_PRIMARY);
            super.getCaret().setBlinkRate(500);
            super.setCaretColor(Color.WHITE);
            this.setEditable(true);
        });
        commandMode = true;
    }

    private void exitCommandMode() {
        if (!commandMode) {
            return;
        }
        this.setEditable(false);
        this.getCaret().setBlinkRate(0);
        this.write("Exited command mode.");
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
                    case KeyEvent.VK_ENTER -> handleCommand();
                    default -> super.keyPressed(e);
                }
            }
        };
    }

    private void write(String text) {
        super.setFont(Font.TEXT_HIGHLIGHT);
        super.setForeground(Color.WHITE);
        super.setCaretColor(Color.GRAY_LIGHT);
        super.setText(text);
    }

    private void writeError(String text) {
        super.setFont(Font.TEXT_HIGHLIGHT);
        super.setForeground(Color.YELLOW);
        super.setCaretColor(Color.RED);
        super.setText(text);
    }
}