package org.aussiebox.starexpress;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.aussiebox.starexpress.command.MainCommand;

public class StarryExpressCommands {

    public static void init() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            MainCommand.register(dispatcher);
        }));
    }

}
