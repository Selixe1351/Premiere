package dev.selixe.scoreboard;

import java.util.Iterator;

public class ScoreboardRunnable implements Runnable {
    private final ScoreboardManager scoreboardManager;

    public ScoreboardRunnable(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    public void run() {
        Iterator var1 = this.scoreboardManager.getBoards().values().iterator();

        while(var1.hasNext()) {
            Scoreboard value = (Scoreboard)var1.next();
            value.update();
        }

    }
}
