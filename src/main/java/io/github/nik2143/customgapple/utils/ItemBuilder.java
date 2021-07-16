package io.github.nik2143.customgapple.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material, short subID) {
        itemStack = new ItemStack(material, 1, subID);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(material, (short) 0);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemBuilder setLore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean unsafe) {
        itemMeta.addEnchant(enchantment, level, unsafe);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments, boolean unsafe) {
        for (Enchantment current : enchantments.keySet()){
            itemMeta.addEnchant(current, enchantments.get(current), unsafe);
        }
        return this;
    }


    public ItemBuilder setDurability(int durability) {
        itemStack.setDurability((short) durability);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag itemFlag) {
        itemMeta.addItemFlags(itemFlag);
        return this;
    }

    public ItemBuilder setItemMeta(ItemMeta itemMeta) {
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack toItemStack() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
