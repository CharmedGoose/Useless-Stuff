package net.goosehub.useless.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.goosehub.useless.UselessStuff;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item TRASH = registerItem("trash", new Item(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(UselessStuff.MOD_ID, "trash")))));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(UselessStuff.MOD_ID, name), item);
    }

    public static void registerModItems() {
        UselessStuff.LOGGER.info("Registering Mod Items");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(TRASH);
        });
    }
}
