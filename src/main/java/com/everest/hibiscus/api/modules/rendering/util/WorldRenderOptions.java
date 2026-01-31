package com.everest.hibiscus.api.modules.rendering.util;

public class WorldRenderOptions {

    public float size = 1f;
    public float maxAlpha = 1f;
    public int fadeInTicks = 0;
    public int fadeOutTicks = 0;

    public AnimationStyle animation = AnimationStyle.NONE;
    public float animationSpeed = 1f;


    public float uvScroll = 0f;

    public WorldRenderOptions() {}

    public WorldRenderOptions size(float size) {
        this.size = size;
        return this;
    }

    public WorldRenderOptions fadeIn(int ticks) {
        this.fadeInTicks = ticks;
        return this;
    }

    public WorldRenderOptions fadeOut(int ticks) {
        this.fadeOutTicks = ticks;
        return this;
    }

    public WorldRenderOptions maxAlpha(float alpha) {
        this.maxAlpha = alpha;
        return this;
    }

    public WorldRenderOptions animation(AnimationStyle style, float speed) {
        this.animation = style;
        this.animationSpeed = speed;
        return this;
    }


    public WorldRenderOptions uvScroll(float speed) {
        this.uvScroll = speed;
        return this;
    }

    public enum AnimationStyle {
        NONE,       // Static
        SPIN,       // Rotates around Y-axis
        PULSE,      // Alpha pulsates
        WOBBLE      // Horizontal sine-wave distortion
    }
}
