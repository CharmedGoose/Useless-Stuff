package net.goosehub.useless.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.goosehub.useless.UselessStuff;
import net.goosehub.useless.component.ModDataComponentTypes;
import net.goosehub.useless.item.custom.TrashItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ModItems {

    public static final Item TRASH = registerItem("trash", new TrashItem(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(UselessStuff.MOD_ID, "trash")))
                    .component(ModDataComponentTypes.ITEMS, new ArrayList<>())
                    .maxCount(1)
            )
    );

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
