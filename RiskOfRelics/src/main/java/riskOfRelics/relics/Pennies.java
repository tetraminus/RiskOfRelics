package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.vfx.GainGoldTextEffect;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class Pennies extends BaseRelic{


    public static final float SCALAR = 0.2f;
    public static final int DECREMENT = 1;
    public static final int AMOUNT = 15;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Pennies");
    private static final String IMAGENAME = "Pennies.png";

    public Pennies() {super(ID,IMAGENAME, RelicTier.UNCOMMON, LandingSound.CLINK);}

    @Override
    public void atBattleStart() {
        counter = AMOUNT;
        super.atBattleStart();
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if (counter <= 0) {
            super.onLoseHp(damageAmount);
            return;
        }
        player.gainGold(counter);
        this.addToBot(new VFXAction(new GainGoldTextEffect(counter)));
        this.addToBot(new VFXAction(new RainingGoldEffect(counter, true)));
        counter -= DECREMENT;
        super.onLoseHp(damageAmount);
    }

    @Override
    public void onVictory() {
        counter = -1;
        super.onVictory();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+ AMOUNT + DESCRIPTIONS[1] + DECREMENT + DESCRIPTIONS[2];
    }

}
