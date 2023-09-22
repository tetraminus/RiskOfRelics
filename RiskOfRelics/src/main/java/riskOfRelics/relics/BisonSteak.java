package riskOfRelics.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class BisonSteak extends BaseRelic {


    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("BisonSteak");
    private static final String IMAGENAME = "BisonSteak.png";

    public BisonSteak() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+AMOUNT+DESCRIPTIONS[1];
    }

    public void onRelicGet(AbstractRelic abstractRelic) {
        this.flash();// 23
        player.increaseMaxHp(AMOUNT,true);
    }
}