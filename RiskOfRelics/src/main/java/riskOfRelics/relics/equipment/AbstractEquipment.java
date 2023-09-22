package riskOfRelics.relics.equipment;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import riskOfRelics.patches.equipment.EquipmentFieldPatch;
import riskOfRelics.relics.BaseRelic;
import riskOfRelics.relics.Interfaces.ChangeEQChargesRelic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public abstract class AbstractEquipment extends BaseRelic implements ClickableRelic {

    private int BASE_COUNTER = 1;

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

    public int GetBaseCounter() {
        return BASE_COUNTER;
    }


    @Override
    public void obtain() {
        BaseMod.publishRelicGet(this);// 295
        this.hb.hovered = false;// 299
        Reposition(getHomePosX(), getHomePosY(),false);
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
            BaseMod.publishRelicGet(this);// 295
            this.flash();// 276
            this.onEquip();// 280
        }
        this.relicTip();// 281
        UnlockTracker.markRelicAsSeen(this.relicId);// 282
    }

    @Override
    public void instantObtain() {
        BaseMod.publishRelicGet(this);// 295
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
        counter = GetBaseCounter();
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

        super.update();
        if (canClick()){
            this.clickUpdate();
        }
    }


    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        if (EquipmentFieldPatch.PlayerEquipment.get(player) == this && this.hb.hovered){
            renderTip(sb);
        }

        this.grayscale = shouldGrayscale();
        if (!Settings.hideRelics) {// 699
            this.renderOutline(sb, true);// 703
            if (this.grayscale) {// 704
                ShaderHelper.setShader(sb, ShaderHelper.Shader.GRAYSCALE);// 705
            }

            sb.setColor(Color.WHITE);// 707
            sb.draw(this.img, this.currentX - 64.0F , this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, 0, 0, 0, 128, 128, false, false);// 708
            if (this.grayscale) {// 725
                ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);// 726
            }

            this.renderCounter(sb, true);// 728
            this.renderFlash(sb, true);// 729
            this.hb.render(sb);// 730
        }
    }
    @Override
    public void renderCounter(SpriteBatch sb, boolean inTopPanel) {
        if (this.counter > -1) {// 1119
            if (inTopPanel) {// 1120
                FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(this.counter),  this.currentX + 30.0F * Settings.scale, this.currentY - 7.0F * Settings.scale, Color.WHITE);// 1121 1124
            } else {
                FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(this.counter), this.currentX + 30.0F * Settings.scale, this.currentY - 7.0F * Settings.scale, Color.WHITE);// 1129 1132
            }
        }

    }// 1138

    @Override
    public void renderOutline(SpriteBatch sb, boolean inTopPanel) {
        float tmpX = this.currentX - 64.0F;// 1206
        sb.setColor(PASSIVE_OUTLINE_COLOR);// 1235
        sb.draw(this.outlineImg, tmpX, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, 0, 0, 0, 128, 128, false, false);// 1236

    }

    public boolean shouldGrayscale() {
        return !canClick();
    }

    @Override
    public void renderBossTip(SpriteBatch sb) {
        super.renderBossTip(sb);
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
