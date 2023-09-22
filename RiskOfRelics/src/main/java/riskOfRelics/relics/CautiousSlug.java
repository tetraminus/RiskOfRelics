package riskOfRelics.relics;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class CautiousSlug extends BaseRelic {


    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("CautiousSlug");
    private static final String IMAGENAME = "CautiousSlug.png";

    public CautiousSlug() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (!(room instanceof MonsterRoom)) {
            player.heal(AMOUNT);
        }
        super.onEnterRoom(room);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}