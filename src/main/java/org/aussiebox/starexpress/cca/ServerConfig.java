package org.aussiebox.starexpress.cca;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import org.aussiebox.starexpress.StarryExpress;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import pro.fazeclan.river.stupid_express.cca.SEConfig;

public class ServerConfig implements AutoSyncedComponent {
    public static final ComponentKey<ServerConfig> KEY = ComponentRegistry.getOrCreate(StarryExpress.id("server_config"), ServerConfig.class);
    private final Level level;

    // Toggles whether the Starstruck role's ability cooldown is reduced upon completing a task.
    @Getter
    @Setter
    private boolean starstruckReduceCooldown = true;

    public void sync() {
        SEConfig.KEY.sync(this.level);
    }

    public ServerConfig(Level level) {
        this.level = level;
    }

    @Override
    public void readFromNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        this.starstruckReduceCooldown = tag.contains("starstruck_ability_reduced") && tag.getBoolean("starstruck_ability_reduced");
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        tag.putBoolean("starstruck_ability_reduced", this.starstruckReduceCooldown);
    }
}
