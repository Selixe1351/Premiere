package dev.selixe.utils.bukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
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

        boolean offHand = false;
        ItemStack item = player.getInventory().getItem(EquipmentSlot.HAND);

        if (item.getType().isAir()) {
            item = player.getInventory().getItem(EquipmentSlot.OFF_HAND);
            offHand = true;
            if (item.getType().isAir()) {
                return;
            }
        }

        if (item.getAmount() == 1) {
            if (offHand) {
                player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
            } else {
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            }
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
