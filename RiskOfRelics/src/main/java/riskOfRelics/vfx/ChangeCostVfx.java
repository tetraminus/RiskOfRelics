//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package riskOfRelics.vfx;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactCurvyEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class ChangeCostVfx extends AbstractGameEffect {
    private AbstractCard card;
    private Color color2;
    private static final float PADDING;
    private float scaleY;
    private static final float ORB_OFFSET_X;
    private static final float ORB_OFFSET_Y ;
    private boolean changedCost = false;


    public ChangeCostVfx(AbstractCard card) {
        this(card, (float) Settings.WIDTH - 96.0F * Settings.scale, (float) Settings.HEIGHT - 32.0F * Settings.scale);// 30
    }// 31

    public ChangeCostVfx(AbstractCard card, float x, float y) {
        this.card = card;// 34
        this.startingDuration = 3.0F;// 35
        this.duration = this.startingDuration;// 36
        this.identifySpawnLocation(x, y);// 37
        card.drawScale = 0.01F;// 38
        card.targetDrawScale = 1.0F;// 39

        this.initializeVfx();// 42
        this.card.cost = card.cost + 1;
        this.card.costForTurn = card.costForTurn + 1;
        changedCost = false;
    }// 43

    private void initializeVfx() {
        this.color = Color.RED.cpy();// 74
        this.color2 = Color.GOLDENROD.cpy();// 74
        this.scale = Settings.scale;// 85
        this.scaleY = Settings.scale;// 86
    }// 87

    private void identifySpawnLocation(float x, float y) {
        int effectCount = 0;// 90
        Iterator var4 = AbstractDungeon.effectList.iterator();// 91

        AbstractGameEffect e;
        while (var4.hasNext()) {
            e = (AbstractGameEffect) var4.next();
            if (e instanceof ChangeCostVfx) {// 92
                ++effectCount;// 93
            }
        }

        var4 = AbstractDungeon.topLevelEffects.iterator();// 96

        while (var4.hasNext()) {
            e = (AbstractGameEffect) var4.next();
            if (e instanceof ChangeCostVfx) {// 97
                ++effectCount;// 98
            }
        }

        this.card.current_x = x;// 102
        this.card.current_y = y;// 103
        this.card.target_y = (float) Settings.HEIGHT * 0.5F;// 104
        switch (effectCount) {// 106
            case 0:
                this.card.target_x = (float) Settings.WIDTH * 0.5F;// 108
                break;// 109
            case 1:
                this.card.target_x = (float) Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH;// 111
                break;// 112
            case 2:
                this.card.target_x = (float) Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH;// 114
                break;// 115
            case 3:
                this.card.target_x = (float) Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;// 117
                break;// 118
            case 4:
                this.card.target_x = (float) Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;// 120
                break;// 121
            default:
                this.card.target_x = MathUtils.random((float) Settings.WIDTH * 0.1F, (float) Settings.WIDTH * 0.9F);// 123
                this.card.target_y = MathUtils.random((float) Settings.HEIGHT * 0.2F, (float) Settings.HEIGHT * 0.8F);//  124
        }

    }// 127

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();// 130
        if (this.duration > 1.5F && this.duration < 1.7F) {// 131
            if (!Settings.DISABLE_EFFECTS) {// 134
                int i;
                for (i = 0; i < 6; ++i) {// 135
                    AbstractDungeon.topLevelEffectsQueue.add(new DamageImpactCurvyEffect(this.card.current_x + ORB_OFFSET_X, this.card.current_y + ORB_OFFSET_Y, this.color, false));// 136
                }
                AbstractDungeon.topLevelEffectsQueue.add(new DamageImpactCurvyEffect(this.card.current_x + ORB_OFFSET_X, this.card.current_y + ORB_OFFSET_Y, this.color2, false));// 136
            }
            this.updateVfx();// 145
        }
        if (this.duration < 1.6F && !changedCost) {// 131
            this.card.cost = card.cost - 1;
            this.card.costForTurn = card.costForTurn - 1;
            CardCrawlGame.sound.playA("ATTACK_MAGIC_SLOW_2", 1.0F);
            changedCost = true;
        }
        // fade card out
