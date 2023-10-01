package dev.selixe;

import dev.selixe.listener.SkullInteractListener;
import dev.selixe.menu.MenuAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class Premiere extends JavaPlugin {

    public static Premiere getInstance() {
        return Premiere.getPlugin(Premiere.class);
    }


    @Override
    public void onEnable() {
        new MenuAPI(this);

        Bukkit.getPluginManager().registerEvents(new SkullInteractListener(), this);
    }

    @Override
    public void onDisable() {
    }

}
