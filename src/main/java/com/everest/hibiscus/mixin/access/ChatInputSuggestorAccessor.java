package com.everest.hibiscus.mixin.access;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChatInputSuggestor.class)
public interface ChatInputSuggestorAccessor {
    @Accessor("textRenderer")
    TextRenderer getTextRenderer();
}