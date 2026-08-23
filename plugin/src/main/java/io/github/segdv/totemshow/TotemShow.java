package io.github.segdv.totemshow;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

public class TotemShow extends JavaPlugin {
    protected boolean totemDetectionEnabled = true;

    @Override
    public void onEnable() {
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("totemshow", new MainCommand(this)));

        Bukkit.getPluginManager().registerEvents(new ResurrectEvent(this), this);
        saveDefaultConfig();
    }
}
