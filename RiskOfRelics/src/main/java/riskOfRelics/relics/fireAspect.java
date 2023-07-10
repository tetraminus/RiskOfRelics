package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.powers.BurningPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class fireAspect extends BaseRelic{



    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("fireAspect");
    private static final String IMAGENAME = "fireAspect.png";

    public fireAspect() {super(ID,IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);}

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {

        if (target != player && info.type== DamageInfo.DamageType.NORMAL && info.owner != null && damageAmount > 0) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, info.owner, new BurningPower(target, info.owner, Math.round(AMOUNT)), AMOUNT));
        }

    }

    @Override
    public String getUpdatedDescription() {
        if (RiskOfRelics.AspectDescEnabled) {
            return DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];
        }
        return DESCRIPTIONS[0];
    }

}
