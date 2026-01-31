package com.everest.hibiscus.mixin.api.text;

import com.everest.hibiscus.api.modules.rendering.text.registry.TextEffect;
import com.everest.hibiscus.api.modules.rendering.text.registry.TextEffectRegistry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Text.class)
public interface TextMixin {
    @Inject(method = "copy", at = @At("RETURN"))
    private void onCopy(CallbackInfoReturnable<MutableText> cir) {
        TextEffect effect = TextEffectRegistry.get((Text) this);
        if (effect != null) {
            TextEffectRegistry.register(cir.getReturnValue(), effect);
        }
    }
}
