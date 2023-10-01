package dev.selixe.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.selixe.Premiere;
import dev.selixe.Premiere;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@Getter
public class ScoreboardManager {

    private final JavaPlugin plugin;
    private final ScoreboardAdapter adapter;
    private ScoreboardRunnable runnable;
    private static final Map<UUID, Scoreboard> boards = Maps.newHashMap();
    @Setter
    private long ticks = 5L;

    public ScoreboardManager(JavaPlugin plugin, ScoreboardAdapter adapter) {
        this.plugin = plugin;
        this.adapter = adapter;
        Premiere.getInstance().getServer().getPluginManager().registerEvents(new ScoreboardListener(this), this.plugin);

        this.plugin.getServer().getOnlinePlayers().forEach(this::setup);
        if (this.getRunnable() != null) {
            return;
        }
        this.runnable = new ScoreboardRunnable(this);
        this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, this.runnable, this.ticks, this.ticks);
    }

    public static Scoreboard getScoreboard(Player player) {
        if (boards.containsKey(player.getUniqueId())) {
            return boards.get(player.getUniqueId());
        }
        return null;
    }

    public void setup(Player player) {
        Scoreboard scoreboard = new Scoreboard(player, adapter);
        boards.put(player.getUniqueId(), scoreboard);
    }

    public static List<ScoreboardLine> listToLines(Scoreboard scoreboard, List<String> text) {
        List<ScoreboardLine> toReturn = Lists.newArrayList();
        for (int i = 0; i < text.size(); i++) {
            toReturn.add(new ScoreboardLine(scoreboard, text.get(i), i));
        }
        Collections.reverse(toReturn);
        return toReturn;
    }
}
