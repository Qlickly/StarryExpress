package org.aussiebox.starexpress.item;

import dev.doctor4t.ratatouille.util.registrar.ItemRegistrar;
import dev.doctor4t.wathe.index.WatheItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import org.aussiebox.starexpress.StarryExpress;
import org.aussiebox.starexpress.block.ModBlocks;
import org.aussiebox.starexpress.item.custom.TapeItem;

public interface StarryExpressItems {

    ItemRegistrar registrar = new ItemRegistrar(StarryExpress.MOD_ID);

    Item TAPE = registrar.create("tape", new TapeItem((new Item.Properties()).stacksTo(1)), WatheItems.EQUIPMENT_GROUP);

    static void init() {
        registrar.registerEntries();
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(StarryExpressItems::addFunctionalEntries);
    }

    static void addFunctionalEntries(FabricItemGroupEntries fabricItemGroupEntries) {
        fabricItemGroupEntries.accept(ModBlocks.CIRCUITWEAVER_PLUSH);
        fabricItemGroupEntries.accept(ModBlocks.JADE_PLUSH);
    }
}
