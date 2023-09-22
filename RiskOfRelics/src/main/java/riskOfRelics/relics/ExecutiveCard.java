package riskOfRelics.relics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.patches.equipment.EquipmentFieldPatch;
import riskOfRelics.relics.equipment.AbstractEquipment;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class ExecutiveCard extends AbstractEquipment {


    public static final int AMOUNT = 20;

    public static final int CARDSALLOWED = 2;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("ExecutiveCard");
    private static final String IMAGENAME = "ExecutiveCard.png";

    public ArrayList<RewardItem> usedRewards = new ArrayList<>();

    public ExecutiveCard() {
        super(ID, IMAGENAME, RelicTier.RARE, LandingSound.MAGICAL);
        grayscale = false;
    }

    @Override
    public int GetBaseCounter() {
        return -1;
    }

   @Override
   public boolean shouldGrayscale() {
       return false;
   }

    @Override
    public boolean canClick() {
        return false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1] + CARDSALLOWED + DESCRIPTIONS[2];
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        usedRewards.clear();
        super.onEnterRoom(room);
    }
    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        if (EquipmentFieldPatch.PlayerEquipment.get(player) == this && this.hb.hovered){
            renderTip(sb);
        }
        super.renderInTopPanel(sb);
    }
}