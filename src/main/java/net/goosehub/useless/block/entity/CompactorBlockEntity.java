package net.goosehub.useless.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class CompactorBlockEntity extends BlockEntity {

    private List<ItemStack> items = new ArrayList<>();

    public CompactorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COMPACTOR_BLOCK_ENTITY, pos, state);
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void addItem(ItemStack item) {
        items.add(item);
        markDirty();
    }

    public void removeLast() {
        items.removeLast();
        markDirty();
    }

    public void clearItems() {
        items = new ArrayList<>();
        markDirty();
    }
}
