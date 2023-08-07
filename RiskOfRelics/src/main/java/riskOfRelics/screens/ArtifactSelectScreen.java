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
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import riskOfRelics.RiskOfRelics;

import java.util.ArrayList;
import java.util.logging.Logger;

import static com.megacrit.cardcrawl.core.CardCrawlGame.cancelButton;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class ArtifactSelectScreen extends CustomScreen
{

        private ArrayList<Artifact> artifacts = new ArrayList<>();
        public static String header;
        private boolean CloseOnSelect;

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
                        this.hb = new Hitbox(CurrentX, CurrentY , 100*Settings.scale, 100*Settings.scale);
                }

        }
        public ArtifactSelectScreen() {
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
        private void open(boolean CloseOnSelect)
        {
                if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE) AbstractDungeon.previousScreen = AbstractDungeon.screen;
                // Call reopen in this example because the basics of
                // setting the current screen are the same across both
                this.CloseOnSelect = CloseOnSelect;
                reopen();
        }

        @Override
        public void reopen()
        {
                Logger.getLogger(ArtifactSelectScreen.class.getName()).info("Reopening screen");
                AbstractDungeon.screen = curScreen();
                AbstractDungeon.isScreenUp = true;
                if (!this.CloseOnSelect){
                        cancelButton.show("Cancel");
                }

                AbstractDungeon.dynamicBanner.appear((float)Settings.HEIGHT / 2.0F + 280.0F * Settings.scale ,header);
                AbstractDungeon.overlayMenu.showBlackScreen();
                //CardCrawlGame.fadeIn(0);
        }

        @Override
        public void close() {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.hideBlackScreen();

        }

        @Override
        public void update() {
                for (Artifact a :
                        artifacts) {
                        a.hb.update();



                        a.Hovered = a.hb.hovered;
                        if (a.hb.justHovered && !RiskOfRelics.UnlockedArtifacts.contains(a.artifact)){
                                CardCrawlGame.sound.play("UI_HOVER");
                        }

                        if (InputHelper.justClickedLeft && a.hb.hovered && !RiskOfRelics.UnlockedArtifacts.contains(a.artifact)){
                                RiskOfRelics.ActiveArtifacts.add(a.artifact);
                                if (this.CloseOnSelect){
                                        if (a.artifact == RiskOfRelics.Artifacts.GLASS){
                                                RiskOfRelics.ApplyGlassArtHealth();
                                        }
                                        CardCrawlGame.sound.play("UI_CLICK_1");
                                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.LONG, false);
                                        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
                                        AbstractDungeon.isScreenUp = false;
                                        this.close();

                                }


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
                                if (artifact != null && !RiskOfRelics.UnlockedArtifacts.contains(artifact.artifact)){
                                        if (artifact.Hovered) {
                                                TipHelper.renderGenericTip(InputHelper.mX+100, InputHelper.mY+100, artifact.name, artifact.description);
                                        }
                                        if (artifact.hb.hovered){

                                                spriteBatch.draw(artifact.ontexture,
                                                        artifact.CurrentX,
                                                        artifact.CurrentY,100*Settings.scale,100*Settings.scale);
                                        }else {
                                                //draw a background


                                                spriteBatch.draw(artifact.texture,
                                                        artifact.CurrentX,
                                                        artifact.CurrentY, 100*Settings.scale, 100*Settings.scale);
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

        static {
                header = languagePack.getUIString(RiskOfRelics.makeID("ArtifactSelect")).TEXT[3];
        }


}