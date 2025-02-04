package net.goosehub.useless;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.goosehub.useless.block.ModBlocks;
import net.goosehub.useless.block.entity.ModBlockEntities;
import net.goosehub.useless.component.ModDataComponentTypes;
import net.goosehub.useless.item.ModItemGroups;
import net.goosehub.useless.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UselessStuff implements ModInitializer {

    public static final String MOD_ID = "useless-stuff";
    public static final Logger LOGGER = LoggerFactory.getLogger("UselessStuff");

    @Override
    public void onInitialize() {
        ModItemGroups.registerItemGroups();

        ModItems.registerModItems();
        ModBlocks.registerModBlocks();

        ModBlockEntities.registerBlockEntities();
        ModDataComponentTypes.registerDataComponentTypes();

        FuelRegistryEvents.BUILD.register((builder, context) -> {
            builder.add(ModItems.TRASH, 120 * 20);
            builder.add(ModBlocks.TRASH_BLOCK, 1200 * 20);
        });
    }
}