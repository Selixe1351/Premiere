package dev.selixe.scoreboard;

import dev.selixe.scoreboard.ScoreboardBase.TeamMode;
import dev.selixe.scoreboard.ScoreboardBase.VersionType;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Objects;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Scoreboard extends ScoreboardBase<String> {
    private static final MethodHandle MESSAGE_FROM_STRING;
    private static final Object EMPTY_MESSAGE;

    public Scoreboard(Player player) {
        super(player);
        this.updateTitle(ScoreboardManager.getInstance().getAdapter().getTitle(player));
        this.updateLines(ScoreboardManager.getInstance().getAdapter().getLines(player));
        ScoreboardManager.getInstance().getBoards().put(player.getUniqueId(), this);
    }

    public void update() {
        this.updateTitle(ScoreboardManager.getInstance().getAdapter().getTitle(this.getPlayer()));
        this.updateLines(ScoreboardManager.getInstance().getAdapter().getLines(this.getPlayer()));
    }

    public void updateTitle(String title) {
        Objects.requireNonNull(title, "title");
        if (!VersionType.V1_13.isHigherOrEqual() && title.length() > 32) {
            throw new IllegalArgumentException("Title is longer than 32 chars");
        } else {
            super.updateTitle(title);
        }
    }

    public void updateLines(String... lines) {
        Objects.requireNonNull(lines, "lines");
        if (!VersionType.V1_13.isHigherOrEqual()) {
            int lineCount = 0;
            String[] var3 = lines;
            int var4 = lines.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String s = var3[var5];
                if (s != null && s.length() > 30) {
                    throw new IllegalArgumentException("Line " + lineCount + " is longer than 30 chars");
                }

                ++lineCount;
            }
        }

        super.updateLines(lines);
    }

    protected void sendLineChange(int score) throws Throwable {
        int maxLength = this.hasLinesMaxLength() ? 16 : 1024;
        String line = (String)this.getLineByScore(score);
        String suffix = "";
        String var10000;
        String prefix;
        if (line != null && !line.isEmpty()) {
            if (line.length() <= maxLength) {
                prefix = line;
            } else {
                int index = line.charAt(maxLength - 1) == 167 ? maxLength - 1 : maxLength;
                prefix = line.substring(0, index);
                String suffixTmp = line.substring(index);
                ChatColor chatColor = null;
                if (suffixTmp.length() >= 2 && suffixTmp.charAt(0) == 167) {
                    chatColor = ChatColor.getByChar(suffixTmp.charAt(1));
                }

                String color = ChatColor.getLastColors(prefix);
                boolean addColor = chatColor == null || chatColor.isFormat();
                var10000 = addColor ? (color.isEmpty() ? ChatColor.RESET.toString() : color) : "";
                suffix = var10000 + suffixTmp;
            }
        } else {
            var10000 = COLOR_CODES[score];
            prefix = var10000 + ChatColor.RESET;
        }

        if (prefix.length() > maxLength || suffix.length() > maxLength) {
            prefix = prefix.substring(0, maxLength);
            suffix = suffix.substring(0, maxLength);
        }

        this.sendTeamPacket(score, TeamMode.UPDATE, prefix, suffix);
    }

    protected Object toMinecraftComponent(String line) throws Throwable {
        return line != null && !line.isEmpty() ? Array.get(MESSAGE_FROM_STRING.invoke(line), 0) : EMPTY_MESSAGE;
    }

    protected String serializeLine(String value) {
        return value;
    }

    protected String emptyLine() {
        return "";
    }

    protected boolean hasLinesMaxLength() {
        return !VersionType.V1_13.isHigherOrEqual();
    }

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            Class<?> craftChatMessageClass = ScoreboardReflection.obcClass("util.CraftChatMessage");
            MESSAGE_FROM_STRING = lookup.unreflect(craftChatMessageClass.getMethod("fromString", String.class));
            EMPTY_MESSAGE = Array.get(MESSAGE_FROM_STRING.invoke(""), 0);
        } catch (Throwable var2) {
            Throwable t = var2;
            throw new ExceptionInInitializerError(t);
        }
    }
}
