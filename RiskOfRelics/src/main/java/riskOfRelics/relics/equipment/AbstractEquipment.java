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

    protected static int BASE_COUNTER = 1;

    public AbstractEquipment(String id, String texture, RelicTier tier, LandingSound sfx) {
        super(id, texture, tier, sfx);
        counter = -1;
    }
    protected boolean isPlayerTurn = false;
    protected boolean usedthisbattle = false;
    protected boolean lockedCharges = false;
    protected boolean rechargeAuto = true;

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

        Reposition(BattlePosX, BattlePosY, false);

        if (this.rechargeAuto) {
            Recharge();
        }

        super.atBattleStart();
    }

    public void Recharge() {
        counter = BASE_COUNTER;
        if (!lockedCharges){
            for (AbstractRelic r : player.relics) {
                if (r instanceof ChangeEQChargesRelic) {
                    counter = ((ChangeEQChargesRelic) r).changeCharges(counter);
                }

            }
        }


        usedthisbattle = false;
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        super.onEnterRoom(room);
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
        return isPlayerTurn && counter > 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
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
            renderTip(sb);
        }
        super.renderInTopPanel(sb);
    }

    @Override
    public void renderBossTip(SpriteBatch sb) {
        super.renderBossTip(sb);
    }

//    @Override
//    public void renderTip(SpriteBatch sb) {
//
//
//        if ((float) InputHelper.mX < 1400.0F * Settings.scale) {// 1346
//            if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.RELIC_VIEW) {// 1347
//                TipHelper.queuePowerTips(180.0F * Settings.scale, (float)Settings.HEIGHT * 0.7F, this.tips);// 1348
//            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP && this.tips.size() > 2 && !AbstractDungeon.player.hasRelic(this.relicId)) {// 1349 1350
//                TipHelper.queuePowerTips((float)InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY + 180.0F * Settings.scale, this.tips);// 1351
//            } else if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(this.relicId) && this.currentY > BattlePosY+10) {// 1355
//                TipHelper.queuePowerTips((float)InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY - 30.0F * Settings.scale, this.tips);// 1356
//            } else if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(this.relicId) && this.currentY < BattlePosY+10) {// 1355
//                TipHelper.queuePowerTips((float)Settings.WIDTH * 0.63F, (float)Settings.HEIGHT * 0.63F, this.tips);// 1342
//            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {// 1360
//                TipHelper.queuePowerTips(360.0F * Settings.scale, (float)InputHelper.mY + 50.0F * Settings.scale, this.tips);// 1361
//            } else {
//                TipHelper.queuePowerTips((float)InputHelper.mX + 50.0F * Settings.scale, (float)InputHelper.mY + 50.0F * Settings.scale, this.tips);// 1363
//            }
//        } else {
//            TipHelper.queuePowerTips((float)InputHelper.mX - 350.0F * Settings.scale, (float)InputHelper.mY + 2000.0F * Settings.scale, this.tips);// 1369
//        }
//    }

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
