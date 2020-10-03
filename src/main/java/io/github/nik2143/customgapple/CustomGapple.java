package io.github.nik2143.customgapple;

import io.github.nik2143.customgapple.commands.CustomGappleCommand;
import io.github.nik2143.customgapple.listeners.EatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomGapple extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.getCommand("customgapple").setExecutor(new CustomGappleCommand(this));
        this.getCommand("customgapple").setTabCompleter(new CustomGappleCommand(this));
        Bukkit.getPluginManager().registerEvents(new EatListener(),this);
    }
}
