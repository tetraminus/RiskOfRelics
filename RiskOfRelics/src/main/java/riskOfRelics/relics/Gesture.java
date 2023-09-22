package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class Gesture extends BaseRelic {


    public static final int AMOUNT = 1;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Gesture");
    private static final String IMAGENAME = "Gesture.png";

    public Gesture() {
        super(ID, IMAGENAME, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (drawnCard.color == AbstractCard.CardColor.COLORLESS) {

            if (drawnCard.type == AbstractCard.CardType.CURSE|| drawnCard.type == AbstractCard.CardType.STATUS) {
                this.addToBot(new DamageAction(player, new DamageInfo(null, 1, DamageInfo.DamageType.THORNS)));
            }
            this.addToBot(new NewQueueCardAction(drawnCard, true, true, true));
        }
        super.onCardDraw(drawnCard);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}