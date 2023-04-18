package riskOfRelics.relics;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.DefaultMod;
import riskOfRelics.actions.PolyluteAction;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class Polylute extends BaseRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Polylute");


    public static final float DAMAGESCALE = 20;
    private static final String IMAGENAME = "Polylute.png";
    private static final int CHANCE = 25;
    private static final int HITS = 3;

    public Polylute() {super(ID,IMAGENAME, RelicTier.UNCOMMON, LandingSound.FLAT);}



    // Lose 1 energy on unequip.

    @Override
    public void obtain() {
        if (player.hasRelic(Ukulele.ID)){
            flash();
            player.loseRelic(Ukulele.ID);
        }
        super.obtain();

    }



    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {

        if (AbstractDungeon.cardRandomRng.random(100) <= CHANCE && info.type == DamageInfo.DamageType.NORMAL) {


            AbstractDungeon.actionManager.addToBottom(new PolyluteAction(target, Math.round(damageAmount*(DAMAGESCALE/100f)), HITS));


        }

    }



    // Description
    @Override
    public String getUpdatedDescription() {
        return (DESCRIPTIONS[0] + CHANCE + DESCRIPTIONS[1] + (int)DAMAGESCALE + DESCRIPTIONS[2] + HITS + DESCRIPTIONS[3]);
    }

}
