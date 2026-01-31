package com.everest.hibiscus.api.modules.rendering.text.registry;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.Style;
import org.joml.Matrix4f;

public interface TextEffect {
    void render(DrawContext context, TextRenderer textRenderer, String text,
                int x, int y, int color, Style style, float tickDelta);

    void render(TextRenderer textRenderer, String text,
                float x, float y, int color, boolean shadow,
                Matrix4f matrix, VertexConsumerProvider vertexConsumers,
                TextRenderer.TextLayerType layerType, int backgroundColor,
                int light, Style style, float tickDelta);
}
