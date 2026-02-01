package com.everest.hibiscus.api.modules.rendering.text.effects;

import com.everest.hibiscus.api.modules.rendering.text.registry.TextEffect;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import org.joml.Matrix4f;

public class LerpWaveEffect implements TextEffect {

    private final float speed;
    private final float amplitude;
    private final float spacing;

    public LerpWaveEffect() {
        this(0.15f, 2.0f, 0.6f);
    }

    public LerpWaveEffect(float speed, float amplitude, float spacing) {
        this.speed = speed;
        this.amplitude = amplitude;
        this.spacing = spacing;
    }

    @Override
    public int render(
            TextRenderer textRenderer,
            OrderedText text,
            float x,
            float y,
            int color,
            boolean shadow,
            Matrix4f matrix,
            VertexConsumerProvider vertexConsumers,
            TextRenderer.TextLayerType layerType,
            int backgroundColor,
            int light
    ) {
        float time = (System.currentTimeMillis() % 1_000_000L) / 50f;

        final float[] cursor = {0f};

        text.accept((index, style, codePoint) -> {
            String ch = new String(Character.toChars(codePoint));
            float charWidth = textRenderer.getWidth(ch);

            float wobble = (float) Math.sin(time * speed + index * spacing) * amplitude;

            int drawColor = style.getColor() != null
                    ? style.getColor().getRgb()
                    : color;

            matrix.translate(cursor[0], wobble, 0f);

            textRenderer.draw(ch, x, y, drawColor, shadow, matrix, vertexConsumers, layerType, backgroundColor, light);

            matrix.translate(-cursor[0], -wobble, 0f);

            cursor[0] += charWidth;
            return true;
        });

        return (int) Math.ceil(cursor[0]);
    }

}
