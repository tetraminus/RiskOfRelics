package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.DefaultMod;


public class Eulogy extends BaseRelic {


    public static final int AMOUNT = 20;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Eulogy");
    private static final String IMAGENAME = "Eulogy.png";

    public Eulogy() {
        super(ID, IMAGENAME, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] +AMOUNT+ DESCRIPTIONS[1];
    }

}