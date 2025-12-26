package org.aussiebox.starexpress.client.gui.screen;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.CheckboxComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.CollapsibleContainer;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.ScrollContainer;
import io.wispforest.owo.ui.core.OwoUIAdapter;
import io.wispforest.owo.ui.core.Sizing;
import io.wispforest.owo.ui.core.Surface;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.aussiebox.starexpress.cca.ServerConfig;
import org.aussiebox.starexpress.packet.ChangeConfigOptionC2SPacket;
import org.jetbrains.annotations.NotNull;

public class ServerConfigScreen extends BaseOwoScreen<FlowLayout> {

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout root) {
        root.surface(Surface.VANILLA_TRANSLUCENT);

        root.child(getConfig());
    }

    private static ScrollContainer<FlowLayout> getConfig() {
        if (Minecraft.getInstance().player == null) return null;
        ServerConfig config = ServerConfig.KEY.get(Minecraft.getInstance().player.level());

        ScrollContainer<FlowLayout> container = Containers.verticalScroll(Sizing.fill(), Sizing.fill(), Containers.verticalFlow(Sizing.fill(), Sizing.fill()));
        FlowLayout layout = (FlowLayout) container.children().getFirst();

        CheckboxComponent starstruckReduceCooldown = Components.checkbox(Component.translatable("config.starexpress.server.starstruck.reduce_cooldown"))
                .checked(config.isStarstruckReduceCooldown())
                .onChanged(checked -> {
                    config.setStarstruckReduceCooldown(checked);
                    config.sync();
                    ClientPlayNetworking.send(new ChangeConfigOptionC2SPacket("starstruckReduceCooldown", checked));
                });

        CollapsibleContainer roleConfig = Containers.collapsible(Sizing.fill(), Sizing.content(), Component.translatable("config.starexpress.server.category.roles"), true);

        CollapsibleContainer starstruckConfig = Containers.collapsible(Sizing.fill(), Sizing.content(), Component.translatable("guidebook.role.starexpress:starstruck"), false);
        starstruckConfig.child(starstruckReduceCooldown.sizing(Sizing.fill(), Sizing.content()).id("starstruck_reduce_cooldown"));

        roleConfig.child(starstruckConfig);

        layout.child(roleConfig);
        return container;
    }
}
