package riskOfRelics.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.DefaultMod;


public class APRounds extends BaseRelic {

    public boolean isBoss;
    public static final float AMOUNT = 20;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("APRounds");
    private static final String IMAGENAME = "APRounds.png";

    public APRounds() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);
        isBoss = false;
    }

    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        if (isBoss){return damage + Math.round(damage * AMOUNT / 100f);}
        else {return super.atDamageModify(damage,c);}
    }

    @Override
    public void atPreBattle() {

    // 38

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m.type == AbstractMonster.EnemyType.BOSS) {// 39
                isBoss = true;// 40
                break;
            }
        }

        if (isBoss) {// 44
            this.beginLongPulse();// 45
            this.flash();
        }
    super.atPreBattle();
    }

    public void onVictory() {
        if (this.pulse) {// 53
            isBoss = false;
            this.stopPulse();// 55
        }

    }// 57
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+AMOUNT+DESCRIPTIONS[1];
    }

}