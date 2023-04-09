package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.DefaultMod;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class Crowbar extends BaseRelic {


    public static final int AMOUNT = 75;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Crowbar");
    private static final String IMAGENAME = "Crowbar.png";

    public Crowbar() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (target.currentHealth >= (target.maxHealth*0.9f) && info.type == DamageInfo.DamageType.NORMAL){

            this.addToBot(new DamageAction(target, new DamageInfo(player, Math.round(info.base*(AMOUNT/100f)), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }


    }




    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}