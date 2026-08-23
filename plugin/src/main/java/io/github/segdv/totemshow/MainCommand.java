package io.github.segdv.totemshow;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Stream;
import org.bukkit.command.CommandSender;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MainCommand implements BasicCommand {
    private TotemShow plugin;

    public MainCommand(TotemShow plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        CommandSender sender = source.getSender();

        if (args.length != 1) {
            for (String rawMessage : plugin.getConfig().getStringList("messages.help")) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize(rawMessage));
            }

            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(MiniMessage.miniMessage()
                    .deserialize(plugin.getConfig().getString("messages.reload")));
            return;
        }

        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "on" -> plugin.totemDetectionEnabled = true;
            case "off" -> plugin.totemDetectionEnabled = false;
            case "toggle" -> plugin.totemDetectionEnabled = !plugin.totemDetectionEnabled;
            default -> {
                for (String rawMessage : plugin.getConfig().getStringList("messages.help")) {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(rawMessage));
                }
                return;
            }
        };

        if (plugin.totemDetectionEnabled) {
            sender.sendMessage(MiniMessage.miniMessage()
                    .deserialize(plugin.getConfig().getString("messages.enable")));
        } else {
            sender.sendMessage(MiniMessage.miniMessage()
                    .deserialize(plugin.getConfig().getString("messages.disable")));
        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack source, String[] args) {
        if (args.length > 1) {
            return Collections.emptyList();
        }

        String input = args.length == 0 ? "" : args[0].toLowerCase(Locale.ROOT);

        return Stream.of("on", "off", "toggle", "reload").filter(option -> option.startsWith(input))
                .toList();
    }

    @Override
    public String permission() {
        return "totemshow.admin";
    }
}
