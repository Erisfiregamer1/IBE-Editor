package com.github.franckyi.guapi.util;

public final class Color {
    public static int rgb(int r, int g, int b) {
        return rgba(r, g, b, 0xff);
    }

    public static int rgba(int r, int g, int b, int a) {
        checkValues(r, g, b, a);
        return (a & 0xff) << 24 |
                (r & 0xff) << 16 |
                (g & 0xff) << 8 |
                b & 0xff;
    }

    private static void checkValues(int r, int g, int b, int a) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255 || a < 0 || a > 255) {
            throw new IllegalArgumentException("RGBA values must be between 0 and 255");
        }
    }
}