package net.goosehub.useless.item.custom;

import net.goosehub.useless.component.ModDataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

import java.util.ArrayList;
import java.util.List;

public class TrashItem extends Item {

    public TrashItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType == ClickType.RIGHT) {
            List<ItemStack> items = stack.getOrDefault(ModDataComponentTypes.ITEMS, new ArrayList<>());

            if (!items.isEmpty()) {
                for (ItemStack item : items) {
                    boolean wasAdded = player.getInventory().insertStack(item);
                    if (!wasAdded) {
                        player.dropItem(item, false);
                    }
                }

                stack.decrement(1);

                return true;
            }
        }

        return false;
    }
}
