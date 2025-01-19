package net.goosehub.useless.renderer;

import net.goosehub.useless.block.entity.CompactorBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.List;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public class CompactorBlockEntityRenderer implements BlockEntityRenderer<CompactorBlockEntity> {
    public CompactorBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(CompactorBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.getWorld() != null) {
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            List<ItemStack> items = entity.getItems();
            Direction direction = entity.getCachedState().get(HORIZONTAL_FACING);

            if (!items.isEmpty()) {
                float pos = 0.255f;

                for (ItemStack item : items) {
                    matrices.push();

                    // not AI
                    switch (direction) {
                        case EAST -> {
                            matrices.translate(0.75f, pos, 0.5f);
                            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
                        }
                        case SOUTH -> {
                            matrices.translate(0.5f, pos, 0.75f);
                            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                        }
                        case WEST -> {
                            matrices.translate(0.25f, pos, 0.5f);
                            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                        }
                        default -> {
                            matrices.translate(0.5f, pos, 0.25f);
                            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
                        }
                    }

                    if (item.getItem() instanceof BlockItem) {
                        matrices.scale(0.35f, 0.01f, 0.35f);
                    } else {
                        matrices.scale(0.35f, 0.35f, 0.35f);
                    }

                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

                    itemRenderer.renderItem(item, ModelTransformationMode.NONE, getLightLevel(entity.getWorld(), entity.getPos(), direction), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);

                    matrices.pop();

                    pos += 0.01f;
                }
            }
        }
    }

    private int getLightLevel(World world, BlockPos pos, Direction direction) {

        // also not AI
        switch (direction) {
            case SOUTH -> pos = pos.south();
            case WEST -> pos = pos.west();
            case EAST -> pos = pos.east();
            default -> pos = pos.north();
        }

        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}