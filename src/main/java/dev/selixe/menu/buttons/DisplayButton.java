package dev.selixe.menu.buttons;

import dev.selixe.menu.Button;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@Getter @Setter
public class DisplayButton extends Button {
    private ItemStack itemStack;
    private boolean cancel;

    public DisplayButton(final ItemStack itemStack, final boolean cancel) {
        this.itemStack = itemStack;
        this.cancel = cancel;
    }

    public DisplayButton(final ItemStack itemStack) {
        this.itemStack = itemStack;
        this.cancel = true;
    }

    @Override
    public ItemStack getButtonItem(final Player player) {
        if (this.itemStack == null) {
            return new ItemStack(Material.AIR);
        }
        return this.itemStack;
    }

    @Override
    public boolean shouldCancel(final Player player, final int slot, final ClickType clickType) {
        return this.cancel;
    }
}
