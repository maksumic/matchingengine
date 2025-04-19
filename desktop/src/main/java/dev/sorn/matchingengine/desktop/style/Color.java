package dev.sorn.matchingengine.desktop.style;

public class Color extends java.awt.Color {
    // region Base colors
    public static final Color BLACK         = new Color(0, 0, 0);
    public static final Color WHITE         = new Color(255, 255, 255);
    public static final Color RED           = new Color(255, 0, 0);
    public static final Color GREEN         = new Color(0, 255, 0);
    public static final Color BLUE          = new Color(0, 0, 170);
    public static final Color CYAN          = new Color(0, 255, 255);
    public static final Color MAGENTA       = new Color(255, 0, 255);
    public static final Color YELLOW        = new Color(255, 255, 0);
    public static final Color GRAY_DARK     = new Color(85, 85, 85);
    public static final Color GRAY_LIGHT    = new Color(170, 170, 170);
    // endregion

    // region Semantic aliases
    public static final Color BACKGROUND       = BLUE;
    public static final Color TEXT_PRIMARY     = WHITE;
    public static final Color TEXT_SECONDARY   = GRAY_LIGHT;
    public static final Color BID_FILL         = GREEN;
    public static final Color ASK_FILL         = RED;
    public static final Color GRID_LINE        = GRAY_DARK;
    public static final Color BORDER           = GRAY_LIGHT;
    public static final Color HIGHLIGHT        = CYAN;
    public static final Color DISABLED_TEXT    = GRAY_DARK;
    // endregion

    public static final int ALPHA_ENABLED = 255;
    public static final int ALPHA_DISABLED = 150;

    private Color(int r, int g, int b) {
        super(r, g, b);
    }

    private Color(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    public Color enabled() {
        return new Color(getRed(), getGreen(), getBlue(), ALPHA_ENABLED);
    }

    public Color disabled() {
        return new Color(getRed(), getGreen(), getBlue(), ALPHA_DISABLED);
    }
}