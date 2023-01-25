package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.DefaultMod;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class MonsterTooth extends BaseRelic {


    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("MonsterTooth");
    private static final String IMAGENAME = "MonsterTooth.png";

    public MonsterTooth() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        this.addToBot(new HealAction(player, player, AMOUNT));
        super.onMonsterDeath(m);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}