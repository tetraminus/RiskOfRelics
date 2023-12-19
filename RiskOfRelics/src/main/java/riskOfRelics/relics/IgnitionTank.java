package riskOfRelics.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import riskOfRelics.RiskOfRelics;


public class IgnitionTank extends BaseRelic implements OnApplyPowerRelic, OnReceivePowerRelic {


    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("IgnitionTank");
    private static final String IMAGENAME = "IgnitionTank.png";

    public IgnitionTank() {
        super(ID, IMAGENAME, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {


    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }


    @Override
    public boolean onApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower.type == AbstractPower.PowerType.DEBUFF) {
            flash();
            abstractPower.amount += AMOUNT;
        }
        return true;
    }

    @Override
    public int onApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (power.type == AbstractPower.PowerType.DEBUFF) {
            flash();
            stackAmount += AMOUNT;
        }
        return OnApplyPowerRelic.super.onApplyPowerStacks(power, target, source, stackAmount);
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature) {
        if (abstractPower.type == AbstractPower.PowerType.DEBUFF) {
            flash();
            abstractPower.amount += AMOUNT;
        }
        return true;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature source, int stackAmount) {
        if (power.type == AbstractPower.PowerType.DEBUFF) {
            flash();
            stackAmount += AMOUNT;
        }
        return OnReceivePowerRelic.super.onReceivePowerStacks(power, source, stackAmount);
    }
}