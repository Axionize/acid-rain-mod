package com.lightning.axionize;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class AcidRainDamageTypes {
    public static final Identifier ACID_RAIN_IDENTIFIER = Identifier.of("acidrain", "acid");
    public static final RegistryKey<DamageType> ACID_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, ACID_RAIN_IDENTIFIER);

    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE).getOrThrow(key));
    }
}
