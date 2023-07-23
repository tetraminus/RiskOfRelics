package riskOfRelics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

@Deprecated
public class HandMirrorAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    private static final float DURATION;

    public HandMirrorAction(final AbstractPlayer target) {

        this.setValues(target, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
        this.p = (AbstractPlayer)target;
    }

    public void update() {
        if (this.duration == DURATION) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() == 1) {
                AbstractCard tmpCard = AbstractDungeon.player.hand.getBottomCard();
                AbstractDungeon.player.hand.addToHand(tmpCard);
                AbstractDungeon.player.hand.addToHand(tmpCard.makeStatEquivalentCopy());
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard tmpCard = AbstractDungeon.handCardSelectScreen.selectedCards.getBottomCard();
                AbstractDungeon.player.hand.addToHand(tmpCard);
                AbstractDungeon.player.hand.addToHand(tmpCard.makeStatEquivalentCopy());
                AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }



    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("CopyAction");
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}
