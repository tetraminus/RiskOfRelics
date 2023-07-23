//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package riskOfRelics.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import riskOfRelics.RiskOfRelics;

public class ArtifactAboveCreatureAction extends AbstractGameEffect {
    private static final float TEXT_DURATION = 1.5F;
    private static final float STARTING_OFFSET_Y;
    private static final float TARGET_OFFSET_Y;
    private static final float LERP_RATE = 5.0F;
    private static final int W = 128;
    private float x;
    private float y;
    private float offsetY;
    private RiskOfRelics.Artifacts Artifact;
    private Color outlineColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
    private Texture img = null;

    public ArtifactAboveCreatureAction(float x, float y, RiskOfRelics.Artifacts artifact) {
        this.duration = 1.5F;// 26
        this.startingDuration = 1.5F;// 27
        this.Artifact = artifact;// 28
        this.x = x;// 29
        this.y = y;// 30
        this.color = Color.WHITE.cpy();// 31
        this.offsetY = STARTING_OFFSET_Y;// 32
        this.scale = Settings.scale;// 33
        img = RiskOfRelics.getArtifactImage(Artifact, true);
    }// 34
    public ArtifactAboveCreatureAction(AbstractCreature source, RiskOfRelics.Artifacts artifact) {
        this.duration = 1.5F;// 26
        this.startingDuration = 1.5F;// 27
        this.Artifact = artifact;// 28
        this.x = source.hb.cX - source.animX;// 29
        this.y = source.hb.cY + source.hb.height / 2.0F - source.animY;// 30
        this.color = Color.WHITE.cpy();// 31
        this.offsetY = STARTING_OFFSET_Y;// 32
        this.scale = Settings.scale;// 33
        img = RiskOfRelics.getArtifactImage(Artifact, true);
    }// 34

    public void update() {
        if (this.duration > 1.0F) {// 38
            this.color.a = Interpolation.exp5In.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);// 39
        }

        super.update();// 41
        if (AbstractDungeon.player.chosenClass == PlayerClass.DEFECT) {// 42
            this.offsetY = MathUtils.lerp(this.offsetY, TARGET_OFFSET_Y + 80.0F * Settings.scale, Gdx.graphics.getDeltaTime() * 5.0F);// 43 46
        } else {
            this.offsetY = MathUtils.lerp(this.offsetY, TARGET_OFFSET_Y, Gdx.graphics.getDeltaTime() * 5.0F);// 48
        }

        this.y += Gdx.graphics.getDeltaTime() * 12.0F * Settings.scale;// 50
    }// 51

    public void render(SpriteBatch sb) {
        this.outlineColor.a = this.color.a / 2.0F;// 55
        sb.setColor(this.color);// 75

        sb.draw(img, this.x - (float) img.getWidth() /2, this.y -(float) img.getHeight()/2 + this.offsetY, (float) img.getWidth()/2, (float) img.getHeight()/2, img.getWidth(), img.getHeight(), this.scale * (2.5F - this.duration), this.scale * (2.5F - this.duration), this.rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);// 76
        sb.setBlendFunction(770, 1);// 93

    }// 133

    public void dispose() {
    }// 138

    static {
        STARTING_OFFSET_Y = 0.0F * Settings.scale;// 16
        TARGET_OFFSET_Y = 60.0F * Settings.scale;// 17
    }
}
