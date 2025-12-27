package org.aussiebox.starexpress.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.aussiebox.starexpress.item.StarryExpressItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({ItemInHandLayer.class})
public class HeldItemRendererMixin {

    @WrapOperation(
            method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getMainHandItem()Lnet/minecraft/world/item/ItemStack;"
            )}
    )
    public ItemStack hideItemsInHand(LivingEntity instance, Operation<ItemStack> original) {
        ItemStack ret = original.call(instance);
        if (ret.is(StarryExpressItems.TAPE)) {
            ret = ItemStack.EMPTY;
        }

        return ret;
    }

}
