package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.DefaultMod;
import riskOfRelics.actions.UkuleleAction;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class Ukulele extends BaseRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Ukulele");


    public float DAMAGESCALE = 0.5f;
    public static int CHANCE = 25;
    private boolean chaining = false;
    private static final String IMAGENAME = "Ukulele.png";

    public Ukulele() {super(ID,IMAGENAME, RelicTier.UNCOMMON, LandingSound.FLAT);}



    // Lose 1 energy on unequip.

    @Override
    public void obtain() {
        if (player.hasRelic(Polylute.ID)){
            player.getRelic(Polylute.ID).flash();
            player.loseRelic(this.relicId);
        }else {
            super.obtain();
        }

    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (target != player && AbstractDungeon.cardRandomRng.random(100) <= CHANCE && info.type == DamageInfo.DamageType.NORMAL) {

            AbstractDungeon.actionManager.addToBottom(new UkuleleAction(info.owner, target, Math.round(damageAmount*DAMAGESCALE), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.LIGHTNING));


        }

    }



    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+CHANCE+DESCRIPTIONS[1];
    }

}
