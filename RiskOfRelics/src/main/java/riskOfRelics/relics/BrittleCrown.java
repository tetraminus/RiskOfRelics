package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import riskOfRelics.DefaultMod;
import riskOfRelics.actions.LoseGoldAction;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class BrittleCrown extends BaseRelic {



    public static final int AMOUNT = 5;

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("BrittleCrown");
    private static final String IMAGENAME = "BrittleCrown.png";

    public BrittleCrown() {
        super(ID, IMAGENAME, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        this.addToBot(new GainGoldAction(AMOUNT));
        super.onAttack(info, damageAmount, target);
    }


    @Override
    public void onLoseHp(int damageAmount) {
        this.addToBot(new LoseGoldAction((damageAmount/player.maxHealth)*100));
        super.onLoseHp(damageAmount);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+AMOUNT+DESCRIPTIONS[1];
    }

}