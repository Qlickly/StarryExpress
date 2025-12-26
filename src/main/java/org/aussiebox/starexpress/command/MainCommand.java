package org.aussiebox.starexpress.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import org.aussiebox.starexpress.packet.OpenConfigS2CPacket;

public class MainCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("starexpress")
                        .then(Commands.literal("config")
                                .then(Commands.literal("open")
                                        .requires(ctx -> ctx.hasPermission(2))
                                        .executes(MainCommand::openConfig))
                        )
        );
    }

    private static int openConfig(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        ServerPlayer player = source.getPlayer();

        if (player != null) ServerPlayNetworking.send(player, new OpenConfigS2CPacket());

        return 1;
    }
}
