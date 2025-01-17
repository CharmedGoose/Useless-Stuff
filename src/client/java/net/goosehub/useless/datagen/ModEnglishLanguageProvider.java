package net.goosehub.useless.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModEnglishLanguageProvider extends FabricLanguageProvider {

    public ModEnglishLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("item.useless-stuff.trash", "Trash");

        translationBuilder.add("block.useless-stuff.trash_block", "Block of Trash");
        translationBuilder.add("block.useless-stuff.compactor", "Compactor");

        translationBuilder.add("itemgroup.useless-stuff", "Useless Stuff");

        translationBuilder.add("tooltip.useless-stuff.trash.1", "I wonder what's inside... hmm...");
        translationBuilder.add("tooltip.useless-stuff.trash.2", "Open it I dare you");
        translationBuilder.add("tooltip.useless-stuff.trash_block", "It's just trash but in block form");
        translationBuilder.add("tooltip.useless-stuff.compactor", "It compacts!");
    }
}
