package com.everest.hibiscus.client.render;

import com.everest.hibiscus.client.render.util.RenderOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class SymbolRenderer {
    public static void render(MatrixStack matrices, VertexConsumerProvider provider, Identifier texture, float xCenter, float y, float zCenter, RenderOptions options) {

        matrices.push();

        float yOffset = 0.01f;
        matrices.translate(xCenter + 5.5, y + yOffset - 64, zCenter - 2.5);

        VertexConsumer cons = provider.getBuffer(RenderLayer.getEntityTranslucent(texture));
        MatrixStack.Entry e = matrices.peek();

        float time = MinecraftClient.getInstance().world.getTime()
                + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false);

        if (options.animation == RenderOptions.AnimationStyle.SPIN) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(time * 4f * options.animationSpeed));
        }

        float wobbleOffset = 0f;
        if (options.animation == RenderOptions.AnimationStyle.WOBBLE) {
            wobbleOffset = (float) Math.sin(time * 0.15f * options.animationSpeed) * 0.2f * options.size;
        }

        float alpha = options.maxAlpha * 255f;
        if (options.fadeInTicks > 0)
            alpha = MathHelper.clamp(time / options.fadeInTicks * 255f, 0f, alpha);
        if (options.fadeOutTicks > 0)
            alpha = MathHelper.clamp((options.fadeOutTicks - (time % options.fadeOutTicks)) / options.fadeOutTicks * 255f, 0f, alpha);
        if (options.animation == RenderOptions.AnimationStyle.PULSE) {
            float pulse = (MathHelper.sin(time * 0.1f * options.animationSpeed) * 0.5f + 0.5f);
            alpha *= pulse;
        }

        float hs = 0.5f * options.size;

        float x1 = -hs + wobbleOffset;
        float x2 =  hs + wobbleOffset;
        float z1 = -hs;
        float z2 =  hs;

        float u1 = 0f;
        float u2 = 1f;
        float v1 = 1f; // flipped V so it's visible from above
        float v2 = 0f;

        put(cons, e, x1, 0f, z1, u1, v1, alpha); // bottom-left
        put(cons, e, x1, 0f, z2, u1, v2, alpha); // top-left
        put(cons, e, x2, 0f, z2, u2, v2, alpha); // top-right
        put(cons, e, x2, 0f, z1, u2, v1, alpha); // bottom-right

        matrices.pop();
    }

    private static void put(VertexConsumer cons, MatrixStack.Entry e,
                            float x, float y, float z,
                            float u, float v,
                            float alpha) {

        cons.vertex(e, x, y, z)
                .color(255, 255, 255, (int) alpha)
                .texture(u, v)
                .light(0xF000F0)
                .normal(e, 0, 1, 0)
                .overlay(OverlayTexture.DEFAULT_UV);
    }
}
