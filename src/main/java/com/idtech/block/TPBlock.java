package com.idtech.block;

import com.idtech.BaseMod;
import com.idtech.block.tileEntity.TeleportEntity;
import com.idtech.block.tileEntity.TileEntityContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Level;

import java.util.UUID;

@Mod.EventBusSubscriber
public class TPBlock extends Block {

    public TPBlock() {
        super(Block.Properties.create(Material.ROCK));
        setRegistryName("tpblock");
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn){
        if(entityIn.isCrouching()) {
            TeleportEntity tile = (TeleportEntity) worldIn.getChunk(pos).getTileEntity(pos);
            tile.teleportPlayer(entityIn);
        }
        super.onEntityWalk(worldIn, pos, entityIn);
    }

    @Override
    public boolean hasTileEntity(final BlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world){
        return new TeleportEntity();
    }

}
