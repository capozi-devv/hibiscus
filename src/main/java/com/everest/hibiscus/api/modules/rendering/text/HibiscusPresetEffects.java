package com.everest.hibiscus.api.modules.rendering.text;

import com.everest.hibiscus.api.modules.rendering.text.effects.DoubleLerpWaveEffect;
import com.everest.hibiscus.api.modules.rendering.text.effects.LerpWaveEffect;
import com.everest.hibiscus.api.modules.rendering.text.effects.PixelWaveEffect;
import com.everest.hibiscus.api.modules.rendering.text.registry.TextEffectManager;

public class HibiscusPresetEffects {
    public static final String PIXEL_WAVE_EFFECT = "pixel_wave";
    public static final String LERP_WAVE_EFFECT = "lerp_wave";
    public static final String DOUBLE_LERP_WAVE_EFFECT = "double_lerp_wave";

    public static void init() {
        TextEffectManager.registerEffect(PIXEL_WAVE_EFFECT, new PixelWaveEffect());
        TextEffectManager.registerEffect(LERP_WAVE_EFFECT, new LerpWaveEffect());
        TextEffectManager.registerEffect(DOUBLE_LERP_WAVE_EFFECT, new DoubleLerpWaveEffect());
    }
}
