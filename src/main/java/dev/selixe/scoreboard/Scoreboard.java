package dev.selixe.scoreboard;

import com.google.common.collect.Lists;
import dev.selixe.utils.bukkit.ColorUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import java.util.List;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@Getter
@Setter
public class Scoreboard {

    private Player player;
    private ScoreboardAdapter adapter;
    private List<ScoreboardLine> lines;
    private org.bukkit.scoreboard.Scoreboard scoreboard;
    private final List<String> identifiers = Lists.newArrayList();

    public Scoreboard(Player player, ScoreboardAdapter adapter) {
        this.player = player;
        this.adapter = adapter;
        this.lines = Lists.newArrayList();

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = scoreboard.getObjective("Premiere-Board");
        if (obj == null) {
            obj = scoreboard.registerNewObjective("Premiere-Board", "dummy");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        for (int i = 0; i < 15; i++) {
            scoreboard.registerNewTeam(String.valueOf(i));
        }

        player.setScoreboard(scoreboard);
    }


    public void updateBoard(List<ScoreboardLine> lines) {
        if (ScoreboardManager.getScoreboard(player) == null) {
            this.setActive();
        }
        Objective obj = scoreboard.getObjective("Premiere-Board");
        if (obj == null) return;
        if (adapter.getTitle(player) != null) {
            if (!obj.getDisplayName().equals(ColorUtils.translate(adapter.getTitle(player)))) {
                obj.setDisplayName(ColorUtils.translate(adapter.getTitle(player)));
            }
        }
        for (int i = 0; i < lines.size(); i++) {
            if (i > 15) break;
            ScoreboardLine line = lines.get(i);
            ScoreboardLine oldLine = this.lines.size() > i ? this.lines.get(i) : null;
            Team team = scoreboard.getTeam(String.valueOf(i));
            if (line != null) {
                String prefix = line.getPrefix();
                String definer = line.getDefiner();
                String suffix = line.getSuffix();
                if (oldLine != null && !team.getEntries().isEmpty()) {
                    if (oldLine.getDefiner().equals(line.getDefiner())) {
                        team.setPrefix(prefix);
                        team.setSuffix(suffix);
                        continue;
                    } else {
                        String oldDefiner = team.getEntries().toArray()[0].toString();
                        scoreboard.resetScores(oldDefiner);
                        team.removeEntry(oldDefiner);
                    }
                }
                if (team.getEntries().isEmpty()) {
                    team.addEntry(definer);
                }
                team.setPrefix(prefix);
                team.setSuffix(suffix);
                if (obj.getScore(definer) != null) {
                    obj.getScore(definer).setScore(i + 1);
                }
            } else if (!team.getEntries().isEmpty()) {
                String oldDefiner = team.getEntries().toArray()[0].toString();
                scoreboard.resetScores(oldDefiner);
                team.removeEntry(oldDefiner);
            }
        }
        this.lines = lines;
    }

    public void setActive() {
        if (this.player.isOnline()) {
            this.player.setScoreboard(this.scoreboard);
        }
    }

    public String getUniqueIdentifier(int position) {
        String identifier;
        identifier = this.getRandomChatColor(position);
        if (this.identifiers.contains(identifier)) {
            identifier = identifier + this.getRandomChatColor(position);
        }

        if (identifier.length() > 16) {
            return this.getUniqueIdentifier(position);
        } else {
            this.identifiers.add(identifier);
            return identifier;
        }
    }

    private String getRandomChatColor(int position) {
        return ChatColor.values()[position].toString();
    }
}
