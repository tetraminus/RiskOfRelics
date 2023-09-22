package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.RiskOfRelics;


public class FocusedConvergence extends BaseRelic {


    public static final float AMOUNT = 50;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("FocusedConvergence");
    private static final String IMAGENAME = "FocusedConvergence.png";

    public FocusedConvergence() {
        super(ID, IMAGENAME, RelicTier.BOSS, LandingSound.MAGICAL);
    }
    @Override
    public void atBattleStart() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m.type == AbstractMonster.EnemyType.BOSS) {// 39
                this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 31

                    m.currentHealth = m.maxHealth - (int)((float)m.maxHealth * (AMOUNT/100f));// 27
                    m.healthBarUpdatedEvent();// 28

            }
        }

    }
    @Override
    public boolean canSpawn() {
        return AbstractDungeon.actNum <= 1;// 47
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+(int)AMOUNT+DESCRIPTIONS[1];
    }

}