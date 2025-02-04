package net.goosehub.useless.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

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

    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
        super.markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {

        nbt.putInt("itemCount", items.size());

        NbtList nbtList = new NbtList();
        for (ItemStack item : items) {
            nbtList.add(item.toNbt(registryLookup));
        }

        nbt.put("items", nbtList);

        super.writeNbt(nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        items = new ArrayList<>();

        int itemCount = nbt.getInt("itemCount");

        NbtList nbtList = (NbtList) nbt.get("items");

        if (nbtList != null) {
            for (int i = 0; i < itemCount; i++) {
                ItemStack itemStack = ItemStack.fromNbtOrEmpty(registryLookup, nbtList.getCompound(i));
                if (!itemStack.isEmpty()) {
                    items.add(itemStack);
                }
            }
        }

        super.readNbt(nbt, registryLookup);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }
}
