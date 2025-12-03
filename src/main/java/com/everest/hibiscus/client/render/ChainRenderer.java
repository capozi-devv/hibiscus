package com.everest.hibiscus.client.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class ChainRenderer {
    public void render(Vec3d startingPos, Vec3d endingPos, Identifier texture, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        matrices.pop();
        Vec3d direction = startingPos.subtract(endingPos).normalize();
        double horizontalMagnitude = direction.horizontalLength();
        double angle = Math.acos(direction.x / direction.horizontalLength());
        if (direction.z > 0.0) angle = (Math.PI * 2 - angle);
        double angle1 = Math.atan(direction.y / horizontalMagnitude);
        int segments = (int) Math.ceil(endingPos.distanceTo(startingPos) * 2);
        VertexConsumer vertexConsumer2 = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(texture, false));
        double backOffset = -0.15;
        double upOffset = 0.05;
        Vec3d startOffset = new Vec3d(
                direction.x * backOffset,
                direction.y * backOffset + upOffset,
                direction.z * backOffset
        );
        matrices.push();
        matrices.translate(startOffset.x, startOffset.y, startOffset.z);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) angle));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) angle1));
        for (int i = 0; i < segments; i++) {
            int frame = Math.min(i + 1, 10);
            Vec3d vec = new Vec3d(0.48, 0, 0);

            if (i % 2 == 0) {
                matrices.multiply(RotationAxis.POSITIVE_X.rotation((float) (90 * Math.PI / 180)));
            }
            else {
                matrices.multiply(RotationAxis.POSITIVE_X.rotation((float) -(90 * Math.PI / 180)));
            }
            drawQuad(matrices, vertexConsumer2, vec.multiply(i), 0.96f, 0.3f, 5, 5, 5, 5, 1, 255, 15728880);
        }
        matrices.pop();
    }

    private static void drawQuad(MatrixStack matrices, VertexConsumer vertexConsumer, Vec3d offset, float width, float height, int frameHeight, int frameWidth, int textureHeight, int textureWidth, int frame, int alpha, int light) {
        Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
        MatrixStack.Entry entry = matrices.peek();
        if (frame < 1) frame = 1;

        float xOffset = width / 2;
        float zOffset = height / 2;
        float uOffset = (float) frameWidth / textureWidth;
        float vOffset = (float) frameHeight / textureHeight;

        //Vertices in clockwise order, starting at top left
        vertexConsumer.vertex(positionMatrix,  (float) (-xOffset + offset.x), (float) offset.y, (float) (-zOffset + offset.z))
                .color(255, 255, 255, alpha)
                .texture(0, vOffset * (frame - 1))
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(entry, 0, 1, 0);
        vertexConsumer.vertex(positionMatrix,(float) (xOffset + offset.x), (float) offset.y, (float) (-zOffset + offset.z))
                .color(255, 255, 255, alpha)
                .texture(uOffset, vOffset * (frame - 1))
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(entry, 0, 1, 0);
        vertexConsumer.vertex(positionMatrix,(float) (xOffset + offset.x), (float) offset.y,(float) (zOffset + offset.z))
                .color(255, 255, 255, alpha)
                .texture(uOffset, vOffset * frame)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(entry, 0, 1, 0);
        vertexConsumer.vertex(positionMatrix, (float) (-xOffset + offset.x),  (float) offset.y, (float) (zOffset + offset.z))
                .color(255, 255, 255, alpha)
                .texture(0, vOffset * frame)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(entry, 0, 1, 0);
    }
}