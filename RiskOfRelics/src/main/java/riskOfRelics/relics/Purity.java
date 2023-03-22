package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.NlothsGift;
import riskOfRelics.DefaultMod;


public class Purity extends BaseRelic {


    public static final float AMOUNT = .75f;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Purity");
    private static final String IMAGENAME = "Purity.png";

    public Purity() {
        super(ID, IMAGENAME, RelicTier.BOSS, LandingSound.MAGICAL);
    }
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;// 49
    }// 50

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;// 54
    }// 55
    public int changeRareCardRewardChance(int rareCardChance) {
        return (int) (rareCardChance-(rareCardChance * AMOUNT));// 19
    }





    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}