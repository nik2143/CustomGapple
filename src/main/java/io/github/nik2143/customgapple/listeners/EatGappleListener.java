package io.github.nik2143.customgapple.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EatGappleListener implements Listener {

    @EventHandler
    private void GappleEatEvent(PlayerItemConsumeEvent e){
        if (e.getItem().getType().equals(Material.GOLDEN_APPLE)){
            ItemStack item = e.getItem();
            NBTItem nbti = new NBTItem(item);
            if (nbti.getCompound("GappleEffects") != null){
                e.setCancelled(true);
                for(String key : nbti.getCompound("GappleEffects").getKeys()){
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.getByName(nbti.getCompound("GappleEffects").getCompound(key).getString("Effect")),
                            nbti.getCompound("GappleEffects").getCompound(key).getInteger("Duration"),
                            nbti.getCompound("GappleEffects").getCompound(key).getInteger("Level")),true);
                }
                item.setAmount(item.getAmount() - 1);
                e.getPlayer().getInventory().setItemInHand(item);
            }
        }
    }

}
