package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.DefaultMod;


public class GoatHoof extends BaseRelic {


    public static final int AMOUNT = 1;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("GoatHoof");
    private static final String IMAGENAME = "GoatHoof.png";

    public GoatHoof() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        counter =  -1;
    }


    @Override
    public void atBattleStart() {
        counter = 0;
        super.atBattleStart();
    }

    @Override
    public void atTurnStart() {
        counter++;
        if (counter % 3 == 0){
            flash();
            addToBot(new DrawCardAction(1));
            counter=0;
        }

        super.atTurnStart();
    }

    @Override
    public void onVictory() {
        counter = -1;
        super.onVictory();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}