package dev.selixe.utils.bukkit;

import java.awt.Color;
import net.md_5.bungee.api.ChatColor;

public class GradientBuilder {
    private Color from;
    private Color to;
    private String message;
    private boolean bold;
    private boolean italic;

    public GradientBuilder() {
    }

    public GradientBuilder(String message, Color from, Color to) {
        this(message, from, to, false, false);
    }

    public GradientBuilder(String message, Color from, Color to, boolean bold) {
        this(message, from, to, bold, false);
    }

    public GradientBuilder(String message, Color from, Color to, boolean bold, boolean italic) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.bold = bold;
        this.italic = italic;
    }

    public String build() {
        if (this.from == null) {
            throw new NullPointerException("Cannot create gradient because 'from' is null");
        } else if (this.to == null) {
            throw new NullPointerException("Cannot create gradient because 'to' is null");
        } else if (this.message == null) {
            throw new NullPointerException("Cannot create gradient because 'message' is null");
        } else {
            return this.createGradient(this.from, this.to, this.message, this.bold);
        }
    }

    public String createGradient(Color from, Color to, String message, boolean bold) {
        StringBuilder result = new StringBuilder();
        int length = message.length();

        for(int i = 0; i < length; ++i) {
            double ratio = (double)i / (double)(length - 1);
            int red = this.interpolate(from.getRed(), to.getRed(), ratio);
            int green = this.interpolate(from.getGreen(), to.getGreen(), ratio);
            int blue = this.interpolate(from.getBlue(), to.getBlue(), ratio);
            ChatColor color = ChatColor.of(new Color(red, green, blue));
            result.append(color).append(bold ? "&l" : "").append(message.charAt(i));
        }

        return result.toString();
    }

    private int interpolate(int from, int to, double ratio) {
        return (int)((double)from + ratio * (double)(to - from));
    }

    public Color getFrom() {
        return this.from;
    }

    public Color getTo() {
        return this.to;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isBold() {
        return this.bold;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public void setFrom(Color from) {
        this.from = from;
    }

    public void setTo(Color to) {
        this.to = to;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }
}
