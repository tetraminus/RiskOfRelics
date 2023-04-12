//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package riskOfRelics.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class UrnSuckEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float tX;
    private float tY;

    public UrnSuckEffect(float sX, float sY, float tX, float tY) {
        this.x = sX;// 14
        this.y = sY;// 15
        this.tX = tX;// 16
        this.tY = tY;// 17
        this.scale = 0.12F;// 19
        this.duration = 0.5F;// 20
    }// 21

    public void update() {
        this.scale -= Gdx.graphics.getDeltaTime();// 24
        if (this.scale < 0.0F) {// 25
            AbstractDungeon.effectsQueue.add(new UrnSuckParticle(this.x + MathUtils.random(10.0F, -10.0F) * Settings.scale, this.y + MathUtils.random(10.0F, -10.0F) * Settings.scale, this.tX, this.tY, !AbstractDungeon.player.flipHorizontal));// 26 28 29
            this.scale = 0.04F;// 33
        }

        this.duration -= Gdx.graphics.getDeltaTime();// 36
        if (this.duration < 0.0F) {// 37
            this.isDone = true;// 38
        }

    }// 40

    public void render(SpriteBatch sb) {
    }// 44

    public void dispose() {
    }// 48
}
