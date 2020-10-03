package io.github.nik2143.customgapple.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EatListener implements Listener {

    @EventHandler
    private void GappleEatEvent(PlayerItemConsumeEvent e){
        if (e.getItem().getType().equals(Material.GOLDEN_APPLE)){
            ItemStack item = e.getItem();
            NBTItem nbti = new NBTItem(item);
            if (nbti.getCompound("GappleEffect") != null){
                e.setCancelled(true);
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.getByName(nbti.getCompound("GappleEffect").getString("Effect")),
                        nbti.getCompound("GappleEffect").getInteger("Duration"),
                        nbti.getCompound("GappleEffect").getInteger("Level")));
                item.setAmount(item.getAmount() - 1);
                e.getPlayer().getInventory().setItemInHand(item);
            }
        }
    }

}