//        if (this.duration < 1.0F) {// 147
//            this.card.targetDrawScale = Interpolation.fade.apply(1.0F, 0.01F, (startingDuration - this.duration)/startingDuration);// 149
//            card.fadingOut = true;
//            card.transparency = Interpolation.fade.apply(1.0F, 0.0F, (startingDuration - this.duration)/startingDuration);
//
//        }

        this.card.update();// 148
        if (this.duration < 0.0F) {// 150
            this.isDone = true;// 151
            this.card.shrink();
            FakeSoulObtain(this.card);
        }

    }// 153

    private void updateVfx() {

        //this.rarityColor.a = this.color.a;// 157
        //this.scale = Interpolation.swingOut.apply(1.6F, 1.0F, this.duration * 2.0F) * Settings.scale;// 158
        //this.scaleY = Interpolation.fade.apply(0.005F, 1.0F, this.duration * 2.0F) * Settings.scale;// 159
    }// 160

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);// 164
        this.card.render(sb);// 165
        this.renderVfx(sb);// 166
    }// 167

    private void renderVfx(SpriteBatch sb) {
//        sb.setColor(this.color);// 170
//        TextureAtlas.AtlasRegion img = ImageMaster.CARD_POWER_BG_SILHOUETTE;// 171
//        sb.draw(img, this.card.current_x + img.offsetX - (float) img.originalWidth / 2.0F, this.card.current_y + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, this.scale * MathUtils.random(0.95F, 1.05F), this.scaleY * MathUtils.random(0.95F, 1.05F), this.rotation);// 172 180 181
//        sb.setBlendFunction(770, 1);// 184
//        sb.setColor(this.color);// 185
//        img = ImageMaster.CARD_SUPER_SHADOW;// 186
//        sb.draw(img, this.card.current_x + img.offsetX - (float) img.originalWidth / 2.0F, this.card.current_y + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, this.scale * 0.75F * MathUtils.random(0.95F, 1.05F), this.scaleY * 0.75F * MathUtils.random(0.95F, 1.05F), this.rotation);// 187 195 196
//        sb.draw(img, this.card.current_x + img.offsetX - (float) img.originalWidth / 2.0F, this.card.current_y + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, this.scale * 0.5F * MathUtils.random(0.95F, 1.05F), this.scaleY * 0.5F * MathUtils.random(0.95F, 1.05F), this.rotation);// 199 207 208
//        sb.setBlendFunction(770, 771);// 210
    }// 211

    public void dispose() {
    }// 216


    private static final float MASTER_DECK_X;

    private static final float MASTER_DECK_Y;

    static {
        PADDING = 30.0F * Settings.scale;// 25
        ORB_OFFSET_X = -150 * Settings.scale;
        ORB_OFFSET_Y = 200 * Settings.scale;
        MASTER_DECK_X = (float)Settings.WIDTH - 96.0F * Settings.scale;// 48
        MASTER_DECK_Y = (float)Settings.HEIGHT - 32.0F * Settings.scale;// 49

    }
    public static void FakeSoulObtain(AbstractCard card) {
        CardCrawlGame.sound.play("CARD_OBTAIN");// 95
        boolean needMoreSouls = true;// 96
        
        ArrayList<Soul> souls =  ReflectionHacks.<ArrayList<Soul>>getPrivate(AbstractDungeon.getCurrRoom().souls, SoulGroup.class, "souls");// 97

        for (Soul s : souls) {// 106
            if (s.isReadyForReuse) {// 107
                s.card = card;// 177


                ReflectionHacks.setPrivate(s, Soul.class, "pos", new Vector2(card.current_x, card.current_y));// 179
                ReflectionHacks.setPrivate(s, Soul.class, "target", new Vector2(MASTER_DECK_X, MASTER_DECK_Y));// 180
                ReflectionHacks.privateMethod(Soul.class, "setSharedVariables").invoke(s);// 178
                ReflectionHacks.setPrivate(s, Soul.class, "group", AbstractDungeon.player.masterDeck);// 180

                needMoreSouls = false;// 111
                souls.add(s);
                break;// 112
            }
        }

        if (needMoreSouls) {// 107
            Soul s = new Soul();// 109

            ReflectionHacks.setPrivate(s, Soul.class, "pos", new Vector2(card.current_x, card.current_y));// 179
            ReflectionHacks.setPrivate(s, Soul.class, "target", new Vector2(MASTER_DECK_X, MASTER_DECK_Y));// 180
            ReflectionHacks.privateMethod(Soul.class, "setSharedVariables").invoke(s);// 178
            ReflectionHacks.setPrivate(s, Soul.class, "group", AbstractDungeon.player.masterDeck);// 180
            souls.add(s);
        }


    }
    
}
