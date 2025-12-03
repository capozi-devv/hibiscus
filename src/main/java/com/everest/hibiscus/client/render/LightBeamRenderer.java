package com.everest.hibiscus.client.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class LightBeamRenderer {
    @SuppressWarnings("ALL")
    public static void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, float bottomSize, float topSize, Vector3f startPos, Vector3f endPos, float bottomAlpha, float topAlpha, Vector3f color) {
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLightning());
        int light = 0xFEFEFE;
        Vector3f diff = new Vector3f(endPos).sub(startPos);
        float height = diff.length();
        matrices.push();
        matrices.translate(startPos.x, startPos.y, startPos.z);

        if (diff.lengthSquared() > 1.0E-7F) {
            diff.normalize();
            float yaw = (float) Math.atan2(diff.x, diff.z);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(yaw));
            float pitch = (float) Math.asin(-diff.y);
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(pitch));
        }

        MatrixStack.Entry entry = matrices.peek();
        renderBeamFaces(entry, buffer, light, bottomSize, topSize, height, bottomAlpha, topAlpha, color);
        matrices.pop();
    }

    private static void renderBeamFaces(MatrixStack.Entry entry, VertexConsumer buffer, int light, float bottomSize, float topSize, float height, float bottomAlpha, float topAlpha, Vector3f color) {
        renderQuad(entry, buffer, light,
                new Vector3f(-bottomSize, 0.0F, 0.0F),
                new Vector3f(-topSize, height, 0.0F),
                new Vector3f(topSize, height, 0.0F),
                new Vector3f(bottomSize, 0.0F, 0.0F),
                color, color, color, color,
                bottomAlpha, topAlpha, topAlpha, bottomAlpha);

        // Back face
        renderQuad(entry, buffer, light,
                new Vector3f(bottomSize, 0.0F, 0.0F),
                new Vector3f(topSize, height, 0.0F),
                new Vector3f(-topSize, height, 0.0F),
                new Vector3f(-bottomSize, 0.0F, 0.0F),
                color, color, color, color,
                bottomAlpha, topAlpha, topAlpha, bottomAlpha);

        // Left face (side)
        renderQuad(entry, buffer, light,
                new Vector3f(0.0F, 0.0F, -bottomSize),
                new Vector3f(0.0F, height, -topSize),
                new Vector3f(0.0F, height, topSize),
                new Vector3f(0.0F, 0.0F, bottomSize),
                color, color, color, color,
                bottomAlpha, topAlpha, topAlpha, bottomAlpha);

        // Right face (side)
        renderQuad(entry, buffer, light,
                new Vector3f(0.0F, 0.0F, bottomSize),
                new Vector3f(0.0F, height, topSize),
                new Vector3f(0.0F, height, -topSize),
                new Vector3f(0.0F, 0.0F, -bottomSize),
                color, color, color, color,
                bottomAlpha, topAlpha, topAlpha, bottomAlpha);
    }

    private static void renderQuad(MatrixStack.Entry entry, VertexConsumer buffer, int light,
                            Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4,
                            Vector3f c1, Vector3f c2, Vector3f c3, Vector3f c4,
                            float a1, float a2, float a3, float a4) {
        Matrix4f positionMatrix = entry.getPositionMatrix();

        Vector3f normal = new Vector3f(0, 1, 0);

        buffer.vertex(positionMatrix, v1.x, v1.y, v1.z).color(c1.x, c1.y, c1.z, a1).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, normal.x, normal.y, normal.z);
        buffer.vertex(positionMatrix, v2.x, v2.y, v2.z).color(c2.x, c2.y, c2.z, a2).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, normal.x, normal.y, normal.z);
        buffer.vertex(positionMatrix, v3.x, v3.y, v3.z).color(c3.x, c3.y, c3.z, a3).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, normal.x, normal.y, normal.z);
        buffer.vertex(positionMatrix, v4.x, v4.y, v4.z).color(c4.x, c4.y, c4.z, a4).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, normal.x, normal.y, normal.z);
    }
}
