package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.RiskOfRelics;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class earthAspect extends BaseRelic {


    public static final int AMOUNT = 20;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("earthAspect");
    private static final String IMAGENAME = "earthAspect.png";

    public earthAspect() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);

    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        counter++;
        if (counter >= 3) {
            this.addToBot(new HealAction(player,player,AMOUNT));
        }
        super.onMonsterDeath(m);
    }

    @Override
    public String getUpdatedDescription() {
        if (RiskOfRelics.AspectDescEnabled) {
            return DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];
        }
        return DESCRIPTIONS[0];
    }

}