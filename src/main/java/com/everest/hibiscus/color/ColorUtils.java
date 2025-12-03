package com.everest.hibiscus.color;

import java.awt.*;

public class ColorUtils {
    // Convert RGB (0–255) to Hex
    public static String rgbToHex(int r, int g, int b) {
        return String.format("#%02X%02X%02X", clamp(r), clamp(g), clamp(b));
    }

    // Convert Hex to Color
    public static Color hexToColor(String hex) {
        return Color.decode(hex);
    }

    // Convert RGB to HSL
    public static float[] rgbToHSL(int r, int g, int b) {
        float[] hsl = new float[3];
        Color.RGBtoHSB(clamp(r), clamp(g), clamp(b), hsl);
        return hsl;
    }

    // Brightness adjustment
    public static Color adjustBrightness(Color color, float factor) {
        int r = clamp((int)(color.getRed() * factor));
        int g = clamp((int)(color.getGreen() * factor));
        int b = clamp((int)(color.getBlue() * factor));
        return new Color(r, g, b, color.getAlpha());
    }

    // Alpha blending (0.0–1.0 alpha)
    public static Color blend(Color base, Color overlay, float alpha) {
        float invAlpha = 1 - alpha;
        int r = clamp((int)(base.getRed() * invAlpha + overlay.getRed() * alpha));
        int g = clamp((int)(base.getGreen() * invAlpha + overlay.getGreen() * alpha));
        int b = clamp((int)(base.getBlue() * invAlpha + overlay.getBlue() * alpha));
        return new Color(r, g, b);
    }

    // Interpolate between two colors
    public static Color lerpColor(Color c1, Color c2, float t) {
        int r = clamp((int)(c1.getRed() + (c2.getRed() - c1.getRed()) * t));
        int g = clamp((int)(c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t));
        int b = clamp((int)(c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t));
        return new Color(r, g, b);
    }

    // Make a color transparent
    public static Color setAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), clamp(alpha));
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}