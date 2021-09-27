package com.idtech.enchantment;

import com.idtech.BaseMod;
import io.netty.handler.logging.LogLevel;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Level;

@Mod.EventBusSubscriber(modid = BaseMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MagmaWalkerEnchantment extends Enchantment {

    protected MagmaWalkerEnchantment(Rarity p_i46731_1_, EnchantmentType p_i46731_2_, EquipmentSlotType[] p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, p_i46731_3_);
        setRegistryName(BaseMod.MODID, "magmawalk");
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return 1;
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return 1;
    }

    @SubscribeEvent
    public static void walk(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;

        if(player.hasItemInSlot(EquipmentSlotType.FEET)
                && EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.MAGMA,
                player.getItemStackFromSlot(EquipmentSlotType.FEET)) > 0){
        }
    }
}
