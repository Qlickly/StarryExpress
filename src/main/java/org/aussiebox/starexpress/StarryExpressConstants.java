package org.aussiebox.starexpress;

import dev.doctor4t.wathe.index.WatheItems;
import dev.doctor4t.wathe.util.ShopEntry;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.aussiebox.starexpress.item.StarryExpressItems;

import java.util.ArrayList;
import java.util.List;

public interface StarryExpressConstants {
    List<ShopEntry> MUZZLER_SHOP = Util.make(new ArrayList<>(), (entries) -> {
        entries.add(new ShopEntry(WatheItems.KNIFE.getDefaultInstance(), 100, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(StarryExpressItems.TAPE.getDefaultInstance(), 75, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(WatheItems.GRENADE.getDefaultInstance(), 350, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(WatheItems.FIRECRACKER.getDefaultInstance(), 10, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.LOCKPICK.getDefaultInstance(), 50, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.CROWBAR.getDefaultInstance(), 25, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.BODY_BAG.getDefaultInstance(), 200, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(new ItemStack(WatheItems.NOTE, 4), 10, ShopEntry.Type.TOOL));
    });

    ResourceLocation SILENCED_OUTSIDE_DEATH_REASON = StarryExpress.id("silenced_and_outside");
}
