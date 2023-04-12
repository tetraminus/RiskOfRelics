package riskOfRelics.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;

import java.util.ArrayList;

public class UrnSuckParticle extends AbstractGameEffect {
    private TextureAtlas.AtlasRegion img;
    private CatmullRomSpline<Vector2> crs = new CatmullRomSpline();
    private ArrayList<Vector2> controlPoints = new ArrayList();
    private static final int TRAIL_ACCURACY = 60;
    private Vector2[] points = new Vector2[60];
    private Vector2 pos;
    private Vector2 target;
    private float currentSpeed = 0.0F;
    private static final float MAX_VELOCITY;
    private static final float VELOCITY_RAMP_RATE;
    private static final float DST_THRESHOLD;
    private float rotation;
    private boolean rotateClockwise = true;
    private boolean stopRotating = false;
    private boolean facingLeft;
    private float rotationRate;

    public UrnSuckParticle(float sX, float sY, float tX, float tY, boolean facingLeft) {
        this.img = ImageMaster.GLOW_SPARK_2;// 43
        this.pos = new Vector2(sX, sY);// 44
        if (!facingLeft) {// 46
            this.target = new Vector2(tX + DST_THRESHOLD, tY);// 47
        } else {
            this.target = new Vector2(tX - DST_THRESHOLD, tY);// 49
        }

        this.facingLeft = facingLeft;// 51
        this.crs.controlPoints = new Vector2[1];// 52
        this.rotateClockwise = MathUtils.randomBoolean();// 53
        this.rotation = (float)MathUtils.random(0, 359);// 54
        this.controlPoints.clear();// 55
        this.rotationRate = MathUtils.random(600.0F, 650.0F) * Settings.scale;// 56
        this.currentSpeed = 1000.0F * Settings.scale;// 57
        this.color = Color.GREEN.cpy();// 58
        this.duration = 0.7F;// 59
        this.scale = 0.5F * Settings.scale;// 60
        this.renderBehind = MathUtils.randomBoolean();// 61
    }// 62

    public void update() {
        this.updateMovement();// 65
    }// 66

    private void updateMovement() {
        Vector2 tmp = new Vector2(this.pos.x - this.target.x, this.pos.y - this.target.y);// 70
        tmp.nor();// 71
        float targetAngle = tmp.angle();// 72
        this.rotationRate += Gdx.graphics.getDeltaTime() * 2000.0F;// 73
        this.scale += Gdx.graphics.getDeltaTime() * 1.0F * Settings.scale;// 74
        if (!this.stopRotating) {// 77
            if (this.rotateClockwise) {// 78
                this.rotation += Gdx.graphics.getDeltaTime() * this.rotationRate;// 79
            } else {
                this.rotation -= Gdx.graphics.getDeltaTime() * this.rotationRate;// 81
                if (this.rotation < 0.0F) {// 82
                    this.rotation += 360.0F;// 83
                }
            }

            this.rotation %= 360.0F;// 87
            if (!this.stopRotating && Math.abs(this.rotation - targetAngle) < Gdx.graphics.getDeltaTime() * this.rotationRate) {// 89 90
                this.rotation = targetAngle;// 91
                this.stopRotating = true;// 92
            }
        }

        tmp.setAngle(this.rotation);// 98
        tmp.x *= Gdx.graphics.getDeltaTime() * this.currentSpeed;// 101
        tmp.y *= Gdx.graphics.getDeltaTime() * this.currentSpeed;// 102
        this.pos.sub(tmp);// 103
        if (this.stopRotating) {// 105
            this.currentSpeed += Gdx.graphics.getDeltaTime() * VELOCITY_RAMP_RATE * 3.0F;// 106
        } else {
            this.currentSpeed += Gdx.graphics.getDeltaTime() * VELOCITY_RAMP_RATE * 1.5F;// 108
        }

        if (this.currentSpeed > MAX_VELOCITY) {// 110
            this.currentSpeed = MAX_VELOCITY;// 111
        }

        if (this.target.dst(this.pos) < DST_THRESHOLD) {// 115
            for(int i = 0; i < 5; ++i) {// 116
                if (this.facingLeft) {// 117
                    AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(this.target.x + DST_THRESHOLD, this.target.y));// 118
                } else {
                    AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(this.target.x - DST_THRESHOLD, this.target.y));// 120
                }
            }


            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);// 124
            this.isDone = true;// 125
        }

        if (!this.controlPoints.isEmpty()) {// 128
            if (!((Vector2)this.controlPoints.get(0)).equals(this.pos)) {// 129
                this.controlPoints.add(this.pos.cpy());// 130
            }
        } else {
            this.controlPoints.add(this.pos.cpy());// 133
        }

        if (this.controlPoints.size() > 3) {// 136
            Vector2[] vec2Array = new Vector2[0];// 137
            this.crs.set((Vector2[]) this.controlPoints.toArray(vec2Array), false);// 138

            for(int i = 0; i < 60; ++i) {// 139
                this.points[i] = new Vector2();// 140
                this.crs.valueAt(this.points[i], (float)i / 59.0F);// 141
            }
        }

        if (this.controlPoints.size() > 10) {// 145
            this.controlPoints.remove(0);// 146
        }

        this.duration -= Gdx.graphics.getDeltaTime();// 150
        if (this.duration < 0.0F) {// 151
            this.isDone = true;// 152
        }

    }// 154

    public void render(SpriteBatch sb) {
        if (!this.isDone) {// 157
            sb.setColor(Color.BLACK);// 159
            float scaleCpy = this.scale;// 160

            int i;
            for(i = this.points.length - 1; i > 0; --i) {// 161
                if (this.points[i] != null) {// 162
                    sb.draw(this.img, this.points[i].x - (float)(this.img.packedWidth / 2), this.points[i].y - (float)(this.img.packedHeight / 2), (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, scaleCpy * 1.5F, scaleCpy * 1.5F, this.rotation);// 163
                    scaleCpy *= 0.98F;// 174
                }
            }

            sb.setBlendFunction(770, 1);// 178
            sb.setColor(Color.GREEN);// 179
            scaleCpy = this.scale;// 180

            for(i = this.points.length - 1; i > 0; --i) {// 181
                if (this.points[i] != null) {// 182
                    sb.draw(this.img, this.points[i].x - (float)(this.img.packedWidth / 2), this.points[i].y - (float)(this.img.packedHeight / 2), (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, scaleCpy, scaleCpy, this.rotation);// 183
                    scaleCpy *= 0.98F;// 194
                }
            }

            sb.setBlendFunction(770, 771);// 197
        }

    }// 199

    public void dispose() {
    }// 203

    static {
        MAX_VELOCITY = 4000.0F * Settings.scale;// 31
        VELOCITY_RAMP_RATE = 3000.0F * Settings.scale;// 32
        DST_THRESHOLD = 42.0F * Settings.scale;// 33
    }
}
