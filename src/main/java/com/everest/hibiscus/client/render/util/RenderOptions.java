package com.everest.hibiscus.client.render.util;

public class RenderOptions {

    public float size = 1f;
    public float maxAlpha = 1f;
    public int fadeInTicks = 0;
    public int fadeOutTicks = 0;
    public AnimationStyle animation = AnimationStyle.NONE;
    public float animationSpeed = 1f;

    public RenderOptions() {}

    public RenderOptions size(float size) { this.size = size; return this; }
    public RenderOptions fadeIn(int ticks) { this.fadeInTicks = ticks; return this; }
    public RenderOptions fadeOut(int ticks) { this.fadeOutTicks = ticks; return this; }
    public RenderOptions maxAlpha(float alpha) { this.maxAlpha = alpha; return this; }
    public RenderOptions animation(AnimationStyle style, float speed) { this.animation = style; this.animationSpeed = speed; return this; }

    public enum AnimationStyle {
        NONE,       // Static
        SPIN,       // Rotates beam around Y-axis
        PULSE,      // Alpha pulsates
        WOBBLE      // Horizontal sine-wave distortion
    }
}
