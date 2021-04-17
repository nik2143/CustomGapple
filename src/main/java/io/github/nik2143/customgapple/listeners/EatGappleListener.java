package io.github.nik2143.customgapple.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.nik2143.customgapple.CustomGapple;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class EatGappleListener implements Listener {

    @EventHandler
    private void GappleEatEvent(PlayerItemConsumeEvent e){
        if (e.getItem().getType().equals(Material.GOLDEN_APPLE)){
            ItemStack item = e.getItem();
            ItemStack originalItem = e.getItem();
            NBTItem nbti = new NBTItem(item);
            if (nbti.getCompound("GappleEffects") != null){
                e.setCancelled(true);
                applyEffects(nbti,e.getPlayer());
                item.setAmount(item.getAmount() - 1);
                e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel()+4);
                e.getPlayer().setSaturation((float) (e.getPlayer().getSaturation()+1.2));

                if(item.getAmount() <= 0) item = null;

                try {
                    ItemStack mainHand = e.getPlayer().getInventory().getItemInMainHand();
                    ItemStack offHand = e.getPlayer().getInventory().getItemInOffHand();

                    if(mainHand.equals(originalItem)) e.getPlayer().getInventory().setItemInMainHand(item);
                    else if(offHand.equals(originalItem)) e.getPlayer().getInventory().setItemInOffHand(item);
                    else if(mainHand.getType() == Material.GOLDEN_APPLE)
                        e.getPlayer().getInventory().setItemInMainHand(item);
                } catch (NoSuchMethodError error){
                    e.getPlayer().getInventory().setItemInHand(item);
                }
            }
        }
    }

    private void applyEffects(NBTItem nbti, Player p){
        Collection<PotionEffect> activeEffects = p.getActivePotionEffects();
        for(String key : nbti.getCompound("GappleEffects").getKeys()){
            boolean haseffect = false;
            PotionEffect pe = new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(nbti.getCompound("GappleEffects").getCompound(key).getString("Effect"))),
                    nbti.getCompound("GappleEffects").getCompound(key).getInteger("Duration"),
                    nbti.getCompound("GappleEffects").getCompound(key).getInteger("Level"));
            if (CustomGapple.getCustomGapple().getConfiguration().applyonlybetter){
                for (PotionEffect effect : activeEffects){
                    if (effect.getType().equals(pe.getType())){
                        haseffect = true;
                        if (effect.getAmplifier()<pe.getAmplifier() || (effect.getDuration()<pe.getDuration() && effect.getAmplifier()==pe.getAmplifier()) ){
                            p.addPotionEffect(pe,true);
                        }
                    }
                }
            }
            if (!haseffect){
                p.addPotionEffect(pe,true);
            }
        }
    }

}
