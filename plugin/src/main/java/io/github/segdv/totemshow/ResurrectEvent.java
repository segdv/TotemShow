package io.github.segdv.totemshow;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public class ResurrectEvent implements Listener {
    private TotemShow plugin;

    public ResurrectEvent(TotemShow plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTotemUse(EntityResurrectEvent event) {
        if (!plugin.totemDetectionEnabled) {
            return;
        }

        if (event.isCancelled()) {
            return;
        }

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!player.hasPermission("totemshow.send")) {
            return;
        }

        ItemStack totem = player.getEquipment().getItem(event.getHand());
        String configText = plugin.getConfig().getString("messages.resurrect");

        Component message = MiniMessage.miniMessage().deserialize(configText,
                Placeholder.component("player", player.displayName()),
                Placeholder.component("team-player", player.teamDisplayName()),
                Placeholder.component("item-name", totem.displayName()),
                Placeholder.styling("item-hover", totem.asHoverEvent()));

        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("totemshow.receive")) {
                onlinePlayer.sendMessage(message);
            }
        }
    }
}
