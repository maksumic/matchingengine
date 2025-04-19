package dev.sorn.matchingengine.desktop;

import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class BlockCaret extends DefaultCaret {
    @Override
    protected synchronized void damage(Rectangle r) {
        if (r == null) {
            return;
        }
        x = r.x;
        y = r.y;
        width = getComponent().getFontMetrics(getComponent().getFont()).charWidth('W'); // full-width block
        height = r.height;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        if (isVisible()) {
            JTextComponent comp = getComponent();
            try {
                Rectangle r = comp.modelToView(getDot());
                g.setColor(comp.getCaretColor());
                g.fillRect(r.x, r.y, width, r.height); // block instead of line
            } catch (Exception ignored) {}
        }
    }
}