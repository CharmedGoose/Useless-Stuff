package net.goosehub.useless.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.goosehub.useless.UselessStuff;
import net.goosehub.useless.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup USELESS_STUFF = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(UselessStuff.MOD_ID, "useless_stuff"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.TRASH))
                    .displayName(Text.translatable("itemgroup.useless-stuff"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.TRASH);
                        entries.add(ModBlocks.TRASH_BLOCK);
                        entries.add(ModBlocks.COMPACTOR);
                    })
                    .build()
    );

    public static void registerItemGroups() {
        UselessStuff.LOGGER.info("Registering Item Groups");
    }
}
