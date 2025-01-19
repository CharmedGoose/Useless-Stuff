package net.goosehub.useless.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.goosehub.useless.block.ModBlocks;
import net.goosehub.useless.item.ModItems;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                createShapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TRASH_BLOCK, 1)
                        .input(ModItems.TRASH, 9)
                        .criterion(hasItem(ModItems.TRASH), conditionsFromItem(ModItems.TRASH))
                        .offerTo(exporter);

                createShaped(RecipeCategory.REDSTONE, ModBlocks.COMPACTOR, 1)
                        .pattern("###")
                        .pattern("#X#")
                        .pattern("#R#")
                        .input('#', Items.COBBLESTONE)
                        .input('R', Items.REDSTONE)
                        .input('X', Items.IRON_INGOT)
                        .criterion(hasItem(Items.CRAFTING_TABLE), conditionsFromItem(Items.CRAFTING_TABLE))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "ModRecipeProvider";
    }
}
