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

public class SkyBeamRenderer {

    public static void render(MatrixStack matrices, VertexConsumerProvider provider, Identifier texture, float tiltAngleDeg, float height, float halfWidth, RenderOptions options) {

        matrices.push();

        var cam = MinecraftClient.getInstance().gameRenderer.getCamera();
        float yaw = cam.getYaw();

        // Apply SPIN rotation around Y-axis
        if (options.animation == RenderOptions.AnimationStyle.SPIN) {
            float time = MinecraftClient.getInstance().world.getTime()
                    + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(time * 4f * options.animationSpeed));
        } else {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-yaw));
        }

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(tiltAngleDeg));

        VertexConsumer cons = provider.getBuffer(RenderLayer.getEntityTranslucent(texture));
        MatrixStack.Entry e = matrices.peek();

        float time = MinecraftClient.getInstance().world.getTime()
                + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false);

        // Texture scroll for non-SPIN animations
        float scroll = 0f;
        float wobbleU = 0f;
        if (options.animation == RenderOptions.AnimationStyle.WOBBLE) {
            wobbleU = (float) Math.sin(time * 0.15f * options.animationSpeed) * 0.08f;
        }

        float x1 = -halfWidth * options.size;
        float x2 =  halfWidth * options.size;
        float zTop = height * options.size;
        float vRepeat = height * 0.10f;

        // Alpha with fade and pulse
        float alphaBottom = 255f;
        float alphaTop = 255f;

        if (options.fadeInTicks > 0)
            alphaBottom = MathHelper.clamp(time / options.fadeInTicks * 255f, 0f, options.maxAlpha * 255f);
        else
            alphaBottom = options.maxAlpha * 255f;

        if (options.fadeOutTicks > 0)
            alphaTop = MathHelper.clamp((options.fadeOutTicks - (time % options.fadeOutTicks)) / options.fadeOutTicks * 255f, 0f, options.maxAlpha * 255f);
        else
            alphaTop = options.maxAlpha * 255f;

        if (options.animation == RenderOptions.AnimationStyle.PULSE) {
            float pulse = (MathHelper.sin(time * 0.1f * options.animationSpeed) * 0.5f + 0.5f);
            alphaBottom *= pulse;
            alphaTop *= pulse;
        }

        // bottom-left
        put(cons, e, x1, 0, 0, wobbleU + 0, scroll, alphaBottom);
        // top-left
        put(cons, e, x1, 0, zTop, wobbleU + 0, scroll + vRepeat, alphaTop);
        // top-right
        put(cons, e, x2, 0, zTop, wobbleU + 1, scroll + vRepeat, alphaTop);
        // bottom-right
        put(cons, e, x2, 0, 0, wobbleU + 1, scroll, alphaBottom);

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
