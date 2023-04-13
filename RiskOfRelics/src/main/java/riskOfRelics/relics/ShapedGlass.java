package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.DefaultMod;


public class ShapedGlass extends BaseRelic {


    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("ShapedGlass");
    private static final String IMAGENAME = "ShapedGlass.png";

    public ShapedGlass() {
        super(ID, IMAGENAME, RelicTier.BOSS, LandingSound.MAGICAL);
    }






    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}