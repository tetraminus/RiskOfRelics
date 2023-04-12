package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import riskOfRelics.DefaultMod;


public class Feather extends BaseRelic {


    public static final int AMOUNT = 1;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Feather");
    private static final String IMAGENAME = "Feather.png";

    public Feather() {
        super(ID, IMAGENAME, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        beginPulse();
        this.addToBot(new SkipEnemiesTurnAction());// 36
        super.atBattleStart();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}