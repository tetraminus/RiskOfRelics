package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.DefaultMod;
import riskOfRelics.actions.DamageRandomEntityAction;


public class Meteorite extends BaseRelic {


    public static final int AMOUNT = 5;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Meteorite");
    private static final String IMAGENAME = "Meteorite.png";

    public Meteorite() {
        super(ID, IMAGENAME, RelicTier.SHOP, LandingSound.MAGICAL);
    }



    @Override
    public void onPlayerEndTurn() {
        this.addToBot(new DamageRandomEntityAction(new DamageInfo(AbstractDungeon.player, AMOUNT, DamageInfo.DamageType.THORNS),
        AbstractGameAction.AttackEffect.FIRE));

        super.onPlayerEndTurn();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}