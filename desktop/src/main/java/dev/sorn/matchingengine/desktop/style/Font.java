package dev.sorn.matchingengine.desktop.style;

public class Font extends java.awt.Font {
    // region Base Fonts
    public static final Font MONO_PLAIN_12   = new Font("Monospaced", PLAIN, 12);
    public static final Font MONO_BOLD_12    = new Font("Monospaced", BOLD, 12);
    public static final Font MONO_PLAIN_14   = new Font("Monospaced", PLAIN, 14);
    public static final Font MONO_BOLD_14    = new Font("Monospaced", BOLD, 14);
    // endregion

    // region Semantic Fonts
    public static final Font TEXT_PRIMARY    = MONO_PLAIN_12;
    public static final Font TEXT_HIGHLIGHT  = MONO_BOLD_12;
    public static final Font TITLE           = MONO_BOLD_14;
    // endregion

    private Font(String name, int style, int size) {
        super(name, style, size);
    }
}