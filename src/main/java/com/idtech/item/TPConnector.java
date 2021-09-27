package com.idtech.item;

import com.idtech.Utils;
import com.idtech.block.BlockMod;
import com.idtech.block.tileEntity.TeleportEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.server.command.TextComponentHelper;

import java.util.Set;
import java.util.UUID;

public class TPConnector extends ToolItem {
    private BlockState TP1;
    private BlockPos TPPos1;

    public TPConnector(float p_i48512_1_, float p_i48512_2_, IItemTier p_i48512_3_, Set<Block> p_i48512_4_, Properties p_i48512_5_) {
        super(p_i48512_1_, p_i48512_2_, p_i48512_3_, p_i48512_4_, p_i48512_5_);
        setRegistryName("tpconnector");
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        BlockPos pos = Utils.getBlockAtCursor(playerIn, 20, true);
        BlockState state = worldIn.getBlockState(pos);
        if(!worldIn.isRemote()) {
            if (state == BlockMod.TP.getDefaultState()) {
                if (TP1 == null) {
                    TP1 = state;
                    TPPos1 = pos;
                    playerIn.sendMessage(new StringTextComponent("TP One selected"), UUID.randomUUID());
                } else if (TPPos1 != pos && pos != null) {
                    TeleportEntity TP1Entity = (TeleportEntity) worldIn.getChunk(TPPos1).getTileEntity(TPPos1);
                    TeleportEntity TP2Entity = (TeleportEntity) worldIn.getChunk(pos).getTileEntity(pos);

                    CompoundNBT nbt1 = TP1Entity.write(new CompoundNBT());
                    nbt1.putInt("tpposX", pos.getX());
                    nbt1.putInt("tpposY", pos.getY());
                    nbt1.putInt("tpposZ", pos.getZ());

                    CompoundNBT nbt2 = TP2Entity.write(new CompoundNBT());
                    nbt2.putInt("tpposX", TPPos1.getX());
                    nbt2.putInt("tpposY", TPPos1.getY());
                    nbt2.putInt("tpposZ", TPPos1.getZ());

                    TP1Entity.func_230337_a_(state, nbt1);
                    TP2Entity.func_230337_a_(state, nbt2);
                    worldIn.notifyBlockUpdate(TPPos1, TP1, TP1, 3);
                    worldIn.notifyBlockUpdate(pos, TP1, TP1, 3);
                    worldIn.sendPacketToServer(TP1Entity.getUpdatePacket());
                    worldIn.sendPacketToServer(TP2Entity.getUpdatePacket());

                    TP1 = null;
                    TPPos1 = null;
                    playerIn.sendMessage(new StringTextComponent("TPs Connected"), UUID.randomUUID());
                }
            }
        }
        return ActionResult.resultPass(stack);
    }
}
