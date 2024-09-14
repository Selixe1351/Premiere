package dev.selixe.utils.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright (c) 2024 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class Tasks {

    void run(Runnable runnable, JavaPlugin plugin) {
        plugin.getServer().getScheduler().runTask(plugin, runnable);
    }

    void runAsync(Runnable runnable, JavaPlugin plugin) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    void runTimer(Runnable runnable, JavaPlugin plugin, long interval) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, 5L, interval);
    }

    void runTimerAsync(Runnable runnable, JavaPlugin plugin, long interval) {
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, runnable, 5L, interval);
    }

    void runLater(Runnable runnable, JavaPlugin plugin, long delay) {
        plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
    }

    void runLaterAsync(Runnable runnable, JavaPlugin plugin, long delay) {
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

}
