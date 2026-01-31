package com.everest.hibiscus.mixin.api.text;

import com.everest.hibiscus.api.modules.rendering.text.registry.TextEffect;
import com.everest.hibiscus.api.modules.rendering.text.registry.TextEffectRegistry;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {

    @Inject(
            method = "drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onDrawTextWithEffect(TextRenderer textRenderer, Text text, int x, int y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
        TextEffect effect = TextEffectRegistry.get(text);

        if (effect != null) {
            effect.render((DrawContext) (Object) this, textRenderer, text.getString(), x, y, color, text.getStyle(), (System.nanoTime() / 1_000_000L) / 50f);
            cir.setReturnValue(textRenderer.getWidth(text));
            cir.cancel();
        }
    }
}