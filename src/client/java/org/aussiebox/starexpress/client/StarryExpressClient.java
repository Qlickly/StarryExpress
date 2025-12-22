package org.aussiebox.starexpress.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import org.agmas.noellesroles.client.NoellesrolesClient;
import org.aussiebox.starexpress.StarryExpress;
import org.aussiebox.starexpress.block.ModBlocks;
import org.aussiebox.starexpress.block.entity.ModBlockEntities;
import org.aussiebox.starexpress.client.render.blockentity.PlushBlockEntityRenderer;
import org.lwjgl.glfw.GLFW;

public class StarryExpressClient implements ClientModInitializer {

    public static KeyMapping abilityBind;

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), ModBlocks.STARGAZER_PLUSH);
        BlockEntityRenderers.register(ModBlockEntities.PLUSH, PlushBlockEntityRenderer::new);

        if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
            abilityBind = NoellesrolesClient.abilityBind;
        } else {
            abilityBind = KeyBindingHelper.registerKeyBinding(new KeyMapping("key." + StarryExpress.MOD_ID + ".ability", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.wathe.keybinds"));
        }

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

        });
    }
}
