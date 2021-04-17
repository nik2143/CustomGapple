package io.github.nik2143.customgapple;

import io.github.nik2143.customgapple.commands.CustomGappleCommand;
import io.github.nik2143.customgapple.listeners.EatGappleListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomGapple extends JavaPlugin {

    private Configuration configuration;
    private static CustomGapple customGapple;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        customGapple = this;
        configuration = new Configuration();
        getCommand("customgapple").setExecutor(new CustomGappleCommand());
        getCommand("customgapple").setTabCompleter(new CustomGappleCommand());
        Bukkit.getPluginManager().registerEvents(new EatGappleListener(),this);
    }

    public Configuration getConfiguration() { return configuration; }

    public static CustomGapple getCustomGapple() { return customGapple; }
}
