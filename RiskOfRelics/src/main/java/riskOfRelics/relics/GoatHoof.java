package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import riskOfRelics.RiskOfRelics;


public class GoatHoof extends BaseRelic {


    public static final int AMOUNT = 3;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("GoatHoof");
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
        if (counter % AMOUNT == 0){
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
        return DESCRIPTIONS[0]+AMOUNT+DESCRIPTIONS[1];
    }

}