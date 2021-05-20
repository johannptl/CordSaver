package com.pagchake.position;

import com.pagchake.position.commanda.commands;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("cords").setExecutor(new commands());
        plugin = this;
    }

    public static Main getPlugin() {
        return plugin;
    }
}
