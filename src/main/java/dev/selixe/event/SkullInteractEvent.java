package dev.selixe.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class SkullInteractEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Player player;
    @Getter private final Skull skull;
    @Getter @Setter
    private boolean cancelled;

    public SkullInteractEvent(Player player, Skull skull) {
        this.player = player;
        this.skull = skull;
    }


    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
