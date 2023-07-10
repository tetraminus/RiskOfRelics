package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import riskOfRelics.RiskOfRelics;


public class Gesture extends BaseRelic {


    public static final int AMOUNT = 0;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Gesture");
    private static final String IMAGENAME = "Gesture.png";

    public Gesture() {
        super(ID, IMAGENAME, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (drawnCard.color == AbstractCard.CardColor.COLORLESS) {
            //drawnCard.freeToPlayOnce = true;
            this.addToBot(new NewQueueCardAction(drawnCard, true, true, true));
        }
        super.onCardDraw(drawnCard);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}