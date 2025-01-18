package net.goosehub.useless.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.goosehub.useless.UselessStuff;
import net.goosehub.useless.block.ModBlocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<CompactorBlockEntity> COMPACTOR_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(UselessStuff.MOD_ID, "compactor_block"),
            FabricBlockEntityTypeBuilder.create(CompactorBlockEntity::new, ModBlocks.COMPACTOR).build());

    public static void registerBlockEntities() {
        UselessStuff.LOGGER.info("Registering Block Entities");
    }
}
