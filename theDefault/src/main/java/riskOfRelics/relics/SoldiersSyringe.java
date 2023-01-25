package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.AdrenalineEffect;
import riskOfRelics.DefaultMod;


public class SoldiersSyringe extends BaseRelic {


    public static final int AMOUNT = 10;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("SoldiersSyringe");
    private static final String IMAGENAME = "SoldiersSyringe.png";

    public SoldiersSyringe() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.CLINK);
        counter = 0;
    }

    @Override
    public void atTurnStartPostDraw() {
        flash();
        counter++;
        if (counter >= AMOUNT) {
            addToBot(new GainEnergyAction(1));
            addToBot(new DrawCardAction(1));
            addToBot(new VFXAction(new AdrenalineEffect(), 0.15F));
            counter = 0;
        }

        super.atTurnStartPostDraw();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}