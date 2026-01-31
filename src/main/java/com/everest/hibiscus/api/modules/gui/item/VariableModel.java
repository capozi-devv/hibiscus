package com.everest.hibiscus.api.modules.gui.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public interface VariableModel {
    Identifier getModel(ItemStack stack);
}
