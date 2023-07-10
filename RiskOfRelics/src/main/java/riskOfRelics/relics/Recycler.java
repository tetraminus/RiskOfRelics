package riskOfRelics.relics;

import riskOfRelics.RiskOfRelics;


public class Recycler extends BaseRelic {


    public static final int AMOUNT = 0;
    public static String RerollText = null;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Recycler");
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