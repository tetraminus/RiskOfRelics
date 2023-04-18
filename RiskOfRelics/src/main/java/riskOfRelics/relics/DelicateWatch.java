package riskOfRelics.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import riskOfRelics.DefaultMod;

public class DelicateWatch extends BaseRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, gain 1 Strength (i.e. Vajra)
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("DelicateWatch");


    public static final float DAMAGEMULTPERCENT = 50;
    private boolean broken = false;

    private static final String IMAGENAME = "DelicateWatch.png";

    public DelicateWatch() {super(ID,IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);}


    @Override
    public void atBattleStart() {
        beginLongPulse();
        super.flavorText = this.DESCRIPTIONS[2];
        broken = false;
        this.grayscale = false;
    }


    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        if (broken){
            return damage;
        } else {
            return damage * (1 + (DAMAGEMULTPERCENT/100));
        }
    }

    @Override
    public void onLoseHp(int damageAmount) {
        broken = true;
        super.flavorText = DESCRIPTIONS[1];
        this.grayscale = true;
        stopPulse();
        super.onLoseHp(damageAmount);
    }

    @Override
    public void onVictory() {
        super.flavorText = this.DESCRIPTIONS[2];
        broken = false;
        this.grayscale = false;
        stopPulse();
        super.onVictory();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+(int)DAMAGEMULTPERCENT+DESCRIPTIONS[1];

    }

}
