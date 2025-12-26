package org.aussiebox.starexpress.mixin;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import net.minecraft.world.entity.player.Player;
import org.aussiebox.starexpress.StarryExpress;
import org.aussiebox.starexpress.StarryExpressRoles;
import org.aussiebox.starexpress.cca.AbilityComponent;
import org.aussiebox.starexpress.cca.ServerConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoodComponent.class)
public abstract class MoodMixin {

    @Shadow
    public abstract float getMood();

    @Shadow @Final
    private Player player;

    @Inject(method = "setMood", at = @At("HEAD"))
    void setMood(float mood, CallbackInfo ci) {
        GameWorldComponent gameWorldComponent = GameWorldComponent.KEY.get(player.level());
        ServerConfig config = ServerConfig.KEY.get(player.level());
        if (mood > getMood()) {
            if (gameWorldComponent.getRole(player) == StarryExpressRoles.STARSTRUCK) {
                StarryExpress.LOGGER.info("Reduce Starstruck Cooldown: {}", config.isStarstruckReduceCooldown());
                if (config.isStarstruckReduceCooldown()) {
                    AbilityComponent ability = AbilityComponent.KEY.get(player);
                    ability.changeCooldown(-100);
                }
            }
        }
    }
}
