package org.aussiebox.starexpress.mixin;

import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.aussiebox.starexpress.cca.SilenceComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameFunctions.class)
public abstract class GameFunctionsMixin {

    @Inject(
            method = "killPlayer(Lnet/minecraft/world/entity/player/Player;ZLnet/minecraft/world/entity/player/Player;Lnet/minecraft/resources/ResourceLocation;)V",
            at = @At("TAIL")
    )
    private static void killPlayer(Player victim, boolean spawnBody, Player killer, ResourceLocation deathReason, CallbackInfo ci) {
        SilenceComponent.KEY.get(victim).reset();
    }

}
