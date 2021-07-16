package io.github.nik2143.customgapple.guis;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import io.github.nik2143.customgapple.utils.ItemBuilder;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class EffectAdderGui implements InventoryProvider {
    @Override
    public void init(Player player, InventoryContents contents) {
        List<ClickableItem> items = new ArrayList<>();
        Pagination pagination = contents.pagination();
        for (XPotion potion : XPotion.values()) {
            if (potion.isSupported()){
                items.add(ClickableItem.of(new ItemBuilder(XMaterial.GRAY_DYE.parseItem())
                                .setName(potion.toString()).toItemStack(),
                        event ->{
                            if ((XMaterial.matchXMaterial(event.getCurrentItem()).equals(XMaterial.GRAY_DYE))) {
                                XMaterial.LIME_DYE.setType(event.getCurrentItem());
                            } else {
                                XMaterial.GRAY_DYE.setType(event.getCurrentItem());
                            }
                        }));
            }
        }
        pagination.setItems(items.toArray(new ClickableItem[0]));
        pagination.setItemsPerPage(45);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0).allowOverride(false));

        if (!pagination.isFirst()){
            contents.set(5, 3, ClickableItem.of(XMaterial.ARROW.parseItem(),
                    event -> GuisManager.EFFECT_ADDER.open(player, pagination.previous().getPage())));
        }

        if (!pagination.isLast()){
            contents.set(5, 5, ClickableItem.of(XMaterial.ARROW.parseItem(),
                    event -> GuisManager.EFFECT_ADDER.open(player, pagination.next().getPage())));
        }

        contents.set(5, 8, ClickableItem.of(XMaterial.GOLDEN_APPLE.parseItem(),
                event -> {
                    if (XMaterial.matchXMaterial(event.getCurrentItem()).equals(XMaterial.GOLDEN_APPLE)) {
                        XMaterial.ENCHANTED_GOLDEN_APPLE.setType(event.getCurrentItem());
                    } else {
                        XMaterial.GOLDEN_APPLE.setType(event.getCurrentItem());
                    }
                }));


    }

    @Override
    public void update(Player player, InventoryContents contents) { }
}
