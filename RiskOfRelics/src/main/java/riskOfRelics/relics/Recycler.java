package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.DefaultMod;


public class Recycler extends BaseRelic {


    public static final int AMOUNT = 0;
    public static String RerollText = null;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Recycler");
    private static final String IMAGENAME = "Recycler.png";

    public Recycler() {

        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        RerollText = DESCRIPTIONS[1];
    }



    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}