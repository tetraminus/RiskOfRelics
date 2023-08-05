package riskOfRelics.relics;

import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.Interfaces.ChangeEQChargesRelic;


public class fuelCell extends BaseRelic implements ChangeEQChargesRelic {


    public static final int AMOUNT = 1;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("fuelCell");
    private static final String IMAGENAME = "fuelCell.png";

    public fuelCell() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }


    @Override
    public int changeCharges(int charges) {
        return charges + AMOUNT;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+AMOUNT+DESCRIPTIONS[1];
    }

}