package dev.selixe.menu.buttons;

import dev.selixe.menu.Button;
import dev.selixe.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class MenuButton extends Button {

    private ItemStack item;
    private Menu menu;

    public MenuButton(ItemStack item, Menu menu) {
        this.item = item;
        this.menu = menu;
    }

    @Override
    public ItemStack getButtonItem(Player p0) {
        return item;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        menu.openMenu(player);
    }
}
