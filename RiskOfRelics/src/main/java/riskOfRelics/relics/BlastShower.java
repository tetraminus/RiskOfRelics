package riskOfRelics.relics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.Interfaces.ChangeEQChargesRelic;
import riskOfRelics.relics.equipment.AbstractEquipment;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class BlastShower extends AbstractEquipment {


    public static final int AMOUNT = 3;
    public int rechargeCount = 0;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("BlastShower");
    private static final String IMAGENAME = "BlastShower.png";
    private static final float offsetY = 28 * Settings.scale;


    public BlastShower() {
        super(ID, IMAGENAME, RelicTier.RARE, LandingSound.MAGICAL);
        this.rechargeAuto = false;
        this.counter = 0;
        this.rechargeCount = 0;
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

                FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(this.rechargeCount+1), this.currentX + 30.0F * Settings.scale, this.currentY - 7.0F * Settings.scale + offsetY, Color.WHITE);// 1121 1124
            } else {
                FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(this.rechargeCount+1), this.currentX + 30.0F * Settings.scale, this.currentY - 7.0F * Settings.scale + offsetY, Color.WHITE);// 1129 1132
            }
        }
        super.renderCounter(sb, inTopPanel);
    }


    @Override
    public void Recharge() {
        int maxCharge = BASE_COUNTER;
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
        if (rechargeCount >= AMOUNT) {
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

        this.addToBot(new VFXAction(new SmokeBombEffect(player.hb.cX, player.hb.cY)));
        this.addToBot(new RemoveDebuffsAction(AbstractDungeon.player));

        super.onRightClick();
    }
}