package io.github.nik2143.customgapple.commands;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.nik2143.customgapple.CustomGapple;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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

    private final CustomGapple plugin;

    public CustomGappleCommand (CustomGapple plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("customgapple")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("[CustomGapple] You can't use this command from console");
                return true;
            }
            if (!sender.isOp() || !sender.hasPermission("customgapple.use")) {
                sender.sendMessage("[CustomGapple] You haven't permissions to use this command");
                return true;
            }
            Player player = (Player) sender;
            switch (args.length) {
                case 0:
                    sender.sendMessage("Wrong sintax. Use /customgapple <EffectsNumbers> <Effects> [Levels] [Durations] [Amount] [Enchanted (true/false)]");
                    return true;
                case 1:
                    sender.sendMessage("Wrong sintax. Use /customgapple <EffectsNumber> <Effects> [Levels] [Durations] [Amount] [Enchanted (true/false)]");
                    return true;
                default:
                    if (!NumberUtils.isNumber(args[0])){
                        sender.sendMessage(args[0] + " isn't a number");
                        return true;
                    }
                    List<PotionEffectType> effects = new ArrayList<>();
                    List<Integer> levels = new ArrayList<>();
                    List<Integer> durations = new ArrayList<>();
                    boolean enchantedGapple = false;
                    int effectsnumber = Integer.parseInt(args[0]);
                    if (args.length < effectsnumber+1) {
                        sender.sendMessage("The number of effects is wrong");
                        return true;
                    }
                    for (int i = 0; i < effectsnumber; i++){
                        if (PotionEffectType.getByName(args[i+1])!=null){
                            effects.add(PotionEffectType.getByName(args[i+1]));
                        } else {
                            sender.sendMessage("Effect " + args[i+1] + " doesn't exist");
                            return true;
                        }
                    }
                    if (args.length >= effectsnumber * 2 + 1) {
                        for (int i = 0; i < effectsnumber; i++){
                            if (NumberUtils.isNumber(args[i+effectsnumber+1])){
                                levels.add(Integer.valueOf(args[i+effectsnumber+1]));
                            } else {
                                sender.sendMessage(args[i+effectsnumber+1] + " isn't a number");
                                return true;
                            }
                        }
                    }else {
                        for (int i = 0; i<effectsnumber;i++){
                            levels.add(1);
                        }
                    }
                    if (args.length >= effectsnumber * 3 + 1){
                        for (int i = 0; i < effectsnumber; i++){
                            if (NumberUtils.isNumber(args[i+effectsnumber*2+1])){
                                durations.add(Integer.valueOf(args[i+effectsnumber*2+1]));
                            } else {
                                sender.sendMessage(args[i+effectsnumber+1] + " isn't a number");
                                return true;
                            }
                        }
                    }else {
                        for (int i = 0; i < effectsnumber; i++){
                            durations.add(plugin.getConfig().getInt("Default-Duration"));
                        }
                    }
                    if  (args.length >= effectsnumber * 3 + 3){
                        enchantedGapple = Boolean.parseBoolean(args[effectsnumber * 3 + 2]);
                    }
                    if (args.length >= effectsnumber * 3 + 2){
                        if (!NumberUtils.isNumber(args[effectsnumber * 3 + 1])){
                            sender.sendMessage(args[effectsnumber * 3 + 1] + " isn't a number");
                            return true;
                        }
                        player.getInventory().addItem(CreateGapple(effectsnumber, effects, levels, durations, Integer.parseInt(args[effectsnumber * 3 + 1 ]),enchantedGapple));
                    } else {
                        player.getInventory().addItem(CreateGapple(effectsnumber, effects, levels, durations, plugin.getConfig().getInt("Default-Amount"),enchantedGapple));
                    }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("customgapple")){
            List<String> autocomplete = new ArrayList<>();
            if (args.length > 1 && NumberUtils.isNumber(args[0]) && args.length < Integer.parseInt(args[0]) + 2){
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

    private ItemStack CreateGapple (int effectsnumber, List<PotionEffectType> effects, List<Integer> level, List<Integer> duration, int amount, boolean enchantedGapple){
        NBTItem nbti = new NBTItem(new ItemStack(Material.GOLDEN_APPLE, amount, enchantedGapple ? (short) 1 : (short) 0));
        nbti.addCompound("GappleEffects");
        for (int i = 0;i<effectsnumber;i++){
            nbti.getCompound("GappleEffects").addCompound("Effect"+i);
            nbti.getCompound("GappleEffects").getCompound("Effect"+i).setString("Effect", effects.get(i).getName());
            nbti.getCompound("GappleEffects").getCompound("Effect"+i).setInteger("Duration",duration.get(i) * 20);
            nbti.getCompound("GappleEffects").getCompound("Effect"+i).setInteger("Level",level.get(i) - 1);
        }
        return nbti.getItem();
    }
}
