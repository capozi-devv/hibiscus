package com.everest.hibiscus.api.modules.rendering.text.effects;

import com.everest.hibiscus.api.modules.rendering.text.registry.TextEffect;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.Style;
import org.joml.Matrix4f;

public class WaveEffect implements TextEffect {
    private final float speed;
    private final float amplitude;
    private final float spacing;

    public WaveEffect() {
        this(0.15f, 2.0f, 0.6f);
    }

    public WaveEffect(float speed, float amplitude, float spacing) {
        this.speed = speed;
        this.amplitude = amplitude;
        this.spacing = spacing;
    }

    @Override
    public void render(DrawContext context, TextRenderer textRenderer, String text, int x, int y, int color, Style style, float tickDelta) {
        float time = (System.nanoTime() / 1_000_000L) / 50f;
        int advance = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            context.getMatrices().push();

            float wobble = (float) Math.sin(time * speed + i * spacing) * amplitude;

            context.getMatrices().translate(
                    x + advance,
                    y + wobble,
                    0f
            );

            textRenderer.draw(
                    String.valueOf(ch),
                    0,
                    0,
                    color,
                    false,
                    context.getMatrices().peek().getPositionMatrix(),
                    context.getVertexConsumers(),
                    TextRenderer.TextLayerType.NORMAL,
                    0,
                    15728880
            );

            context.getMatrices().pop();

            advance += textRenderer.getWidth(String.valueOf(ch));
        }
    }

    @Override
    public void render(TextRenderer textRenderer, String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType layerType, int backgroundColor, int light, Style style, float tickDelta) {

    }
}
