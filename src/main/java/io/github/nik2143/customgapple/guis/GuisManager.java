package io.github.nik2143.customgapple.guis;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import fr.minuskube.inv.InventoryListener;
import fr.minuskube.inv.SmartInventory;
import io.github.nik2143.customgapple.CustomGapple;
import io.github.nik2143.customgapple.conversations.LevelPrompt;
import org.bukkit.ChatColor;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public final class GuisManager {

    private GuisManager(){throw new InstantiationError("Can't create an instance of GuisManager class");}

    public static final SmartInventory EFFECT_ADDER = SmartInventory.builder()
            .id("effectSelect")
            .provider(new EffectAdderGui())
            .type(InventoryType.CHEST)
            .manager(CustomGapple.getCustomGapple().getInventoryManager())
            .title(ChatColor.RED + "Effect Adder")
            .listener(new InventoryListener<>(InventoryCloseEvent.class,event -> {
                List<ItemStack> content = Arrays.asList(event.getInventory().getContents());
                ItemStack gappleType = content.stream()
                        .filter(Objects::nonNull)
                        .filter(item->XMaterial.matchXMaterial(item).equals(XMaterial.GOLDEN_APPLE)
                                || XMaterial.matchXMaterial(item).equals(XMaterial.ENCHANTED_GOLDEN_APPLE))
                        .findFirst().get();
                List<XPotion> effects = content.stream()
                        .filter(Objects::nonNull)
                        .filter((item)->XMaterial.matchXMaterial(item).equals(XMaterial.LIME_DYE))
                        .filter(ItemStack::hasItemMeta)
                        .filter(item-> Objects.requireNonNull(item.getItemMeta()).hasDisplayName())
                        .map(item->XPotion.matchXPotion(item.getItemMeta().getDisplayName().toUpperCase(Locale.ROOT).replaceAll(" ","_")).get())
                        .collect(Collectors.toList());
                if (effects.isEmpty()) {return;}
                HashMap<Object,Object> initialData = new HashMap<>();
                initialData.put("gappleType",gappleType);
                Conversation c = CustomGapple.getCustomGapple().getConversationFactory().withInitialSessionData(initialData)
                        .withFirstPrompt(new LevelPrompt(effects))
                        .buildConversation((Player)event.getPlayer());
                c.begin();
            }))
            .build();

}
