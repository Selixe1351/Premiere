package dev.selixe.scoreboard;

import com.google.common.collect.Maps;
import dev.selixe.Premiere;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Setter
@Getter
public class ScoreboardManager {
    private ScoreboardAdapter adapter;
    private JavaPlugin plugin;
    private ScoreboardRunnable runnable;
    private long ticks;
    private Map<UUID, Scoreboard> boards = Maps.newHashMap();
    @Getter
    private static ScoreboardManager instance;

    public ScoreboardManager(JavaPlugin plugin, ScoreboardAdapter adapter) {
        instance = this;
        this.plugin = plugin;
        this.ticks = 5L;
        Premiere.getInstance().getServer().getPluginManager().registerEvents(new ScoreboardListener(this), this.plugin);
        this.plugin.getServer().getOnlinePlayers().forEach(this::setup);
        if (this.getRunnable() == null) {
            this.runnable = new ScoreboardRunnable(this);
            this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, this.runnable, this.ticks, this.ticks);
        }

        this.adapter = adapter;
    }

    public ScoreboardManager(JavaPlugin plugin, ScoreboardAdapter adapter, long ticks) {
        instance = this;
        this.plugin = plugin;
        this.ticks = ticks;
        Premiere.getInstance().getServer().getPluginManager().registerEvents(new ScoreboardListener(this), this.plugin);
        this.plugin.getServer().getOnlinePlayers().forEach(this::setup);
        if (this.getRunnable() == null) {
            this.runnable = new ScoreboardRunnable(this);
            this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, this.runnable, this.ticks, this.ticks);
        }

        this.adapter = adapter;
    }

    public Scoreboard setup(Player player) {
        return new Scoreboard(player);
    }

}
