package dev.selixe.scoreboard;

import java.util.List;
import org.bukkit.entity.Player;

public interface ScoreboardAdapter {
    String getTitle(Player var1);

    List<String> getLines(Player var1);
}
