package riskOfRelics.relics;

import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class Pearl extends BaseRelic {


    public static final int AMOUNT = 30;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Pearl");
    private static final String IMAGENAME = "Pearl.png";

    public Pearl() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void obtain() {
        player.increaseMaxHp(Math.round(player.maxHealth * (((float) AMOUNT /100))), true);
        super.obtain();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT +DESCRIPTIONS[1];
    }

}