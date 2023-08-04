package riskOfRelics.relics.equipment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import riskOfRelics.patches.equipment.EquipmentFieldPatch;
import riskOfRelics.relics.BaseRelic;
import riskOfRelics.relics.Interfaces.ChangeEQChargesRelic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public abstract class AbstractEquipment extends BaseRelic implements ClickableRelic {

    public AbstractEquipment(String id, String texture, RelicTier tier,  LandingSound sfx) {
        super(id, texture, tier, sfx);
        counter = 1;
    }
    private boolean isPlayerTurn = false;
    private boolean usedthisbattle = false;


    @Override
    public void obtain() {
        this.hb.hovered = false;// 299
        Reposition(false);
        EquipmentFieldPatch.PlayerEquipment.set(player, this);// 303
        player.relics.remove(this);// 304
        UnlockTracker.markRelicAsSeen(this.relicId);// 305

    }
    @Override
    public void atTurnStart() {
        //usedThisTurn = false;  // Resets the used this turn. You can remove this to use a relic only once per combat rather than per turn.
        isPlayerTurn = true; // It's our turn!
//        beginLongPulse(); // Pulse while the player can click on it.
    }

    @Override
    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip) {
        this.playLandingSFX();// 269
        this.isDone = true;// 270
        this.isObtained = true;// 271
        Reposition(true);
        EquipmentFieldPatch.PlayerEquipment.set(player, this);// 303
        this.hb.move(this.currentX, this.currentY);// 279
        if (callOnEquip ){
            this.flash();// 276
            this.onEquip();// 280
        }
        this.relicTip();// 281
        UnlockTracker.markRelicAsSeen(this.relicId);// 282
    }

    @Override
    public void instantObtain() {
        this.playLandingSFX();// 269
        this.isDone = true;// 270
        this.isObtained = true;// 271
        Reposition(true);
        this.flash();// 276
        EquipmentFieldPatch.PlayerEquipment.set(player, this);// 303
        this.hb.move(this.currentX, this.currentY);// 279
        this.onEquip();// 280
        this.relicTip();// 281
        UnlockTracker.markRelicAsSeen(this.relicId);// 282
    }

    @Override
    public void onPlayerEndTurn() {
        isPlayerTurn = false; // Not our turn now.
        stopPulse();
    }


    @Override
    public void atBattleStart() {

        Reposition(true);
        counter = 1;
        for (AbstractRelic r : player.relics) {
            if (r instanceof ChangeEQChargesRelic) {
                counter = ((ChangeEQChargesRelic) r).changeCharges(counter);
            }

        }

        usedthisbattle = false;
        super.atBattleStart();
    }

    @Override
    public void onRightClick() {
        counter--;

    }

    public void Reposition(boolean instant) {
        this.targetX = 180 * Settings.scale;// 301
        this.targetY = (90 ) * Settings.scale;// 302
        if (instant) {
            this.hb.move(this.targetX, this.targetY);// 305
            currentX = this.targetX;// 306
            currentY = this.targetY;// 307
            this.isDone = true;// 303
            return;
        }

        this.isDone = false;// 303



    }

    public boolean canClick() {
        return isPlayerTurn && counter > 0;
    }

    public boolean isAvailable() {
        return AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    @Override
    public void update() {
        if (canClick()){
            this.clickUpdate();
        }

        super.update();
    }

    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        if (EquipmentFieldPatch.PlayerEquipment.get(player) == this && this.hb.hovered){
            this.renderTip(sb);
        }
        super.renderInTopPanel(sb);
    }
}
