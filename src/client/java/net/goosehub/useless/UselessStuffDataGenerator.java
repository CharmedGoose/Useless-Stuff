package net.goosehub.useless;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.goosehub.useless.datagen.ModEnglishLanguageProvider;
import net.goosehub.useless.datagen.ModLootTableProvider;
import net.goosehub.useless.datagen.ModModelProvider;
import net.goosehub.useless.datagen.ModRecipeProvider;

public class UselessStuffDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModEnglishLanguageProvider::new);

        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModRecipeProvider::new);
    }
}
