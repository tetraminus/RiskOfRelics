package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.equipment.AbstractEquipment;


public class Tricorn extends AbstractEquipment {


    public static final int AMOUNT = 99999;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Tricorn");
    private static final String IMAGENAME = "Tricorn.png";

    public Tricorn() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);

        lockedCharges = true;
    }

    @Override
    public int GetBaseCounter() {
        return -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];

    }

    @Override
    public boolean canClick() {
        return isPlayerTurn && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.getCurrRoom().eliteTrigger;
    }

    @Override
    public void onRightClick() {

        this.addToBot(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(AMOUNT, true), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.NONE, true));
        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(AbstractDungeon.returnRandomNonCampfireRelic(AbstractDungeon.returnRandomRelicTier())));

        new TricornUsed().instantObtain();

        super.onRightClick();
    }
}