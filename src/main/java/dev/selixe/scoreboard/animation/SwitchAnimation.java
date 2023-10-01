package dev.selixe.scoreboard.animation;

import java.util.List;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class SwitchAnimation{

    private long lastMillisTitle = System.currentTimeMillis();
    private int iTitle = 0;

    private String rendered = "";

    private final List<String> text;
    private final long interval;

    public SwitchAnimation(List<String> text, long interval) {
        this.text = text;
        this.interval = interval;
    }

   /* @Override
    public void run() {
        long time = System.currentTimeMillis();

        if (lastMillisTitle + interval <= time) {
            if (iTitle != text.size() - 1) {
                iTitle++;
            } else {
                iTitle = 0;
            }
            lastMillisTitle = time;
        }
        rendered = text.get(iTitle);
    } */



    public String render() {
        long time = System.currentTimeMillis();

        if (lastMillisTitle + interval <= time) {
            if (iTitle != text.size() - 1) {
                iTitle++;
            } else {
                iTitle = 0;
            }
            lastMillisTitle = time;
        }
        rendered = text.get(iTitle);
        return rendered;
    }
}
