package org.aussiebox.starexpress.item;

import dev.doctor4t.ratatouille.util.registrar.ItemRegistrar;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import org.aussiebox.starexpress.StarryExpress;
import org.aussiebox.starexpress.block.ModBlocks;

public interface ModItems {

    ItemRegistrar registrar = new ItemRegistrar(StarryExpress.MOD_ID);

    static void init() {
        registrar.registerEntries();
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(ModItems::addFunctionalEntries);
    }

    static void addFunctionalEntries(FabricItemGroupEntries fabricItemGroupEntries) {
        fabricItemGroupEntries.accept(ModBlocks.CIRCUITWEAVER_PLUSH);
    }
}
