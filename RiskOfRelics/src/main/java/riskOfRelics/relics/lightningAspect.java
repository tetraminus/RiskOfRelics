package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.DefaultMod;
import riskOfRelics.powers.lightningPower;


public class lightningAspect extends BaseRelic {


    public static final int AMOUNT = 50;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("lightningAspect");
    private static final String IMAGENAME = "lightningAspect.png";

    public lightningAspect() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }
    public boolean usedThisTurn = false;
    @Override

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {

        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != null && damageAmount > 0 && !usedThisTurn) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, info.owner, new lightningPower(target, damageAmount/2), 1));
            usedThisTurn = true;
            grayscale = true;
        }


    }

    @Override
    public void atBattleStart() {
        usedThisTurn = false;
        super.atBattleStart();
    }

    @Override
    public void atTurnStart() {
        usedThisTurn = false;
        grayscale = false;
        super.atTurnStart();
    }

    @Override
    public void onVictory() {
        usedThisTurn = false;
        grayscale = false;
        super.onVictory();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}