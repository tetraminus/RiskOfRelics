package riskOfRelics.cards;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import riskOfRelics.RiskOfRelics;

public class WarbannerCardmod extends AbstractCardModifier
{

    @Override
    public String identifier(AbstractCard card) {
        return RiskOfRelics.makeID("WarbannerCardmod");
    }
    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost = card.cost - 1;
        if (card.cost < 0) {
            card.cost = 0;
        }
        card.costForTurn = card.cost;
    }


    public WarbannerCardmod makeCopy() {
        return new WarbannerCardmod();
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

}
