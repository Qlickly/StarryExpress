package org.aussiebox.starexpress.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.aussiebox.starexpress.block.entity.ModBlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlushBlockEntity extends BlockEntity {
    public double squash;

    public PlushBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLUSH, pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, @NotNull PlushBlockEntity spark) {
        if (spark.squash > (double)0.0F) {
            spark.squash /= 3.0F;
            if (spark.squash < (double)0.01F) {
                spark.squash = 0.0F;
                if (world != null) {
                    world.sendBlockUpdated(pos, state, state, 2);
                }
            }
        }

    }

    public void squish(int squash) {
        this.squash += squash;
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 2);
        }

        this.setChanged();
    }

    protected void saveAdditional(CompoundTag nbt, HolderLookup.@NotNull Provider registries) {
        nbt.putDouble("squash", this.squash);
    }

    protected void loadAdditional(CompoundTag nbt, HolderLookup.@NotNull Provider registries) {
        this.squash = nbt.getDouble("squash");
    }

    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        return this.saveWithoutMetadata(registries);
    }
}

