package org.aussiebox.starexpress.cca;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.aussiebox.starexpress.StarryExpress;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.Objects;
import java.util.UUID;

public class SilenceComponent implements AutoSyncedComponent {
    public static final ComponentKey<SilenceComponent> KEY = ComponentRegistry.getOrCreate(StarryExpress.id("silence"), SilenceComponent.class);

    private final Player player;

    @Setter
    @Getter
    private boolean silenced;

    @Setter
    @Getter
    private UUID silencer;

    public SilenceComponent(Player player) {
        this.player = player;
    }

    public void reset() {
        this.silenced = false;
        this.silencer = null;
        this.sync();
    }

    public void sync() {
        KEY.sync(this.player);
    }


    @Override
    public void readFromNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        this.silenced = tag.contains("silenced") && tag.getBoolean("silenced");
        this.silencer = tag.contains("silencer") ? tag.getUUID("silencer") : null;
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        tag.putBoolean("silenced", this.silenced);
        tag.putUUID("silencer", Objects.requireNonNullElseGet(this.silencer, () -> UUID.fromString("e1e89fbb-3beb-492a-b1be-46a4ce19c9d1")));
    }
}
