package dev.selixe.utils.bukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@UtilityClass
public class ItemUtils {

    public void consume(Player player) {
        ItemStack item = player.getInventory().getItemInHand();
        if (item.getAmount() == 1) {
            player.getInventory().remove(item);
        } else {
            item.setAmount(item.getAmount() - 1);
        }
        player.updateInventory();
    }

    public void give(Player player, ItemStack item) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), item);
        } else {
            player.getInventory().addItem(item);
        }
    }

    public boolean isEmpty(ItemStack item){
        return item == null || item.getType() == Material.AIR;
    }
}
