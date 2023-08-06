//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package riskOfRelics.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CoinPingEffect extends AbstractGameEffect {
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float x;
    private float y;
    private float vY;
    private float vX;
    private TextureAtlas.AtlasRegion img;
    private boolean activated = false;

    public CoinPingEffect(float sX, float sY, float tX, float tY) {
        this.img = ImageMaster.COPPER_COIN_1;// 23
        this.sX = sX + MathUtils.random(-90.0F, 90.0F) * Settings.scale;// 25
        this.sY = sY + MathUtils.random(-90.0F, 90.0F) * Settings.scale;// 26
        this.tX = tX + MathUtils.random(-50.0F, 50.0F) * Settings.scale;// 27
        this.tY = tY + MathUtils.random(-50.0F, 50.0F) * Settings.scale;// 28
        this.vX = this.sX;
        this.vY = this.sY;
        this.x = this.sX;// 31
        this.y = this.sY;// 32
        this.scale = 1F;// 34
        this.startingDuration = 0.8F;// 35
        this.duration = this.startingDuration;// 36
        this.renderBehind = MathUtils.randomBoolean(0.2F);// 37
        this.color = new Color(1.0F, 1f,1f, 1.0F);// 38
    }// 39

    public void update() {
        //move to target
        this.x = Interpolation.swingIn.apply(this.tX, this.sX, this.duration / this.startingDuration);// 43
        this.y = Interpolation.swingIn.apply(this.tY, this.sY, this.duration / this.startingDuration);// 44


        this.duration -= Gdx.graphics.getDeltaTime();// 55
        if (this.duration < this.startingDuration / 2.0F && !this.activated) {// 57
            this.activated = true;// 58
            this.sX = this.x;// 59
            this.sY = this.y;// 60
        }

        if (this.duration < 0.0F) {// 63
            CardCrawlGame.screenShake.shake(ShakeIntensity.LOW, ShakeDur.SHORT, false);// 64
            this.isDone = true;// 66
        }

    }// 68

    public void render(SpriteBatch sb) {
        //sb.setColor(Color.BLACK);// 72
        //sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 2.0F, this.scale * 2.0F, this.rotation);// 73
        sb.setColor(this.color);// 85
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);// 86
    }// 97

    public void dispose() {
    }// 102
}