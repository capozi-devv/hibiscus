package com.everest.hibiscus.api.modules.prefab.item;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class AutoStatAxeItem extends SwordItem {
    private final float damageModifier;
    private final float speedModifier;
    private final float materialScaleRate;

    public AutoStatAxeItem(ToolMaterial toolMaterial, Settings settings) {
        this(toolMaterial, settings, 0.0F, 0.0F, 1.0F);
    }

    public AutoStatAxeItem(ToolMaterial toolMaterial, Settings settings, float damageModifier, float speedModifier) {
        this(toolMaterial, settings, damageModifier, speedModifier, 1.0F);
    }

    public AutoStatAxeItem(ToolMaterial material, Settings settings, float damageModifier, float speedModifier, float materialScaleRate) {
        super(material, settings);
        this.damageModifier = damageModifier;
        this.speedModifier = speedModifier;
        this.materialScaleRate = materialScaleRate;
    }

    public float getAttackDamage() {
        return calculateAttackDamage(super.getMaterial(), damageModifier, materialScaleRate);
    }

    public float getAttackSpeed() {
        return calculateAttackSpeed(super.getMaterial(), speedModifier, materialScaleRate);
    }

    private static float calculateAttackDamage(ToolMaterial material, float damageModifier, float materialScaleRate) {
        float baseDamage = material.getAttackDamage();
        float miningSpeed = material.getMiningSpeedMultiplier();
        int enchantability = material.getEnchantability();
        int durability = material.getDurability();

        float materialBonus =
                (miningSpeed * 0.6F)
                        + (enchantability * 0.05F)
                        + (durability / 1200.0F);

        return (baseDamage + 6.0F + materialBonus) * materialScaleRate
                + damageModifier;
    }

    private static float calculateAttackSpeed(ToolMaterial material, float speedModifier, float materialScaleRate) {
        float miningSpeed = material.getMiningSpeedMultiplier();
        int durability = material.getDurability();

        float materialSpeed =
                -3.2F
                        + (miningSpeed * 0.08F)
                        + (durability / 6000.0F);

        return (materialSpeed * materialScaleRate)
                + speedModifier;
    }
}