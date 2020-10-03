package io.github.nik2143.customgapple.commands;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.nik2143.customgapple.CustomGapple;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomGappleCommand implements CommandExecutor, TabCompleter {

    private CustomGapple plugin;

    public CustomGappleCommand (CustomGapple plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("customgapple")){
            if (!(sender instanceof Player)) {
                sender.sendMessage("[CustomGapple] You can't use this command from console");
                return true;
            }
            if (!sender.isOp() || !sender.hasPermission("customgapple.use")){
                sender.sendMessage("[CustomGapple] You haven't permissions to use this command");
                return true;
            }
            Player player = (Player) sender;
            switch (args.length){
                case 0:
                    sender.sendMessage("Wrong sintax. Use /customgapple <Effect> [Duration] [Level] [Amount]");
                    return true;
                case 1:
                    if (PotionEffectType.getByName(args[0])==null){
                        sender.sendMessage("Unknown potion effect");
                        return true;
                    }
                    player.getInventory().addItem(CreateGapple(PotionEffectType.getByName(args[0]),plugin.getConfig().getInt("Default-Duration"),1, plugin.getConfig().getInt("Default-Amount")));
                    break;
                case 2:
                    if (PotionEffectType.getByName(args[0])==null){
                        sender.sendMessage("Unknown potion effect");
                        return true;
                    }
                    if (!NumberUtils.isNumber(args[1])){
                        sender.sendMessage(args[1] + " isn't a number");
                        return true;
                    }
                    player.getInventory().addItem(CreateGapple(PotionEffectType.getByName(args[0]), Integer.parseInt(args[1]),1, plugin.getConfig().getInt("Default-Amount")));
                    break;
                case 3:
                    if (PotionEffectType.getByName(args[0])==null){
                        sender.sendMessage("Unknown potion effect");
                        return true;
                    }
                    if (!NumberUtils.isNumber(args[1])){
                        sender.sendMessage(args[1] + " isn't a number");
                        return true;
                    }
                    if (!NumberUtils.isNumber(args[2])){
                        sender.sendMessage(args[2] + " isn't a number");
                        return true;
                    }
                    player.getInventory().addItem(CreateGapple(PotionEffectType.getByName(args[0]), Integer.parseInt(args[1]),Integer.parseInt(args[2]), plugin.getConfig().getInt("Default-Amount")));
                    break;
                case 4:
                    if (PotionEffectType.getByName(args[0])==null){
                        sender.sendMessage("Unknown potion effect");
                        return true;
                    }
                    if (!NumberUtils.isNumber(args[1])){
                        sender.sendMessage(args[1] + " isn't a number");
                        return true;
                    }
                    if (!NumberUtils.isNumber(args[2])){
                        sender.sendMessage(args[2] + " isn't a number");
                        return true;
                    }
                    if (!NumberUtils.isNumber(args[3])){
                        sender.sendMessage(args[3] + " isn't a number");
                        return true;
                    }
                    player.getInventory().addItem(CreateGapple(PotionEffectType.getByName(args[0]), Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3])));
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("customgapple")){
            List<String> autocomplete = new ArrayList<String>();
            if (args.length == 1){
                for (PotionEffectType effect : PotionEffectType.values()){
                    if(effect == null) {
                        continue;
                    }
                    autocomplete.add(StringUtils.capitalize(effect.getName().toLowerCase()));
                }
                return autocomplete;
            }
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    private ItemStack CreateGapple (PotionEffectType effect,int duration,int level,int amount){
        NBTItem nbti = new NBTItem(new ItemStack(Material.GOLDEN_APPLE, amount));
        nbti.addCompound("GappleEffect");
        nbti.getCompound("GappleEffect").setString("Effect", effect.getName());
        nbti.getCompound("GappleEffect").setInteger("Duration",duration * 20);
        nbti.getCompound("GappleEffect").setInteger("Level",level - 1);
        return nbti.getItem();
    }
}
