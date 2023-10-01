package dev.selixe.utils.bukkit;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class ActionBar {

    private String text;

    public ActionBar(String text) {
        this.text = text;
    }

    public void sendToPlayer(Player p) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(TextComponent.fromLegacyText(ColorUtils.translate(text))));
    }

    public void sendToAll() {
        Bukkit.getOnlinePlayers().forEach(this::sendToPlayer);
    }

}