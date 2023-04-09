package riskOfRelics.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import riskOfRelics.DefaultMod;
import riskOfRelics.util.TextureLoader;

import static riskOfRelics.DefaultMod.makeRelicOutlinePath;
import static riskOfRelics.DefaultMod.makeRelicPath;

public class DelicateWatch extends BaseRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, gain 1 Strength (i.e. Vajra)
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("DelicateWatch");


    public static final float DAMAGEMULTPERCENT = 0.5f;
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
            return damage * (1 + DAMAGEMULTPERCENT);
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
        return DESCRIPTIONS[0];

    }

}
