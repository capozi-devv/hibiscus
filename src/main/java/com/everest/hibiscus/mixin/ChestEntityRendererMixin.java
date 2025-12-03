package com.everest.hibiscus.mixin;

import com.everest.hibiscus.client.render.LightBeamRenderer;
import com.everest.hibiscus.client.render.SkyBeamRenderer;
import com.everest.hibiscus.client.render.SymbolRenderer;
import com.everest.hibiscus.client.render.util.RenderOptions;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntityRenderer.class)
public class ChestEntityRendererMixin {
    @Inject(
            method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
            at = @At("TAIL")
    )
    private void test(BlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        if (entity instanceof ChestBlockEntity chest) {
            RenderOptions o = new RenderOptions()
                    .size(3f)
                    .animation(RenderOptions.AnimationStyle.SPIN, 0.8f)
                    .maxAlpha(1f);

            BlockPos pos = entity.getPos();
            SymbolRenderer.render(matrices, vertexConsumers, Identifier.of("moew", "nmew"), pos.getX(), pos.getY(), pos.getZ(), o);
        }
    }
}

