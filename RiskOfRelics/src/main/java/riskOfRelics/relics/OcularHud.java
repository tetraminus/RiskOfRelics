package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.equipment.AbstractEquipment;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class OcularHud extends AbstractEquipment {


    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("OcularHud");
    private static final String IMAGENAME = "OcularHud.png";

    public OcularHud() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onRightClick() {
        addToBot(new ApplyPowerAction(player, player,new DoubleDamagePower(player, 1, false) ));
        super.onRightClick();
    }
}