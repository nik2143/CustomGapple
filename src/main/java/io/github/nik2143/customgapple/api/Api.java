package io.github.nik2143.customgapple.api;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Api {

    private Api(){throw new InstantiationError("Can't create an instance of Api class");}

    /**
     * Create a golden apple with effects
     * @param item the item stack that will have effects
     * @param effects the list of effects that item will give
     * @return the item with effects
     */
    public static ItemStack createGapple (ItemStack item, List<PotionEffect> effects){
        if (!XMaterial.matchXMaterial(item).parseMaterial().isEdible()){ throw new IllegalArgumentException("The item isn't a food"); }
        if (effects.isEmpty()) {throw new IllegalArgumentException("List of effects can't be wmpty");}
        NBTItem nbtItem = new NBTItem(item);
        NBTCompoundList compoundList = nbtItem.getCompoundList("CustomGappleEffects");
        for (PotionEffect effect : effects) {
            NBTCompound compound = compoundList.addCompound();
            compound.setString("type", XPotion.matchXPotion(effect.getType()).name());
            compound.setInteger("amplifier", effect.getAmplifier());
            compound.setInteger("duration", effect.getDuration());
        }
        return nbtItem.getItem();
    }

    /**
     * Get Effects attached to item
     * @param itemStack the itemstack where check effects
     * @return the list of effects attached to the item that can contains null objects
     */
    public static List<PotionEffect> getEffects(ItemStack itemStack){
        NBTItem nbtItem = new NBTItem(itemStack);
        List<PotionEffect> effects =  new ArrayList<>();
        if (nbtItem.hasKey("CustomGappleEffects")){
            NBTCompoundList compoundList = nbtItem.getCompoundList("CustomGappleEffects");
            for (NBTCompound compound : compoundList) {
                if (!compound.hasKey("type") || !compound.hasKey("duration") || !compound.hasKey("amplifier")) { continue; }
                effects.add(XPotion.valueOf(compound.getString("type"))
                        .parsePotion(compound.getInteger("duration"),compound.getInteger("amplifier")));
            }
        }

        //Keep for compatibility reasons with old versions
        if (nbtItem.hasKey("GappleEffects")){
            for(String key : nbtItem.getCompound("GappleEffects").getKeys()){
                effects.add(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(nbtItem.getCompound("GappleEffects").getCompound(key).getString("Effect"))),
                        nbtItem.getCompound("GappleEffects").getCompound(key).getInteger("Duration"),
                        nbtItem.getCompound("GappleEffects").getCompound(key).getInteger("Level")));
            }
        }

        return effects;
    }

}
