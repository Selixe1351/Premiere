package dev.selixe.utils.bukkit;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class Constants {

    public static final String VERTICAL_BAR;
    public static final String CAUTION;
    public static final String ARROW_LEFT;
    public static final String ARROW_RIGHT;
    public static final String ARROWS_LEFT;
    public static final String ARROWS_RIGHT;
    public static final String HEART;
    public static final String DOLLAR;
    public static final String COIN;
    public static final String XP;
    public static final String DOT;
    public static final String CURVED_ARROW;

    static {
        VERTICAL_BAR = StringEscapeUtils.unescapeJava("┃");
        CAUTION = StringEscapeUtils.unescapeJava("⚠");
        ARROW_LEFT = StringEscapeUtils.unescapeJava("\u25c0"); // ◀
        ARROW_RIGHT = StringEscapeUtils.unescapeJava("\u25b6"); // ▶
        ARROWS_LEFT = StringEscapeUtils.unescapeJava("«");
        ARROWS_RIGHT = StringEscapeUtils.unescapeJava("»");
        HEART = StringEscapeUtils.unescapeJava("\u2764");
        DOLLAR = StringEscapeUtils.unescapeJava("$");
        COIN = StringEscapeUtils.unescapeJava("⛃");
        XP = StringEscapeUtils.unescapeJava("✪");
        DOT = "\u25cf";
        CURVED_ARROW = StringEscapeUtils.unescapeJava("➥");
    }


}
