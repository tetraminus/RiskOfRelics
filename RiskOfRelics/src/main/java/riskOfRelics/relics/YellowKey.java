package riskOfRelics.relics;

import com.megacrit.cardcrawl.dungeons.TheEnding;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.patches.AmbryPatches;

import static com.megacrit.cardcrawl.core.CardCrawlGame.dungeon;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class YellowKey extends BaseRelic {



    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("YellowKey");
    private static final String IMAGENAME = "YellowKey.png";

    public YellowKey() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public static boolean IsAvailable() {
        return !player.hasRelic(YellowKey.ID) && !(dungeon instanceof TheEnding) && !AmbryPatches.MapGenPatch.isEvil();
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}