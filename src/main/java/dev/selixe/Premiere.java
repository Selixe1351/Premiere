package dev.selixe;

import dev.selixe.command.CommandHandler;
import dev.selixe.command.impl.ColorCommand;
import dev.selixe.command.style.CommandStyle;
import dev.selixe.event.PotionEffectEndEvent;
import dev.selixe.menu.MenuAPI;
import dev.selixe.snapshot.SnapshotManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@Getter
public class Premiere extends JavaPlugin {

    @Getter
    private static Premiere instance;

    private CommandStyle style;
    private CommandHandler commandHandler;
    private SnapshotManager snapshotManager;

    public Premiere() {

    }

    @Override
    public void onEnable() {
        instance = this;

        new MenuAPI(this);

        this.commandHandler = new CommandHandler();
        this.snapshotManager = new SnapshotManager();

        commandHandler.registerCommands(ColorCommand.class, this);

        commandHandler.registerProcessors("dev.selixe.command.processors", this);

        Bukkit.getPluginManager().registerEvents(new PotionEffectEndEvent.PotionEffectEndListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public Premiere setStyle(CommandStyle style) {
        this.style = style;
        return this;
    }

    public Premiere create(CommandStyle style) {
        return setStyle(style);
    }

}
