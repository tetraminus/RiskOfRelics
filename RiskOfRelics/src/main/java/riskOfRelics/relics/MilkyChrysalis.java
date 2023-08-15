package riskOfRelics.relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.Interfaces.ChangeEQChargesRelic;
import riskOfRelics.relics.equipment.AbstractEquipment;
import riskOfRelics.relics.equipment.HasChargeTimerEQ;

import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class MilkyChrysalis extends AbstractEquipment implements HasChargeTimerEQ {

    public int rechargeCount = -1;

    public static final int AMOUNT = 2;

    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("MilkyChrysalis");
    private static final String IMAGENAME = "MilkyChrysalis.png";
    private static final float offsetY = 28 * Settings.scale;
    private boolean targetMode;
    private AbstractMonster hoveredMonster;
    private float x;
    private float y;
    private static final int SEGMENTS = 20;
    private Vector2[] points = new Vector2[20];
    private Vector2 controlPoint;
    private float arrowScale;
    private float arrowScaleTimer = 0.0F;
    private static final float ARROW_TARGET_SCALE = 1.2F;
    private static final int TARGET_ARROW_W = 256;

    private static boolean justClicked= false;


    public MilkyChrysalis() {
        super(ID, IMAGENAME, RelicTier.RARE, LandingSound.MAGICAL);
        this.rechargeAuto = false;
        this.counter = GetBaseCounter();
        this.rechargeCount = -1;
        for(int i = 0; i < this.points.length; ++i) {// 68
            this.points[i] = new Vector2();// 69
        }
    }

    @Override
    public int GetBaseCounter() {
        return 1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {

        super.onEquip();
    }

    @Override
    public void renderCounter(SpriteBatch sb, boolean inTopPanel) {
        if (this.rechargeCount > -1) {// 1119
            if (inTopPanel) {// 1120

                FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(this.rechargeCount), this.currentX + 30.0F * Settings.scale, this.currentY - 7.0F * Settings.scale + offsetY, Color.WHITE);// 1121 1124
            } else {
                FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(this.rechargeCount), this.currentX + 30.0F * Settings.scale, this.currentY - 7.0F * Settings.scale + offsetY, Color.WHITE);// 1129 1132
            }
        }
        super.renderCounter(sb, inTopPanel);
    }


    @Override
    public void Recharge() {
        int maxCharge = GetBaseCounter();
        if (!lockedCharges) {
            for (AbstractRelic r : player.relics) {
                if (r instanceof ChangeEQChargesRelic) {
                    maxCharge = ((ChangeEQChargesRelic) r).changeCharges(maxCharge);
                }

            }
        }

        if (counter < maxCharge) {
            if (rechargeCount <= 0) {
                rechargeCount = 0;
            }
            rechargeCount++;
        } else {
            rechargeCount = -1;
        }
        if (rechargeCount > AMOUNT) {
            rechargeCount = 0;
            counter++;
            if (counter == maxCharge) {
                rechargeCount = -1;
            }

            if (counter > maxCharge) {
                counter = maxCharge;
            }
        }



    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        Recharge();
        super.onEnterRoom(room);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
    }

    @Override
    public void onRightClick() {
        this.targetMode = true;// 290
        GameCursor.hidden = true;// 291
        justClicked = true;




    }

    @Override
    public void update() {
        if (targetMode) {
            updateTargetMode();
        }
        super.update();
    }

    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        if (this.targetMode) {// 448
            if (this.hoveredMonster != null) {// 449
                this.hoveredMonster.renderReticle(sb);// 450
            }

            this.renderTargetingUi(sb);// 452
        }
        super.renderInTopPanel(sb);
    }
    private void renderTargetingUi(SpriteBatch sb) {
        float x = (float)InputHelper.mX;// 457
        float y = (float)InputHelper.mY;
        this.controlPoint = new Vector2(this.currentX - (x - this.currentX) / 4.0F, y + (y - this.currentY - 40.0F * Settings.scale) / 2.0F);// 458
        if (this.hoveredMonster == null) {// 462
            this.arrowScale = Settings.scale;// 463
            this.arrowScaleTimer = 0.0F;// 464
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));// 465
        } else {
            this.arrowScaleTimer += Gdx.graphics.getDeltaTime();// 467
            if (this.arrowScaleTimer > 1.0F) {// 468
                this.arrowScaleTimer = 1.0F;// 469
            }

            this.arrowScale = Interpolation.elasticOut.apply(Settings.scale, Settings.scale * 1.2F, this.arrowScaleTimer);// 472
            sb.setColor(new Color(1.0F, 0.2F, 0.3F, 1.0F));// 477
        }

        Vector2 tmp = new Vector2(this.controlPoint.x - x, this.controlPoint.y - y);// 481
        tmp.nor();// 482
        this.drawCurvedLine(sb, new Vector2(this.currentX, this.currentY - 40.0F * Settings.scale), new Vector2(x, y), this.controlPoint);// 484
        sb.draw(ImageMaster.TARGET_UI_ARROW, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.arrowScale, this.arrowScale, tmp.angle() + 90.0F, 0, 0, 256, 256, false, false);// 489 499
    }// 506
    private void drawCurvedLine(SpriteBatch sb, Vector2 start, Vector2 end, Vector2 control) {
        float radius = 7.0F * Settings.scale;// 509

        for(int i = 0; i < this.points.length - 1; ++i) {// 511
            this.points[i] = (Vector2) Bezier.quadratic(this.points[i], (float)i / 20.0F, start, control, end, new Vector2());// 512
            radius += 0.4F * Settings.scale;// 513
            Vector2 tmp;
            float angle;
            if (i != 0) {// 518
                tmp = new Vector2(this.points[i - 1].x - this.points[i].x, this.points[i - 1].y - this.points[i].y);// 519
                angle = tmp.nor().angle() + 90.0F;// 520
            } else {
                tmp = new Vector2(this.controlPoint.x - this.points[i].x, this.controlPoint.y - this.points[i].y);// 522
                angle = tmp.nor().angle() + 270.0F;// 523
            }

            sb.draw(ImageMaster.TARGET_UI_CIRCLE, this.points[i].x - 64.0F, this.points[i].y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, radius / 18.0F, radius / 18.0F, angle, 0, 0, 128, 128, false, false);// 526
        }

    }// 544

    private void updateTargetMode() {
        if (!justClicked &&( InputHelper.justClickedRight || AbstractDungeon.isScreenUp ||
                player.isDraggingCard ||
                CInputActionSet.cancel.isJustPressed())) {// 222 224
            CInputActionSet.cancel.unpress();// 225
            this.targetMode = false;// 226
            GameCursor.hidden = false;// 227
        }

        if (justClicked && !InputHelper.isMouseDown_R) {
            justClicked = false;

        }

        this.hoveredMonster = null;// 230
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();// 231

        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if (m.hb.hovered && !m.isDying) {// 232
                this.hoveredMonster = m;// 233
                break;// 234
            }
        }

        if (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) {// 238
            InputHelper.justClickedLeft = false;// 239
            CInputActionSet.select.unpress();// 240
            if (this.hoveredMonster != null) {// 242
                if (AbstractDungeon.player.hasPower("Surrounded")) {// 243
                    AbstractDungeon.player.flipHorizontal = this.hoveredMonster.drawX < AbstractDungeon.player.drawX;// 244
                }


                DoAction();

                this.targetMode = false;// 257
                GameCursor.hidden = false;// 258
            }
        }

    }// 261

    private void DoAction() {
        this.addToBot(new StunMonsterAction(hoveredMonster,player));// 248
        super.onRightClick();
    }

    @Override
    public int getCharges() {
        return rechargeCount;
    }

    @Override
    public void setCharges(int charges) {
        rechargeCount = charges;

    }
}