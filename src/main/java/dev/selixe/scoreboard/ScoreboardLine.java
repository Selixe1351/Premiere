package dev.selixe.scoreboard;

import dev.selixe.utils.bukkit.ColorUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

@Getter
@Setter
public class ScoreboardLine {

    private final Scoreboard scoreboard;

    private String lineText;
    private String definer;
    private String prefix;
    private String suffix;
    private int position;

    public ScoreboardLine(Scoreboard scoreboard, String text, int position) {
        this.scoreboard = scoreboard;
        this.position = position;
        this.lineText = ColorUtils.translate(text);
        calculateLines();
    }

    /**
     * forked from: sirjavlux
     */
    private void calculateLines() {
        if (this.lineText.length() > 48) {
            System.out.println("Line is greater than 48 characters: " + this.lineText);
            this.lineText = ColorUtils.translate("&cInvalid Line");
        }

        if (this.lineText.length() <= 16) {
            this.prefix = "";
            if (this.lineText.isEmpty()) {
                this.definer = scoreboard.getUniqueIdentifier(position);
            } else {
                this.definer = scoreboard.getUniqueIdentifier(position) + this.lineText;
            }
            this.suffix = "";
        } else if (this.lineText.length() <= 32) {
            this.prefix = "";
            this.definer = scoreboard.getUniqueIdentifier(position) + this.lineText.substring(0, 14);
            final int lastColorIndex = definer.lastIndexOf(ChatColor.COLOR_CHAR);
            if (lastColorIndex >= 14) {
                this.suffix = ChatColor.getLastColors(definer.substring(0, 15)) + lineText.substring(lastColorIndex);
            } else {
                this.suffix = ChatColor.getLastColors(definer) + lineText.substring(14);
            }
        } else if (this.lineText.length() <= 48) {
            this.definer = scoreboard.getUniqueIdentifier(position) + this.lineText.substring(0, 14);
            final int lastColorIndex = definer.lastIndexOf(ChatColor.COLOR_CHAR);
            if (lastColorIndex >= 14) {
                this.prefix = ChatColor.getLastColors(definer.substring(0, 15)) + lineText.substring(lastColorIndex);
            } else {
                this.prefix = ChatColor.getLastColors(definer) + lineText.substring(14);
            }
            if (this.prefix.lastIndexOf(ChatColor.COLOR_CHAR) >= 30) {
                this.suffix = ChatColor.getLastColors(this.prefix.substring(15, 31)) + lineText.substring(this.prefix.lastIndexOf(ChatColor.COLOR_CHAR));
            } else {
                this.suffix = ChatColor.getLastColors(this.prefix) + lineText.substring(30);
            }
        }
    }
}
