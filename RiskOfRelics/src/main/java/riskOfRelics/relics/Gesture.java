package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
            //remove from hand
            AbstractDungeon.player.hand.removeCard(drawnCard);
            AbstractDungeon.player.limbo.group.add(drawnCard);// 43
            drawnCard.current_y = -200.0F * Settings.scale;// 44
            drawnCard.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;// 45
            drawnCard.target_y = (float)Settings.HEIGHT / 2.0F;// 46
            drawnCard.targetAngle = 0.0F;// 47
            drawnCard.lighten(false);// 48
            drawnCard.drawScale = 0.12F;// 49
            drawnCard.targetDrawScale = 0.75F;// 50
            drawnCard.applyPowers();// 52

            if (drawnCard.type == AbstractCard.CardType.CURSE|| drawnCard.type == AbstractCard.CardType.STATUS) {
                this.addToBot(new DamageAction(player, new DamageInfo(null, 1, DamageInfo.DamageType.THORNS)));
            }
            this.addToBot(new NewQueueCardAction(drawnCard, true, false, true));
            this.addToBot(new UnlimboAction(drawnCard));
            this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }
        super.onCardDraw(drawnCard);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}