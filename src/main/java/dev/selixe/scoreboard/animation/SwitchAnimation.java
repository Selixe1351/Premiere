package dev.selixe.scoreboard.animation;

import java.util.List;

public class SwitchAnimation {
    private long lastMillisTitle = System.currentTimeMillis();
    private int iTitle = 0;
    private String rendered = "";
    private final List<String> text;
    private final long interval;

    public SwitchAnimation(List<String> text, long interval) {
        this.text = text;
        this.interval = interval;
    }

    public String render() {
        long time = System.currentTimeMillis();
        if (this.lastMillisTitle + this.interval <= time) {
            if (this.iTitle != this.text.size() - 1) {
                ++this.iTitle;
            } else {
                this.iTitle = 0;
            }

            this.lastMillisTitle = time;
        }

        this.rendered = (String)this.text.get(this.iTitle);
        return this.rendered;
    }
}
