package com.idtech.block.tileEntity;

import com.idtech.BaseMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.impl.TeleportCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;
import net.minecraft.world.server.ChunkManager;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLCommonLaunchHandler;
import net.minecraftforge.fml.loading.FMLServerLaunchProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class TeleportEntity extends TileEntity implements ITickableTileEntity {

/*    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

    private final IItemHandler inventory = new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();
        }
    };*/

    private BlockPos TPPos = new BlockPos(0,0,0);
    private boolean teleport = true;
    private int cooldown = 0;
    private int cooldownTime = 30;

    public TeleportEntity(){
        super(TileEntityContainer.TPENTITY);
    }

    public void teleported(){
        teleport = false;
        markDirty();
    }

    public void teleportPlayer(Entity player){
        if(TPPos != null && teleport) {
            if(world.isRemote()) {
                this.teleported();
                player.sendMessage(new StringTextComponent("teleporting from " + this.getPos().toString() + " to " + TPPos.toString()), UUID.randomUUID());
                TeleportEntity tile2 = (TeleportEntity) player.world.getChunk(TPPos).getTileEntity(TPPos);
                if (tile2 != null) {
                    tile2.teleported();
                    markDirty();
                    player.setPositionAndRotation(TPPos.getX() + 0.5, TPPos.getY() + 1, TPPos.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
                }
            }
        }
    }

    //load method
    @Override
    public void func_230337_a_(BlockState state, CompoundNBT nbt){
        super.func_230337_a_(state, nbt);
        this.TPPos = new BlockPos(
                    nbt.getInt("tpposX"),
                    nbt.getInt("tpposY"),
                    nbt.getInt("tpposZ"));
        teleport = nbt.getBoolean("teleport");
        cooldown = nbt.getInt("cooldown");
        super.func_230337_a_(state, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag(){
        return this.write(new CompoundNBT());
    }

    //save method
    @Override
    public CompoundNBT write(CompoundNBT nbt){
        BaseMod.LOGGER.log(Level.DEBUG, this.TPPos.toImmutable().getX());
        nbt.putInt("tpposX", TPPos.getX());
        nbt.putInt("tpposY", TPPos.getY());
        nbt.putInt("tpposZ", TPPos.getZ());
        nbt.putBoolean("teleport", teleport);
        nbt.putInt("cooldown", cooldown);
        return super.write(nbt);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket(){
        return new SUpdateTileEntityPacket(this.getPos(), 0, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt){
        this.func_230337_a_(this.getBlockState(), pkt.getNbtCompound());
    }

    @Override
    public void tick() {
        if(!teleport){
            cooldown += 1;
            if(cooldown == cooldownTime){
                teleport = true;
                cooldown=0;
            }
        }
        markDirty();
    }
}
