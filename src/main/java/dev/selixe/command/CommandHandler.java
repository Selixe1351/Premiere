package dev.selixe.command;

import com.google.common.reflect.ClassPath;
import dev.selixe.command.help.Help;
import dev.selixe.command.help.HelpNode;
import dev.selixe.command.node.CommandNode;
import dev.selixe.command.parameter.ParamProcessor;
import dev.selixe.command.parameter.Processor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class CommandHandler {
    @Getter @Setter private static Plugin plugin;

    /**
     * Registers commands based off a file path
     * @param path Path
     */
    @SneakyThrows
    public void registerCommands(String path, Plugin plugin) {
        ClassPath.from(plugin.getClass().getClassLoader()).getAllClasses().stream()
                .filter(info -> info.getPackageName().startsWith(path))
                .forEach(info -> registerCommands(info.load(), plugin));
    }

    /**
     * Registers the commands in the class
     * @param commandClass Class
     */
    @SneakyThrows
    public void registerCommands(Class<?> commandClass, Plugin plugin) {
        CommandHandler.setPlugin(plugin);
        registerCommands(commandClass.newInstance());
    }

    /**
     * Registers the commands in the class
     * @param commandClass Class
     */
    private void registerCommands(Object commandClass) {
        Arrays.stream(commandClass.getClass().getDeclaredMethods()).forEach(method -> {
            Command command = method.getAnnotation(Command.class);
            if(command == null) return;

            new CommandNode(commandClass, method, command);
        });

        Arrays.stream(commandClass.getClass().getDeclaredMethods()).forEach(method -> {
            Help help = method.getAnnotation(Help.class);
            if(help == null) return;

            HelpNode helpNode = new HelpNode(commandClass, help.names(), help.permission(), method);
            CommandNode.getNodes().forEach(node -> node.getNames().forEach(name -> Arrays.stream(help.names())
                    .map(String::toLowerCase)
                    .filter(helpName -> name.toLowerCase().startsWith(helpName))
                    .forEach(helpName -> node.getHelpNodes().add(helpNode))));
        });
    }

    /**
     * Registers processors based off a file path
     * @param path Path
     */
    @SneakyThrows
    public void registerProcessors(String path, Plugin plugin) {
        ClassPath.from(plugin.getClass().getClassLoader()).getAllClasses().stream()
                .filter(info -> info.getPackageName().startsWith(path))
                .filter(info -> info.load().getSuperclass().equals(Processor.class))
                .forEach(info -> {
                    try { ParamProcessor.createProcessor((Processor<?>) info.load().newInstance());
                    } catch(Exception exception) { exception.printStackTrace(); }
                });
    }

    /**
     * Registers the processor in the class
     * @param processorClass Class
     */
    @SneakyThrows
    public void registerProcessor(Class<?> processorClass, Plugin plugin) {
        registerProcessors(processorClass.getPackageName(), plugin);
    }
}
