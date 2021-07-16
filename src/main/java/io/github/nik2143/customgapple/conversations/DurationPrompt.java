package io.github.nik2143.customgapple.conversations;

import com.cryptomorin.xseries.XPotion;
import io.github.nik2143.customgapple.api.Api;
import io.github.nik2143.customgapple.utils.Utils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public final class DurationPrompt extends NumericPrompt {

    private List<XPotion> effects;
    private XPotion effect;
    private int level;

    public DurationPrompt(List<XPotion> effects,XPotion effect,int level){
        this.effects = effects;
        this.effect = effect;
        this.level = level;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return "Enter the duration in seconds of the effect "+effect.toString() + " or write exit to leave";
    }

    @Override
    protected boolean isNumberValid(ConversationContext context, Number input){
        return input.intValue()>0;
    }

    @Override @SuppressWarnings("unchecked")
    protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
        ArrayList<PotionEffect> effectsReal;
        if (!(context.getSessionData("effectApply") instanceof ArrayList)) {
            effectsReal = new ArrayList<>();
        }
        else {effectsReal = (ArrayList<PotionEffect>) context.getSessionData("effectApply");}
        effectsReal.add(effect.parsePotion(input.intValue()*20,level));
        effects.remove(effect);
        context.setSessionData("effectApply",effectsReal);
        if (!effects.isEmpty()) {return new LevelPrompt(effects);}
        ((Player)context.getForWhom()).getInventory().addItem(Api.createGapple((ItemStack) context.getSessionData("gappleType"), effectsReal));
        context.getAllSessionData().clear();
        context.getForWhom().sendRawMessage(Utils.color("Golden Apple created succesfully"));
        return Prompt.END_OF_CONVERSATION;
    }

}
