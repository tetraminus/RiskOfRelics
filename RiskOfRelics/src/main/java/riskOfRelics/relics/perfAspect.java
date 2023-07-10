package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class perfAspect extends BaseRelic {


    public static final int AMOUNT = 1;
    public static final int TRIGGERAMOUNT = 4;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("perfAspect");
    private static final String IMAGENAME = "perfAspect.png";

    public perfAspect() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;

        super.atTurnStart();
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.type == AbstractCard.CardType.ATTACK) {

                this.counter++;
                if (this.counter == TRIGGERAMOUNT) {
                    flash();
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(useCardAction.target, player, new DexterityPower(useCardAction.target, -AMOUNT), -AMOUNT));
                    //add one dex to player
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new DexterityPower(player, AMOUNT), AMOUNT));
                }

        }


        super.onUseCard(targetCard, useCardAction);
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
        super.atBattleStart();
    }

    @Override
    public void onVictory() {
        this.counter = -1;
        super.onVictory();
    }

    @Override
    public String getUpdatedDescription() {
        if(RiskOfRelics.AspectDescEnabled){
            return DESCRIPTIONS[1] + TRIGGERAMOUNT + DESCRIPTIONS[2] + AMOUNT + DESCRIPTIONS[3];
        }


        return DESCRIPTIONS[0];
    }

}