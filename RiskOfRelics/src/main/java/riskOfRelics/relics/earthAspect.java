package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class earthAspect extends BaseRelic {


    public static final int AMOUNT = 20;
    public static final int TRIGGER = 6;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("earthAspect");
    private static final String IMAGENAME = "earthAspect.png";

    public earthAspect() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);
        counter = 0;

    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if (!m.hasPower("Minion")) {
            counter++;
            if (counter >= TRIGGER) {
                this.addToBot(new HealAction(player,player,AMOUNT));
                counter = 0;
            }
        }

        super.onMonsterDeath(m);
    }

    @Override
    public String getUpdatedDescription() {
        if (RiskOfRelics.AspectDescEnabled) {
            return DESCRIPTIONS[1] + TRIGGER + DESCRIPTIONS[2] + AMOUNT + DESCRIPTIONS[3];
        }
        return DESCRIPTIONS[0];
    }

}