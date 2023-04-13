package riskOfRelics.relics;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import riskOfRelics.DefaultMod;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class Pearl extends BaseRelic {


    public static final int AMOUNT = 10;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Pearl");
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