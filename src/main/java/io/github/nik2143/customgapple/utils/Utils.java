package io.github.nik2143.customgapple.utils;

import io.github.nik2143.customgapple.CustomGapple;
import io.github.nik2143.customgapple.api.Api;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public final class Utils {

    private Utils() {
        throw new InstantiationError("Can't create an instance of Utils class");
    }

    public static String color(String string){
        if (string == null) {return string;}
        return ChatColor.translateAlternateColorCodes('&',string);
    }

    public static void applyEffects(ItemStack itemStack, Player p){
        Collection<PotionEffect> activeEffects = p.getActivePotionEffects();
        for(PotionEffect effect : Api.getEffects(itemStack)){
            if (effect == null) {continue;}
            boolean haseffect = false;
            if (CustomGapple.getCustomGapple().getConfiguration().applyonlybetter){
                for (PotionEffect effect1 : activeEffects){
                    if (effect.getType().equals(effect1.getType())){
                        haseffect = true;
                        if (effect1.getAmplifier()<effect.getAmplifier()
                                || (effect1.getDuration()<effect.getDuration() && effect1.getAmplifier()==effect.getAmplifier()) ){
                            p.addPotionEffect(effect,true);
                        }
                    }
                }
            }
            if (!haseffect){
                p.addPotionEffect(effect,true);
            }
        }
    }

}
