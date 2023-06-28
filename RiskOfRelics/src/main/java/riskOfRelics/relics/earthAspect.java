package riskOfRelics.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.RiskOfRelics;


public class earthAspect extends BaseRelic {


    public static final int AMOUNT = 5;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("earthAspect");
    private static final String IMAGENAME = "earthAspect.png";

    public earthAspect() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);

    }

    @Override
    public void onVictory() {
        int healing = Math.round((AbstractDungeon.player.maxHealth)*(AMOUNT/100.0f));
        flash();
        AbstractDungeon.player.heal(healing) ;
        super.onVictory();
    }

    @Override
    public String getUpdatedDescription() {
        if (RiskOfRelics.AspectDescEnabled) {
            return DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];
        }
        return DESCRIPTIONS[0];
    }

}