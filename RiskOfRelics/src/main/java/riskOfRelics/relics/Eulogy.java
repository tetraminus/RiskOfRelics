package riskOfRelics.relics;

import riskOfRelics.RiskOfRelics;


public class Eulogy extends BaseRelic {


    public static final int AMOUNT = 20;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Eulogy");
    private static final String IMAGENAME = "Eulogy.png";

    public Eulogy() {
        super(ID, IMAGENAME, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] +AMOUNT+ DESCRIPTIONS[1];
    }

}