package net.goosehub.useless.block.custom;

import net.goosehub.useless.component.ModDataComponentTypes;
import net.goosehub.useless.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class CompactorBlock extends Block {

    public CompactorBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        world.playSound(player, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 1f, 1f);

        if (!world.isClient) {
            Box area = new Box(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
            List<ItemEntity> items = world.getEntitiesByType(EntityType.ITEM, area, EntityPredicates.VALID_ENTITY);

            if (!items.isEmpty()) {
                List<ItemStack> itemStacks = items.stream()
                        .map(ItemEntity::getStack)
                        .collect(Collectors.toList());

                ItemStack trash = new ItemStack(ModItems.TRASH);
                trash.set(ModDataComponentTypes.ITEMS, itemStacks);

                for (ItemEntity item : items) {
                    item.remove(Entity.RemovalReason.KILLED);
                }

                world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), trash));
                player.sendMessage(Text.literal("Compacted: " + getStackNames(itemStacks)), true);
            }
        }

        world.playSound(player, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 1f, 1f);

        return ActionResult.SUCCESS;
    }

    private String getStackNames(List<ItemStack> itemStacks) {
        return itemStacks.stream()
                .map(item ->
                {
                    String itemName = item.getItem().getName().getString();
                    return item.getCount() + " " + itemName + ((item.getCount() > 1) && !itemName.endsWith("s") ? "s" : "");
                })
                .collect(Collectors.joining(", "));
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.useless-stuff.compactor"));
        super.appendTooltip(stack, context, tooltip, options);
    }
}
