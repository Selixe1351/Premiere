package dev.selixe.command.parameter.impl;

import dev.selixe.command.parameter.Processor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChatColorProcessor extends Processor<ChatColor> {

    public ChatColor process(CommandSender sender, String supplied) {
        try { return ChatColor.valueOf(supplied); }
        catch(Exception exception) {
            sender.sendMessage(ChatColor.RED + "A color by the name of '" + supplied + "' doesn't exist.");
            return null;
        }
    }

    public List<String> tabComplete(CommandSender sender, String supplied) {
        return Arrays.stream(ChatColor.values())
                .map(ChatColor::name)
                .filter(name -> name.toLowerCase().startsWith(supplied.toLowerCase()))
                .collect(Collectors.toList());
    }
}
