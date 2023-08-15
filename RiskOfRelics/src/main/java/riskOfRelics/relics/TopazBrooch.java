package riskOfRelics.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.RiskOfRelics;


public class TopazBrooch extends BaseRelic {


    public static final int AMOUNT = 8;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("TopazBrooch");
    private static final String IMAGENAME = "TopazBrooch.png";

    public TopazBrooch() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        this.addToBot(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, AMOUNT));
        super.onMonsterDeath(m);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}