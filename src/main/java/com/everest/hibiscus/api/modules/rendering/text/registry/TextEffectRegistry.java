package com.everest.hibiscus.api.modules.rendering.text.registry;

import net.minecraft.text.Text;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TextEffectRegistry {
    private static final Map<Text, TextEffect> EFFECTS = new ConcurrentHashMap<>();
    private static final Map<Text, TextEffect> WEAK_EFFECTS = new WeakHashMap<>();

    /**
     * Associate a text effect with a Text object
     */
    public static void register(Text text, TextEffect effect) {
        EFFECTS.put(text, effect);
        WEAK_EFFECTS.put(text, effect);
    }

    /**
     * Get the effect associated with a Text object
     */
    public static TextEffect get(Text text) {
        // Check both maps for safety
        TextEffect effect = EFFECTS.get(text);
        if (effect == null) {
            effect = WEAK_EFFECTS.get(text);
        }
        return effect;
    }

    /**
     * Remove a text effect association
     */
    public static void remove(Text text) {
        EFFECTS.remove(text);
        WEAK_EFFECTS.remove(text);
    }

    /**
     * Check if a Text has an effect
     */
    public static boolean hasEffect(Text text) {
        return EFFECTS.containsKey(text) || WEAK_EFFECTS.containsKey(text);
    }

    /**
     * Clear all registered effects
     */
    public static void clear() {
        EFFECTS.clear();
        WEAK_EFFECTS.clear();
    }
}
