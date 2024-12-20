package dev.selixe;

import dev.selixe.command.CommandHandler;
import dev.selixe.command.impl.ColorCommand;
import dev.selixe.event.PotionEffectEndEvent;
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
        CommandHandler.registerCommands(ColorCommand.class, this);

        CommandHandler.registerProcessors("dev.selixe.command.processors", this);

        Bukkit.getPluginManager().registerEvents(new PotionEffectEndEvent.PotionEffectEndListener(), this);
    }

    @Override
    public void onDisable() {
    }

}
