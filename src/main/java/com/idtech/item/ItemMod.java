package com.idtech.item;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.Set;

@Mod.EventBusSubscriber
public class ItemMod {

    //BASIC ITEMS
    public static final Item STRUCTURE_GEL = ItemUtils.buildBasicItem("structuregel", ItemGroup.MISC);
    public static final Item TPCONNECT = new TPConnector(1,1, ItemTier.WOOD, ImmutableSet.of(Blocks.ANDESITE), new Item.Properties().group(ItemGroup.MISC).addToolType(ToolType.PICKAXE, ItemTier.WOOD.getHarvestLevel()));

    //FOODS


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        //BASIC ITEMS
        event.getRegistry().register(STRUCTURE_GEL);
        event.getRegistry().register(TPCONNECT);

        // ITEMS

        // TOOLS

        // FOOD

        // ARMOR

    }
}
