package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import riskOfRelics.DefaultMod;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class Crowbar extends BaseRelic {


    public static final int AMOUNT = 75;
    public static final int THRESH = 90;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Crowbar");
    private static final String IMAGENAME = "Crowbar.png";

    public Crowbar() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (target.currentHealth >= (target.maxHealth*(THRESH/100f)) && info.type == DamageInfo.DamageType.NORMAL){

            this.addToBot(new DamageAction(target, new DamageInfo(player, Math.round(info.base*(AMOUNT/100f)), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }


    }




    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+AMOUNT+DESCRIPTIONS[1]+THRESH+DESCRIPTIONS[2];
    }

}