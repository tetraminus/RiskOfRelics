package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.DefaultMod;


public class TopazBrooch extends BaseRelic {


    public static final int AMOUNT = 15;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("TopazBrooch");
    private static final String IMAGENAME = "TopazBrooch.png";

    public TopazBrooch() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, AMOUNT));
        super.onMonsterDeath(m);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}