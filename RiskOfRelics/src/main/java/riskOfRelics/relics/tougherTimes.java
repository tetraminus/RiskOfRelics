package riskOfRelics.relics;

import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class tougherTimes extends BaseRelic {


    public static final int AMOUNT = 10;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("tougherTimes");
    private static final String IMAGENAME = "tougherTimes.png";

    public tougherTimes() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void obtain() {
        if (player.hasRelic(saferSpaces.ID)){
            player.getRelic(saferSpaces.ID).flash();
            player.loseRelic(this.relicId);
         }else {
            super.obtain();
        }

    }



    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+AMOUNT+DESCRIPTIONS[1];
    }

}