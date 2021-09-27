package com.idtech.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EnchantmentRegistry {

    public static MagmaWalkerEnchantment MAGMA = new MagmaWalkerEnchantment(Enchantment.Rarity.COMMON,
            EnchantmentType.ARMOR_FEET, new EquipmentSlotType[] {EquipmentSlotType.FEET});

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event){
        event.getRegistry().register(MAGMA);
    }
}
