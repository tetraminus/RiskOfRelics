package riskOfRelics.util;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import riskOfRelics.cards.AbstractEquipmentCard;

public class ChargesVariable extends DynamicVariable
{
    @Override
    public String key()
    {
        return "CHARGES";
        // What you put in your localization file between ! to show your value. Eg, !myKey!.
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        return true;
        // Set to true if the value is modified from the base value.
    }



    @Override
    public int value(AbstractCard card)
    {
        if (card instanceof AbstractEquipmentCard){
            AbstractEquipmentCard aec = (AbstractEquipmentCard) card;

            return aec.CURRENT_CHARGES;
        }

        else return -1;
        // What the dynamic variable will be set to on your card. Usually uses some kind of int you store on your card.
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        if (card instanceof AbstractEquipmentCard){
            AbstractEquipmentCard aec = (AbstractEquipmentCard) card;

            return aec.CURRENT_CHARGES;
        }

        else return -1;
        // Should generally just be the above.
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        return false;
        // Set to true if this value is changed on upgrade
    }
}