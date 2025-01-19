package net.goosehub.useless;

import net.fabricmc.api.ClientModInitializer;
import net.goosehub.useless.block.entity.ModBlockEntities;
import net.goosehub.useless.renderer.CompactorBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class UselessStuffClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.COMPACTOR_BLOCK_ENTITY, CompactorBlockEntityRenderer::new);
    }
}