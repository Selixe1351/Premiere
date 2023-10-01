package dev.selixe.utils.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class TitleBar {

    private String title;
    private String subTitle;

    public TitleBar(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public void sendToPlayer(Player p) {
        p.sendTitle(ColorUtils.translate(title), ColorUtils.translate(subTitle));
    }

    public void sendToAll() {
        Bukkit.getOnlinePlayers().forEach(this::sendToPlayer);
    }
}
