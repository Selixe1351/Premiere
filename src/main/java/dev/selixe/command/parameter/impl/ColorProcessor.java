package dev.selixe.command.parameter.impl;

import dev.selixe.command.parameter.Processor;
import dev.selixe.utils.bukkit.ColorUtils;
import org.bukkit.command.CommandSender;

import java.awt.*;

public class ColorProcessor extends Processor<Color> {
    @Override
    public Color process(CommandSender sender, String supplied) {
        if (supplied.startsWith("#") && supplied.length() == 6) {
            return Color.decode(supplied);
        }
        sender.sendMessage(ColorUtils.translate("&cThere is no hex color by this value."));
        return null;
    }
}
