package io.github.nik2143.customgapple.conversations;

import com.cryptomorin.xseries.XPotion;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

import java.util.List;

public final class LevelPrompt extends NumericPrompt {

    private List<XPotion> effects;
    private XPotion effect;

    public LevelPrompt(List<XPotion> effects){
        this.effects = effects;
        effect = effects.get(0);
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return "Enter the level of the effect "+effect.toString() + " between 1 and 128 or write exit to leave";
    }

    @Override
    protected boolean isNumberValid(ConversationContext context, Number input) {
        return input.intValue() > 0 && input.intValue()<=128;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, Number level) {
        return new DurationPrompt(effects,effect,level.intValue()-1);
    }

}
