package io.github.nik2143.customgapple.commands;

import io.github.nik2143.customgapple.CustomGapple;
import io.github.nik2143.customgapple.guis.GuisManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public final class CustomGappleCommand extends BukkitCommand {
    public CustomGappleCommand() {
        super("customgapple");
        setPermission("customgapple.use");
        setPermissionMessage(CustomGapple.getCustomGapple().getConfiguration().nopermsmsg);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }
        if (((Player) sender).isConversing()){
            sender.sendMessage("You are already creating a custom apple");
            return true;
        }
        if (!testPermission(sender)) { return true; }
        GuisManager.EFFECT_ADDER.open((Player) sender);
        return true;
    }
}
