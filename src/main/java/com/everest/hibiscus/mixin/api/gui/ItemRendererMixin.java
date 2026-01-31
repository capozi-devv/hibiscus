package com.everest.hibiscus.mixin.api.gui;

import com.everest.hibiscus.api.modules.gui.item.VariableModel;
import com.everest.hibiscus.api.modules.gui.item.VariableTexture;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @WrapOperation(
            method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V"
            )
    )
    private void hibiscus_gui$renderGuiTexture(ItemRenderer instance, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel bakedModel, Operation<Void> original) {
        if (stack.getItem() instanceof VariableTexture vt) {
            Identifier t = vt.getTexture(stack);
            bakedModel = MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(Identifier.of(t.getNamespace(), "item/" + t.getPath()), "inventory"));
        } else if (stack.getItem() instanceof VariableModel vm) {
            bakedModel = MinecraftClient.getInstance().getBakedModelManager().getModel(ModelIdentifier.ofInventoryVariant(vm.getModel(stack)));
        }
        original.call(instance, stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, bakedModel);
    }
}
