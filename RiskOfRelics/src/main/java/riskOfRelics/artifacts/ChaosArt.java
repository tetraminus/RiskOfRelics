package riskOfRelics.artifacts;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import riskOfRelics.cards.ChaosCardmod;

public class ChaosArt {
    public static void ApplyMod(AbstractCard c) {
        CardModifierManager.addModifier(c, new ChaosCardmod());
    }

}
