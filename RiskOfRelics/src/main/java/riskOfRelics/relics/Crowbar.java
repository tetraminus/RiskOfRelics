package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.powers.CrowbarPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class Crowbar extends BaseRelic {


    public static final int AMOUNT = 75;
    public static final int THRESH = 90;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Crowbar");
    private static final String IMAGENAME = "Crowbar.png";

    public Crowbar() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        for (AbstractCreature m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(m, player, new CrowbarPower(m, player, 1), 1));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+AMOUNT+DESCRIPTIONS[1];
    }

}