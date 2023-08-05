package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.equipment.AbstractEquipment;


public class TestEquipment extends AbstractEquipment {


    public static final int AMOUNT = 5;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("TestEquipment");
    private static final String IMAGENAME = "TestEquipment.png";

    public TestEquipment() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {


    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public String[] CLICKABLE_DESCRIPTIONS() {
        return new String[]{DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1]};
    }

    @Override
    public void onRightClick() {
        //check if its the player's turn

        this.addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(AMOUNT, true), DamageInfo.DamageType.THORNS, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
        super.onRightClick();
    }
}