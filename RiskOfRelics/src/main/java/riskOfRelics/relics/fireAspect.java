package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.DefaultMod;
import riskOfRelics.powers.Burning;

public class fireAspect extends BaseRelic{


    public static final float SCALAR = 0.2f;
    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("fireAspect");
    private static final String IMAGENAME = "fireAspect.png";

    public fireAspect() {super(ID,IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);}

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        flash();
        if (info.type== DamageInfo.DamageType.NORMAL && info.owner != null && damageAmount > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, info.owner, new Burning(target, info.owner, Math.round(AMOUNT)), AMOUNT));
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
