package com.idtech.block.tileEntity;

import com.idtech.BaseMod;
import com.idtech.block.BlockMod;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(BaseMod.MODID)
public class TileEntityContainer {
    public static final TileEntityType<?> TPENTITY = TileEntityType.Builder.create(TeleportEntity::new, BlockMod.TP).build(null);
}
