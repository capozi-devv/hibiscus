package com.everest.hibiscus.api.modules.gui.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public interface VariableTexture {
    Identifier getTexture(ItemStack stack);
}
