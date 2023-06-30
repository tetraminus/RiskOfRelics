package riskOfRelics.screens;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ArtifactSelectScreen extends CustomScreen
{

        private ArrayList<Artifact> artifacts = new ArrayList<>();
        private static class Artifact
        {
                public Texture texture;
                public String name;
                public String description;
                public Hitbox hb;

                boolean Hovered = false;

                public int CurrentX;
                public int CurrentY;

                public Artifact(Texture texture, String name, String description, int CurrentX, int CurrentY)
                {
                        this.texture = texture;
                        this.name = name;
                        this.description = description;
                        this.CurrentX = CurrentX;
                        this.CurrentY = CurrentY;
                        this.hb = new Hitbox(CurrentX, CurrentY, texture.getWidth(), texture.getHeight());
                }

        }
        public ArtifactSelectScreen(){
                for (int i = 0; i < 16; i++) {
                        artifacts.add(new Artifact(ImageMaster.loadImage("riskOfRelicsResources/images/ui/ambrySelect/Artifact"+(i+1)+"_off.png"),
                                "Artifact" + i, "Artifact" + i + " description",
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
                        if (a.hb.hovered){
                                a.Hovered = true;
                        }
                        else{
                                a.Hovered = false;
                        }


                }


        }

        @Override
        public void render(SpriteBatch spriteBatch) {
                // Draw a white square in the middle of the screen
                spriteBatch.setColor(Color.WHITE);

                // draw a grid of the textures
                for (int i = 0; i < 4; i++)
                {
                        for (int j = 0; j < 4; j++)
                        {
                                if (artifacts.get(i * 4 + j) != null){

                                        spriteBatch.draw(artifacts.get(i * 4 + j).texture,
                                                artifacts.get(i * 4 + j).CurrentX,
                                                artifacts.get(i * 4 + j).CurrentY,100,100);
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