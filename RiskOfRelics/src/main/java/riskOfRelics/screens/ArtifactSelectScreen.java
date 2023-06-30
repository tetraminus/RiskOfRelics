package riskOfRelics.screens;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.RiskOfRelics;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ArtifactSelectScreen extends CustomScreen
{

        private ArrayList<Artifact> artifacts = new ArrayList<>();
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
        public ArtifactSelectScreen(){
                for (int i = 0; i < 16; i++) {
                        artifacts.add(new Artifact(ImageMaster.loadImage("riskOfRelicsResources/images/ui/ambrySelect/Artifact"+(i+1)+"_off.png"),
                                ImageMaster.loadImage("riskOfRelicsResources/images/ui/ambrySelect/Artifact"+(i+1)+"_on.png"),
                                RiskOfRelics.getArtifact(i),RiskOfRelics.getArtifactName(RiskOfRelics.getArtifact(i)), "Artifact" + i + " description",
                                (int) Settings.WIDTH / 2 - 200 + 100 * (i % 4),
                                (int) Settings.HEIGHT / 2 - 200 + 100 * (i / 4)));
                        if (artifacts.get(i).texture == null){
                                artifacts.get(i).texture = ImageMaster.loadImage("riskOfRelicsResources/images/ui/ambrySelect/Badge.png");
                        }


                }


        }
        public static class Enum
        {
                @SpireEnum
                public static AbstractDungeon.CurrentScreen AMBRY_SCREEN;
        }

        @Override
        public AbstractDungeon.CurrentScreen curScreen()
        {
                return Enum.AMBRY_SCREEN;
        }

        // Note that this can be private and take any parameters you want.
        // When you call openCustomScreen it finds the first method named "open"
        // and calls it with whatever arguments were passed to it.
        private void open()
        {
                if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
                        AbstractDungeon.previousScreen = AbstractDungeon.screen;
                // Call reopen in this example because the basics of
                // setting the current screen are the same across both
                reopen();
        }

        @Override
        public void reopen()
        {
                Logger.getLogger(ArtifactSelectScreen.class.getName()).info("Reopening screen");
                AbstractDungeon.screen = curScreen();
                AbstractDungeon.isScreenUp = true;
                //CardCrawlGame.fadeIn(0);
        }

        @Override
        public void close() {

        }

        @Override
        public void update() {
                for (Artifact a :
                        artifacts) {
                        a.hb.update();



                        a.Hovered = a.hb.hovered;
                        if (a.hb.justHovered){
                                CardCrawlGame.sound.play("UI_HOVER");
                        }

                        if (InputHelper.justClickedLeft && a.hb.hovered ){
                                //RiskOfRelics.addArtifact(a.artifact);

                                AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
                                AbstractDungeon.isScreenUp = false;
                                AbstractDungeon.overlayMenu.proceedButton.show();
                                AbstractDungeon.overlayMenu.endTurnButton.hide();
                                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        }


                }


        }

        @Override
        public void render(SpriteBatch spriteBatch) {

                spriteBatch.setColor(Color.WHITE);




                // draw a grid of the textures
                for (int i = 0; i < 4; i++)
                {
                        for (int j = 0; j < 4; j++)
                        {
                                Artifact artifact = artifacts.get(i * 4 + j);
                                if (artifact != null){
                                        if (artifact.hb.hovered){
                                                TipHelper.renderGenericTip(200.0f * Settings.scale, 200.0f * Settings.scale, artifact.name, artifact.description);
                                                spriteBatch.draw(artifact.ontexture,
                                                        artifact.CurrentX,
                                                        artifact.CurrentY,100,100);
                                        }else {
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

        @Override
        public void openingSettings()
        {
                // Required if you want to reopen your screen when the settings screen closes
                AbstractDungeon.previousScreen = curScreen();
        }


}