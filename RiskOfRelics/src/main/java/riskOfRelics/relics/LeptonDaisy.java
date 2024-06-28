package riskOfRelics.relics;


import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.RiskOfRelics;


import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class LeptonDaisy extends BaseRelic {


    public static final int AMOUNT = 25;
    public static final int THRESH = 50;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("LeptonDaisy");
    private static final String IMAGENAME = "LeptonDaisy.png";

    public LeptonDaisy() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);

        if (target instanceof AbstractMonster){
            AbstractMonster targetMonster = (AbstractMonster) target;
            if (targetMonster.type != AbstractMonster.EnemyType.BOSS) {
                return;
            }
        } else {
            return;
        }

        int hpAfter = target.currentHealth - damageAmount;
        //if the target is less than half threshold percent hp
        // ignore if the target was already below the threshold
        if (hpAfter <= target.maxHealth * THRESH / 100f && target.currentHealth > target.maxHealth * THRESH / 100f) {
           addToBot(new RelicAboveCreatureAction(target, this));
           addToBot(new HealAction(player, player, AMOUNT));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+THRESH+DESCRIPTIONS[1]+AMOUNT+DESCRIPTIONS[2];
    }

}