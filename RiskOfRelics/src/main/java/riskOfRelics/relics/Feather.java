package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import riskOfRelics.RiskOfRelics;


public class Feather extends BaseRelic {


    public static final int AMOUNT = 3;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Feather");
    private static final String IMAGENAME = "Feather.png";

    public Feather() {
        super(ID, IMAGENAME, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        counter = 0;
        super.atBattleStart();
    }

    @Override
    public void atTurnStart() {
        counter++;

        if (counter == AMOUNT) {
            flash();
            addToBot(new SkipEnemiesTurnAction());

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