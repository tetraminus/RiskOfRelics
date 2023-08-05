package riskOfRelics.relics.equipment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import riskOfRelics.patches.equipment.EquipmentFieldPatch;
import riskOfRelics.relics.BaseRelic;
import riskOfRelics.relics.Interfaces.ChangeEQChargesRelic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public abstract class AbstractEquipment extends BaseRelic implements ClickableRelic {

    private static int BASE_COUNTER = 1;

    public AbstractEquipment(String id, String texture, RelicTier tier, LandingSound sfx) {
        super(id, texture, tier, sfx);
        counter = -1;
    }
    private boolean isPlayerTurn = false;
    private boolean usedthisbattle = false;

    private static final float BattlePosX;
    private static final float BattlePosY;
    private static final float HomePosX;
    private static final float HomePosY;

    @Override
    public void obtain() {
        this.hb.hovered = false;// 299
        Reposition(getHomePosX(),getHomePosY(),false);
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
        Reposition(getHomePosX(),getHomePosY(),true);
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
        Reposition(getHomePosX(),getHomePosY(),true);
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

        Reposition(BattlePosX,BattlePosY, false);


        counter = BASE_COUNTER;
        for (AbstractRelic r : player.relics) {
            if (r instanceof ChangeEQChargesRelic) {
                counter = ((ChangeEQChargesRelic) r).changeCharges(counter);
            }

        }

        usedthisbattle = false;
        super.atBattleStart();
    }

    private static float getHomePosX() {
        return 64.0F * Settings.scale;// 85
    }

    private static float getHomePosY() {
        float PosY;

        if (player != null){
            PosY = player.blights.size () > 0 ? (float)Settings.HEIGHT - (176.0F + 74) * Settings.scale : (float)Settings.HEIGHT - 176.0F * Settings.scale;
        } else {
            PosY = (float)Settings.HEIGHT - 176.0F * Settings.scale;
        }
        return PosY;
    }


    @Override
    public void onVictory() {
        Reposition(getHomePosX(),getHomePosY(), false);
        counter = -1;
        super.onVictory();
    }


    @Override
    public void onRightClick() {
        counter--;

    }

    public void Reposition(float x, float y,  boolean instant) {
        this.targetX = x;// 301
        this.targetY = y;// 302
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



    static {
        BattlePosX = 190 * Settings.scale;
        BattlePosY = (80 ) * Settings.scale;
        HomePosX = getHomePosX();

        HomePosY = getHomePosY();



    }

    public void GoHome() {
        Reposition(getHomePosX(),getHomePosY(), false);
    }
}
