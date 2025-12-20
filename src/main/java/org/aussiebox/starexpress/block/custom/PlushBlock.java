package org.aussiebox.starexpress.block.custom;

import com.mojang.serialization.MapCodec;
import dev.doctor4t.ratatouille.index.RatatouilleSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.aussiebox.starexpress.ModSounds;
import org.aussiebox.starexpress.block.ModBlocks;
import org.aussiebox.starexpress.block.entity.ModBlockEntities;
import org.aussiebox.starexpress.block.entity.custom.PlushBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlushBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    private static final MapCodec<PlushBlock> CODEC = simpleCodec(PlushBlock::new);
    public static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<Direction> FACING;
    private static final VoxelShape SHAPE;

    public PlushBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public static SoundEvent getSound(BlockState state) {
        SoundEvent ret = SoundEvents.WOOL_HIT;
        if (state.getBlock() == ModBlocks.STARGAZER_PLUSH) {
            ret = ModSounds.BLOCK_STARGAZER_PLUSH_HONK;
        }

        return ret;
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return super.getRenderShape(state);
    }

    public void attack(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player player) {
        if (!world.isClientSide) {
            Vec3 mid = Vec3.atCenterOf(pos);
            float pitch = 1.2F + world.random.nextFloat() * 0.4F;
            BlockState note = world.getBlockState(pos.below());
            if (note.hasProperty(BlockStateProperties.NOTE)) {
                pitch = (float)Math.pow(2.0F, (double)(note.getValue(BlockStateProperties.NOTE) - 12) / (double)12.0F);
            }

            BlockEntity var9 = world.getBlockEntity(pos);
            if (var9 instanceof PlushBlockEntity plushie) {
                plushie.squish(24);
            }
        }

    }

    protected void spawnDestroyParticles(Level world, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockEntity var6 = world.getBlockEntity(pos);
        if (var6 instanceof PlushBlockEntity plushie) {
            plushie.squish(4);
        }

        super.spawnDestroyParticles(world, player, pos, state);
    }

    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
        if (!world.isClientSide) {
            Vec3 mid = Vec3.atCenterOf(pos);
            float pitch = 0.8F + world.random.nextFloat() * 0.4F;
            BlockState note = world.getBlockState(pos.below());
            if (note.hasProperty(BlockStateProperties.NOTE)) {
                pitch = (float)Math.pow(2.0F, (double)(note.getValue(BlockStateProperties.NOTE) - 12) / (double)12.0F);
            }

            world.playSound(null, mid.x(), mid.y(), mid.z(), getSound(state), SoundSource.BLOCKS, 1.0F, 1.0F);
            BlockEntity var10 = world.getBlockEntity(pos);
            if (var10 instanceof PlushBlockEntity plushie) {
                plushie.squish(1);
            }
        }

        return InteractionResult.SUCCESS;
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        return true;
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level world, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.PLUSH, PlushBlockEntity::tick);
    }

    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new PlushBlockEntity(pos, state);
    }

    public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidState.is(Fluids.WATER));
    }

    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        SHAPE = box(3.0F, 0.0F, 3.0F, 13.0F, 15.0F, 13.0F);
    }
}

