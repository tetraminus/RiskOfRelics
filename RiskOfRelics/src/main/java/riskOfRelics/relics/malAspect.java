package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;
import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class malAspect extends BaseRelic {


    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("malAspect");
    private static final String IMAGENAME = "malAspect.png";

    public malAspect() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        //deal damage to all enemies

            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, DamageInfo.createDamageMatrix(AMOUNT, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.POISON));
        for (AbstractCreature m : AbstractDungeon.getMonsters().monsters) {

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, player, new PoisonPower(m,player, AMOUNT), AMOUNT));
        }


        super.onPlayerEndTurn();
    }

    @Override
    public String getUpdatedDescription() {
        if (RiskOfRelics.AspectDescEnabled){
            return DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2] + AMOUNT + DESCRIPTIONS[3];
        }

        return DESCRIPTIONS[0];
    }

}