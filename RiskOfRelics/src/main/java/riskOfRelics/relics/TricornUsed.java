package riskOfRelics.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.equipment.AbstractEquipment;


public class TricornUsed extends AbstractEquipment {


    public static final int AMOUNT = 0;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("TricornUsed");
    private static final String IMAGENAME = "TricornUsed.png";

    public TricornUsed() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);

        lockedCharges = true;
    }

    @Override
    public int GetBaseCounter() {
        return -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean canClick() {

        return CardCrawlGame.isInARun() && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    @Override
    public void onRightClick() {
        AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 1.0f, DESCRIPTIONS[1], true));// 42

        super.onRightClick();
    }
}