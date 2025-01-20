package net.goosehub.useless.block.custom;

import com.mojang.serialization.MapCodec;
import net.goosehub.useless.block.entity.CompactorBlockEntity;
import net.goosehub.useless.component.ModDataComponentTypes;
import net.goosehub.useless.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompactorBlock extends FacingBlock implements BlockEntityProvider {

    public static final MapCodec<CompactorBlock> CODEC = Block.createCodec(CompactorBlock::new);

    public CompactorBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CompactorBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof CompactorBlockEntity compactorBlockEntity) {
                if (stack.isEmpty()) {
                    List<ItemStack> items = compactorBlockEntity.getItems();

                    if (!items.isEmpty()) {
                        world.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 1.0F, 1.0F);

                        ItemStack trash = new ItemStack(ModItems.TRASH);
                        trash.set(ModDataComponentTypes.ITEMS, items);

                        BlockPos spawnPos = getSpawnPos(pos, state.get(Properties.HORIZONTAL_FACING));

                        world.spawnEntity(new ItemEntity(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), trash));
                        compactorBlockEntity.clearItems();

                        player.sendMessage(Text.translatable("message.useless-stuff.compactor", getStackNames(items)), true);
                        world.playSound(null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    } else {
                        player.sendMessage(Text.translatable("message.useless-stuff.compactor.empty"), true);
                    }
                } else {
                    compactorBlockEntity.addItem(stack.copy());
                    player.setStackInHand(hand, ItemStack.EMPTY);
                    player.sendMessage(Text.translatable("message.useless-stuff.compactor.add", (stack.getCount() + " " + stack.getName().getString())), true);
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    protected void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!world.isClient()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof CompactorBlockEntity compactorBlockEntity) {
                List<ItemStack> items = compactorBlockEntity.getItems();

                if (!items.isEmpty()) {
                    ItemStack lastItem = items.getLast();
                    BlockPos spawnPos = getSpawnPos(pos, state.get(Properties.HORIZONTAL_FACING));

                    world.spawnEntity(new ItemEntity(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), lastItem));
                    compactorBlockEntity.removeLast();

                    player.sendMessage(Text.translatable("message.useless-stuff.compactor.remove", (lastItem.getCount() + " " + lastItem.getName().getString())), true);
                }
            }
        }

        super.onBlockBreakStart(state, world, pos, player);
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof CompactorBlockEntity compactorBlockEntity) {
                List<ItemStack> items = compactorBlockEntity.getItems();

                if (!items.isEmpty()) {
                    for (ItemStack item : items) {
                        world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), item));
                    }
                }
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
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

    private BlockPos getSpawnPos(BlockPos pos, Direction direction) {
        return pos.offset(direction);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.useless-stuff.compactor"));
        super.appendTooltip(stack, context, tooltip, options);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }
}
