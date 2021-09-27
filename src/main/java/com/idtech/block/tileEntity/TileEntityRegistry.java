package com.idtech.block.tileEntity;

import com.idtech.block.BlockMod;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber
public class TileEntityRegistry {

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<TileEntityType<?>> event){
        TileEntityContainer.TPENTITY.setRegistryName("teleportentity");
        event.getRegistry().register(TileEntityContainer.TPENTITY);
    }
}
