package com.everest.hibiscus.api.modules.rendering.text;

import com.everest.hibiscus.api.modules.rendering.text.registry.TextEffect;
import com.everest.hibiscus.api.modules.rendering.text.registry.TextEffectRegistry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class TextEffects {
    public static MutableText withEffect(Text text, TextEffect effect) {
        MutableText mutableText = text.copy();
        TextEffectRegistry.register(mutableText, effect);
        return mutableText;
    }

    public static MutableText withEffect(MutableText text, TextEffect effect) {
        TextEffectRegistry.register(text, effect);
        return text;
    }
}