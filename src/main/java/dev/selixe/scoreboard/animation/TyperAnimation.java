package dev.selixe.scoreboard.animation;

import java.util.List;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class TyperAnimation implements Runnable {

    private int textIndex = 0;
    private int letterIndex = 0;

    private Long appendAt = null;
    private Long pausedAt = null;

    private String rendered = "";

    private List<String> text;

    public TyperAnimation(List<String> text) {
        this.text = text;
    }

    @Override
    public void run() {
        if (pausedAt == null) {
            String currentText = text.get(textIndex);

            if (appendAt == null || System.currentTimeMillis() >= appendAt + 100) {
                rendered += currentText.charAt(++letterIndex);
                appendAt = System.currentTimeMillis();

                if (letterIndex >= currentText.length() - 1) {
                    pausedAt = System.currentTimeMillis();
                }
            }
        } else {
            if (System.currentTimeMillis() >= pausedAt + 2000) {
                letterIndex = 0;
                pausedAt = null;
                rendered = "";

                if (++textIndex >= text.size()) {
                    textIndex = 0;
                }
            }
        }
    }

    public String render() {
        return rendered;
    }

}
