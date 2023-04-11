package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import riskOfRelics.DefaultMod;
import riskOfRelics.actions.KjarosBandAction;


public class KjarosBand extends BaseRelic {


    public static final int AMOUNT = 16;
    public static final int THRESHOLD = 20;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("KjarosBand");
    private static final String IMAGENAME = "KjarosBand.png";

    public KjarosBand() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type == DamageInfo.DamageType.NORMAL && damageAmount > THRESHOLD) {
            flash();
            this.addToBot(new VFXAction(new FireBurstParticleEffect(target.hb.cX, target.hb.cY)));
            this.addToBot(new KjarosBandAction(AMOUNT));
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + THRESHOLD + DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];
    }

}