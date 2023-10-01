package dev.selixe.listener;

import dev.selixe.event.SkullInteractEvent;
import dev.selixe.utils.bukkit.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class SkullInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() == Material.PLAYER_HEAD) {
            SkullInteractEvent skullInteractEvent = new SkullInteractEvent(event.getPlayer(), (Skull) event.getClickedBlock().getState());
            Bukkit.getPluginManager().callEvent(skullInteractEvent);
            if (event.isCancelled()) return;
            event.getPlayer().sendMessage(ColorUtils.translate("&eThis is the head of: " + ((Skull) event.getClickedBlock().getState()).getOwner()));
        }
    }
}
