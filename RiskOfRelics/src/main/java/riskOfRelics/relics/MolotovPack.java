package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.powers.BurningPower;
import riskOfRelics.relics.equipment.AbstractEquipment;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class MolotovPack extends AbstractEquipment {


    public static final int AMOUNT = 10;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("MolotovPack");
    private static final String IMAGENAME = "MolotovPack.png";

    public MolotovPack() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public void onRightClick() {
        getCurrRoom().monsters.monsters.forEach(m -> {
            addToBot(new ApplyPowerAction(m, player, new BurningPower(m, player, AMOUNT)));
        });
        super.onRightClick();
    }
}