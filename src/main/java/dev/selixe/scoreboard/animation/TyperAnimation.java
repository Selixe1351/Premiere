package dev.selixe.scoreboard.animation;

import java.util.List;

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

    public void run() {
        if (this.pausedAt == null) {
            String currentText = (String)this.text.get(this.textIndex);
            if (this.appendAt == null || System.currentTimeMillis() >= this.appendAt + 100L) {
                String var10001 = this.rendered;
                this.rendered = var10001 + currentText.charAt(++this.letterIndex);
                this.appendAt = System.currentTimeMillis();
                if (this.letterIndex >= currentText.length() - 1) {
                    this.pausedAt = System.currentTimeMillis();
                }
            }
        } else if (System.currentTimeMillis() >= this.pausedAt + 2000L) {
            this.letterIndex = 0;
            this.pausedAt = null;
            this.rendered = "";
            if (++this.textIndex >= this.text.size()) {
                this.textIndex = 0;
            }
        }

    }

    public String render() {
        return this.rendered;
    }
}
