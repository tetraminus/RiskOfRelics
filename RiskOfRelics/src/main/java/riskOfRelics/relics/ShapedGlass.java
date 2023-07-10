package riskOfRelics.relics;

import riskOfRelics.RiskOfRelics;


public class ShapedGlass extends BaseRelic {


    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("ShapedGlass");
    private static final String IMAGENAME = "ShapedGlass.png";

    public ShapedGlass() {
        super(ID, IMAGENAME, RelicTier.BOSS, LandingSound.MAGICAL);
    }






    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}