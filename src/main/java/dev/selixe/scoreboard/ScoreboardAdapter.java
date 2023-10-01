package dev.selixe.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public interface ScoreboardAdapter {

    String getTitle(Player player);

    List<String> getLines(Player player);


}
