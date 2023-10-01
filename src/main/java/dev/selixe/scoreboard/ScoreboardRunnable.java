package dev.selixe.scoreboard;

import org.bukkit.entity.Player;

public class ScoreboardRunnable implements Runnable{

    private final ScoreboardManager scoreboardManager;

    public ScoreboardRunnable(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    @Override
    public void run() {
        for (Player player : scoreboardManager.getPlugin().getServer().getOnlinePlayers()) {
            if (ScoreboardManager.getScoreboard(player) == null) {
                scoreboardManager.setup(player);
            } else {
                ScoreboardManager.getScoreboard(player).updateBoard(ScoreboardManager.listToLines(ScoreboardManager.getScoreboard(player), scoreboardManager.getAdapter().getLines(player)));
            }
        }

    }
}
