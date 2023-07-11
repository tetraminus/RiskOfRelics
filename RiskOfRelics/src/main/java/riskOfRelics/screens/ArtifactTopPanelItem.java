package riskOfRelics.screens;

import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import riskOfRelics.RiskOfRelics;

import java.util.ArrayList;

public class ArtifactTopPanelItem extends TopPanelItem {
    public static final Texture IMG = new Texture("riskOfRelicsResources/images/ui/ambrySelect/Artifact1_off.png");
    public static final String ID = "riskOfRelics:ArtifactTopPanelItem";
    private static final String[] uiStrings = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    private ArrayList<Artifact> artifacts = new ArrayList<>();
    boolean ShouldRender;
    private static class Artifact
    {
        public Texture texture;
        public Texture ontexture;
        public String name;
        public String description;
        public Hitbox hb;

        boolean Hovered = false;

        public int CurrentX;
        public int CurrentY;
        public RiskOfRelics.Artifacts artifact;

        public Artifact(Texture texture,Texture ontexture, RiskOfRelics.Artifacts artifact, String name, String description, int CurrentX, int CurrentY)
        {
            this.artifact = artifact;

            this.texture = texture;
            this.ontexture = ontexture;
            this.name = name;
            this.description = description;
            this.CurrentX = CurrentX;
            this.CurrentY = CurrentY;
            this.hb = new Hitbox(CurrentX+ (float) texture.getWidth() /3.33f, CurrentY + (float) texture.getHeight() /3.33f , texture.getWidth(), texture.getHeight());
        }

    }
    public ArtifactTopPanelItem() {
        super(IMG, ID);
        for (int i = 0; i < 16; i++) {
            artifacts.add(new Artifact(ImageMaster.loadImage("riskOfRelicsResources/images/ui/ambrySelect/Artifact"+(i+1)+"_off.png"),
                    ImageMaster.loadImage("riskOfRelicsResources/images/ui/ambrySelect/Artifact"+(i+1)+"_on.png"),
                    RiskOfRelics.getArtifact(i),RiskOfRelics.getArtifactName(RiskOfRelics.getArtifact(i)), RiskOfRelics.getArtifactDescription(RiskOfRelics.getArtifact(i)),
                    (int) Settings.WIDTH / 2 - 200 + 100 * (i % 4),
                    (int) Settings.HEIGHT / 2 - 200 + 100 * (i / 4)));
            if (artifacts.get(i).texture == null){
                artifacts.get(i).texture = ImageMaster.loadImage("riskOfRelicsResources/images/ui/ambrySelect/Badge.png");
            }


        }

    }

    @Override
    protected void onClick() {
        ShouldRender = !ShouldRender;

    }

    @Override
    public void update() {
        this.setClickable(RiskOfRelics.ActiveArtifacts.size() > 0);
        if (ShouldRender) {
            for (Artifact a :
                    artifacts) {
                a.hb.update();


                a.Hovered = a.hb.hovered;
                if (a.hb.justHovered) {
                    CardCrawlGame.sound.play("UI_HOVER");
                }



            }
        }

        super.update();
    }

    @Override
    protected void onHover() {
        if (RiskOfRelics.ActiveArtifacts.size() > 0) {
            TipHelper.renderGenericTip(this.x, this.y-100,  uiStrings[0], uiStrings[1] );
            super.onHover();

        } else {
            TipHelper.renderGenericTip(this.x, this.y-100,  uiStrings[0], uiStrings[2] );
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

        if (ShouldRender){
            spriteBatch.setColor(Color.WHITE);

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    Artifact artifact = artifacts.get(i * 4 + j);
                    if (artifact != null) {
                        if (artifact.Hovered) {
                            TipHelper.renderGenericTip(InputHelper.mX+100, InputHelper.mY+100, artifact.name, artifact.description);
                        }
                        if (artifact.hb.hovered || RiskOfRelics.ActiveArtifacts.contains(artifact.artifact)) {

                            spriteBatch.draw(artifact.ontexture,
                                    artifact.CurrentX,
                                    artifact.CurrentY, 100, 100);
                        } else {
                            //draw a background


                            spriteBatch.draw(artifact.texture,
                                    artifact.CurrentX,
                                    artifact.CurrentY, 100, 100);
                            artifact.hb.render(spriteBatch);
                        }

                    }
                }
            }
        }
        super.render(spriteBatch);
    }



}
