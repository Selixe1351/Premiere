package dev.selixe.command.impl;

import java.awt.Color;

import dev.selixe.command.Command;
import dev.selixe.command.parameter.Param;
import dev.selixe.utils.bukkit.GradientBuilder;
import org.bukkit.command.CommandSender;

public class ColorCommand {
    public ColorCommand() {
    }

    @Command(
        names = {"color"},
        permission = "premiere.color"
    )
    public void execute(CommandSender sender, @Param(name = "color") Color color) {
        GradientBuilder builder = new GradientBuilder();
        builder.setMessage("This is a colored message");
        builder.setFrom(color);
        builder.setTo(color);
        sender.sendMessage(builder.build());
    }

    @Command(
        names = {"gradient"},
        permission = "premiere.gradient"
    )
    public void execute(CommandSender sender, @Param(name = "from") Color from, @Param(name = "to") Color to) {
        GradientBuilder builder = new GradientBuilder("This is a gradient message", from, to);
        sender.sendMessage(builder.build());
    }
}
