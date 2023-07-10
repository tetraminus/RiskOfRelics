package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class ghostAspect extends BaseRelic {


    public static final int AMOUNT = 1;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("ghostAspect");
    private static final String IMAGENAME = "ghostAspect.png";

    public ghostAspect() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {

            this.flash();// 36
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new ApplyPowerAction(player, player, new IntangiblePlayerPower(player, AMOUNT )));
        super.onMonsterDeath(m);

    }

    @Override
    public String getUpdatedDescription() {
        if(RiskOfRelics.AspectDescEnabled){
            return DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];
        }
        return DESCRIPTIONS[0];
    }

}