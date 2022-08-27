package riskOfRelics.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.DefaultMod;
import riskOfRelics.actions.UkuleleAction;
import riskOfRelics.util.TextureLoader;

import static riskOfRelics.DefaultMod.*;


public class Ukulele extends BaseRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Ukulele");


    public float DAMAGESCALE = 0.5f;
    private boolean chaining = false;
    private static final String IMAGENAME = "Ukulele.png";

    public Ukulele() {super(ID,IMAGENAME, RelicTier.UNCOMMON, LandingSound.FLAT);}



    // Lose 1 energy on unequip.



    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (AbstractDungeon.cardRandomRng.random(100) <= 25 && info.type == DamageInfo.DamageType.NORMAL) {

            AbstractDungeon.actionManager.addToBottom(new UkuleleAction(info.owner, target, Math.round(damageAmount*DAMAGESCALE), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.LIGHTNING));


        }

    }



    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
