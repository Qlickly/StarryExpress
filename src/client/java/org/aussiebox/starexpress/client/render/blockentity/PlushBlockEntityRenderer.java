package org.aussiebox.starexpress.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.doctor4t.ratatouille.mixin.client.BlockRenderManagerAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.aussiebox.starexpress.block.entity.custom.PlushBlockEntity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class PlushBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    private final BlockRenderDispatcher renderManager;

    public PlushBlockEntityRenderer(BlockEntityRendererProvider.@NotNull Context ctx) {
        this.renderManager = ctx.getBlockRenderDispatcher();
    }

    public void render(@NotNull T entity, float tickDelta, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();
        double var10000;
        if (entity instanceof PlushBlockEntity plushie) {
            var10000 = plushie.squash;
        } else {
            var10000 = 0.0F;
        }

        double squish = var10000;
        double lastSquish = squish * (double)3.0F;
        float squash = (float)Math.pow((double)1.0F - (double)1.0F / ((double)1.0F + Mth.lerp(tickDelta, lastSquish, squish)), 2.0F);
        matrices.scale(1.0F, 1.0F - squash, 1.0F);
        matrices.translate(0.5F, 0.0F, (double)0.5F);
        matrices.scale(1.0F + squash / 2.0F, 1.0F, 1.0F + squash / 2.0F);
        matrices.translate(-0.5F, 0.0F, (double)-0.5F);
        BlockState state = entity.getBlockState();
        BakedModel bakedModel = this.renderManager.getBlockModel(state);
        ((BlockRenderManagerAccessor)this.renderManager).getModelRenderer().renderModel(matrices.last(), vertexConsumers.getBuffer(ItemBlockRenderTypes.getRenderType(state, false)), state, bakedModel, 255.0F, 255.0F, 255.0F, light, overlay);
        matrices.popPose();
    }
}
