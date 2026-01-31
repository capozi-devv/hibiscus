package com.everest.hibiscus.mixin.api.text;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Screen.class)
public class ScreenMixin {
//    @Inject(
//            method = "getTooltipFromItem",
//            at = @At("RETURN"),
//            cancellable = true
//    )
//    private static void ensureTooltipHasEffect(MinecraftClient client, ItemStack stack, CallbackInfoReturnable<List<Text>> cir) {
//        if (!tooltip.isEmpty() && !stack.isEmpty()) {
//            Text itemName = stack.getItem().getName(stack);
//
//            tooltip.set(0, itemName);
//        }
//        return tooltip;
//    }
}
