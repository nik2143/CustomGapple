package io.github.nik2143.customgapple;

import de.jeff_media.updatechecker.UpdateChecker;
import fr.minuskube.inv.InventoryManager;
import io.github.nik2143.customgapple.commands.CustomGappleCommand;
import io.github.nik2143.customgapple.listeners.EatGappleListener;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class CustomGapple extends JavaPlugin {

    @Getter private Configuration configuration;
    @Getter private static CustomGapple customGapple;
    @Getter private InventoryManager inventoryManager;
    @Getter private ConversationFactory conversationFactory;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        customGapple = this;
        configuration = new Configuration();
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();
        new Metrics(this, 12063);
        UpdateChecker.init(this,94323)
                .setNotifyOpsOnJoin(true)
                .setNotifyRequesters(false)
                .setNotifyByPermissionOnJoin("customgapple.update")
                .setDownloadLink(94323)
                .checkEveryXHours(24)
                .checkNow();
        if (!registerCommand(this.getName(),new CustomGappleCommand())){
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getPluginManager().registerEvents(new EatGappleListener(),this);
        conversationFactory = new ConversationFactory(this).withEscapeSequence("exit").withLocalEcho(false).withModality(false);
    }

    private boolean registerCommand(String fallback, Command command) {
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(fallback, command);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            getLogger().severe("Can't get command map");
            return false;
        }
        try {
            Method m = Bukkit.getServer().getClass().getDeclaredMethod("syncCommands");
            m.setAccessible(true);
            m.invoke(Bukkit.getServer());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) { }
        return true;
    }

}
