package riskOfRelics.relics;

import riskOfRelics.RiskOfRelics;


public class YellowKey extends BaseRelic {



    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("YellowKey");
    private static final String IMAGENAME = "YellowKey.png";

    public YellowKey() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }



    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}