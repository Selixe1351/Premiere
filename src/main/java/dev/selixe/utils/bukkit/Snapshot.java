package dev.selixe.utils.bukkit;

import dev.selixe.utils.bukkit.location.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@AllArgsConstructor @Getter @Setter
public class Snapshot {

    private World world;
    private Position position;
    private ItemStack[] inventoryContents;
    private ItemStack[] armorContents;

    private GameMode gameMode;
    private double health;
    private double maxHealth;
    private int foodLevel;

    private boolean flying;
    private boolean allowFly;

    private float saturation;
    private float walkSpeed;

    private int xp;

    public void restore(Player player) {
        player.teleport(position.toLocation(world));
        player.setGameMode(gameMode);
        player.getInventory().setArmorContents(armorContents);
        player.getInventory().setContents(inventoryContents);
        player.setHealth(health);
        player.setFoodLevel(foodLevel);
        player.setFlying(flying);
        player.setAllowFlight(allowFly);
        player.setTotalExperience(xp);
        player.setMaxHealth(maxHealth);
        player.setWalkSpeed(walkSpeed);
        player.setSaturation(saturation);
        player.updateInventory();
    }

}
