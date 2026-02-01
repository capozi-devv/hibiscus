package com.everest.hibiscus.test.item;

import com.everest.hibiscus.Hibiscus;
import com.everest.hibiscus.api.modules.gui.item.VariableTexture;
import com.everest.hibiscus.api.modules.rendering.text.HibiscusPresetEffects;
import com.everest.hibiscus.api.modules.rendering.text.TextEffects;
import com.everest.hibiscus.api.modules.rendering.text.registry.TextEffectManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TestItem extends Item implements VariableTexture {
    public TestItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        MutableText text = Text.literal("Test Item");
        Style currentStyle = text.getStyle();
        Style withFont = currentStyle.withFont(Hibiscus.id("distorted"));
        Style withEffect = TextEffectManager.withEffect(withFont,
                HibiscusPresetEffects.DOUBLE_LERP_WAVE_EFFECT,
                TextEffectManager.getEffect(HibiscusPresetEffects.DOUBLE_LERP_WAVE_EFFECT)
        );
        text.setStyle(withEffect);
        return text;
    }

    @Override
    public Identifier getTexture(ItemStack stack) {
        return Identifier.of(Hibiscus.MODID, "test/test_item_gui");
    }
}
