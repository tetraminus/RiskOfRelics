package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import riskOfRelics.DefaultMod;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class ghostAspect extends BaseRelic {


    public static final int AMOUNT = 10;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("ghostAspect");
    private static final String IMAGENAME = "ghostAspect.png";

    public ghostAspect() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {

            this.flash();// 36
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new ApplyPowerAction(player, player, new IntangiblePlayerPower(player, 1 )));
        super.onMonsterDeath(m);

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}