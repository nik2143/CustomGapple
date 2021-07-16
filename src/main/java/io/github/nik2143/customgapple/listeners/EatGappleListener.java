package io.github.nik2143.customgapple.listeners;

import com.cryptomorin.xseries.ReflectionUtils;
import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.nik2143.customgapple.utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public final class EatGappleListener implements Listener {

    @EventHandler
    public void onGappleEat(PlayerItemConsumeEvent e){
        XMaterial xMaterial = XMaterial.matchXMaterial(e.getItem().getType());
        if (xMaterial.equals(XMaterial.GOLDEN_APPLE) || xMaterial.equals(XMaterial.ENCHANTED_GOLDEN_APPLE)){
            ItemStack item = e.getItem();
            ItemStack originalItem = e.getItem();
            NBTItem nbti = new NBTItem(item);
            if (nbti.hasKey("CustomGappleEffects")||nbti.getCompound("GappleEffects")!=null){
                e.setCancelled(true);
                Utils.applyEffects(item,e.getPlayer());
                item.setAmount(item.getAmount() - 1);
                e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel()+4);
                e.getPlayer().setSaturation((float) (e.getPlayer().getSaturation()+1.2));
                if(item.getAmount() <= 0) {item = null;}
                if (ReflectionUtils.supports(9)){
                    ItemStack mainHand = e.getPlayer().getInventory().getItemInMainHand();
                    ItemStack offHand = e.getPlayer().getInventory().getItemInOffHand();

                    if(mainHand.equals(originalItem)) {e.getPlayer().getInventory().setItemInMainHand(item);}
                    else if(offHand.equals(originalItem)) {e.getPlayer().getInventory().setItemInOffHand(item);}
                    else if(mainHand.getType() == Material.GOLDEN_APPLE) {e.getPlayer().getInventory().setItemInMainHand(item);}
                    e.getPlayer().updateInventory();
                } else {
                    e.getPlayer().getInventory().setItemInHand(item);
                }
            }
        }
    }

}
