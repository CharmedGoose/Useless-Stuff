package net.goosehub.useless.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.goosehub.useless.block.ModBlocks;
import net.goosehub.useless.item.ModItems;
import net.minecraft.client.data.*;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TRASH_BLOCK);

        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.COMPACTOR);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.TRASH, Models.GENERATED);
    }
}
