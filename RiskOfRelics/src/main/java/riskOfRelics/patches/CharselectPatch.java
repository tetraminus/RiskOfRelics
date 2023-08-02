package riskOfRelics.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import riskOfRelics.RiskOfRelics;

import java.util.ArrayList;

import static riskOfRelics.RiskOfRelics.saveData;


public class CharselectPatch {

    public static Texture tex = new Texture("riskOfRelicsResources/images/ui/ambrySelect/Artifact1_off.png");
    public static Texture ontex = new Texture("riskOfRelicsResources/images/ui/ambrySelect/Artifact1_on.png");

    public static UIStrings buttonstrs = CardCrawlGame.languagePack.getUIString("riskOfRelics:ArtifactSelect");
    public static Hitbox buttonHB = new Hitbox(1000 * Settings.scale, 25* Settings.scale, tex.getWidth(), tex.getHeight());
    public static ArrayList<Artifact> artifacts = new ArrayList<>();
    public static boolean ShouldRender = false;
    public static boolean AnyEnabled = false;
    public static boolean CharSelected = false;
    public static boolean FirstOpen = true;

    public static boolean DimmerRendering = false;
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

    static {
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
    @SpirePatch2(
            clz = com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen.class,
            method = "open"
    )
    public static class OpenPatch {

        @SpirePrefixPatch
        public static void Prefix(com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen __instance) {
            AnyEnabled = !RiskOfRelics.ActiveArtifacts.isEmpty();
            FirstOpen = true;
            DimmerRendering = false;
        }
    }


    @SpirePatch2(clz = com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen.class,
            method = "render"
    )
    public static class Renderpatch {
        @SpirePostfixPatch

        public static void Postfix(com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen __instance, SpriteBatch sb) {

            sb.setColor(Color.WHITE);
            buttonHB.render(sb);

            if (ReflectionHacks.getPrivate(__instance, com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen.class, "anySelected")) {
                if (buttonHB.hovered) {
                    TipHelper.renderGenericTip(InputHelper.mX + 100, InputHelper.mY + 100, buttonstrs.TEXT[0], (RiskOfRelics.UnlockedArtifacts.size() == 0) ? buttonstrs.TEXT[2] : buttonstrs.TEXT[1]);
                    sb.draw(ontex, buttonHB.x, buttonHB.y);
                    if (RiskOfRelics.UnlockedArtifacts.size() == 0) {
                        sb.draw(ImageMaster.RELIC_LOCK, buttonHB.x, buttonHB.y, (float) ontex.getWidth(), (float) ontex.getHeight());
                    }
                } else {
                    sb.draw(tex, buttonHB.x, buttonHB.y);
                    if (RiskOfRelics.UnlockedArtifacts.size() == 0) {
                        sb.draw(ImageMaster.RELIC_LOCK, buttonHB.x, buttonHB.y, (float) tex.getWidth(), (float) tex.getHeight());
                    }
                }
            }


            if (CharSelected && (ShouldRender)){


                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        Artifact artifact = artifacts.get(i * 4 + j);
                        if (artifact != null) {
                            if (artifact.Hovered) {
                                TipHelper.renderGenericTip(InputHelper.mX+100, InputHelper.mY+100, artifact.name, artifact.description);
                            }
                            if (artifact.hb.hovered || RiskOfRelics.ActiveArtifacts.contains(artifact.artifact)) {
                                if (artifact.hb.hovered && RiskOfRelics.UnlockedArtifacts.contains(artifact.artifact)){
                                    if (RiskOfRelics.ActiveArtifacts.contains(artifact.artifact)) {
                                        sb.setColor(Color.RED);
                                    } else {
                                        sb.setColor(Color.GREEN);
                                    }
                                }

                                sb.draw(artifact.ontexture,
                                        artifact.CurrentX,
                                        artifact.CurrentY, 100, 100);
                                if (!RiskOfRelics.UnlockedArtifacts.contains(artifact.artifact)) {
                                    sb.draw(ImageMaster.RELIC_LOCK, artifact.CurrentX, artifact.CurrentY,  100,  100);
                                }
                                sb.setColor(Color.WHITE);

                            } else {
                                //draw a background


                                sb.draw(artifact.texture,
                                        artifact.CurrentX,
                                        artifact.CurrentY, 100, 100);
                                artifact.hb.render(sb);
                                if (!RiskOfRelics.UnlockedArtifacts.contains(artifact.artifact)) {
                                    sb.draw(ImageMaster.RELIC_LOCK, artifact.CurrentX, artifact.CurrentY, 100, 100);
                                }
                            }

                        }
                    }
                }
            }


        }
    }

    @SpirePatch2(clz = com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen.class,
            method = "update"
    )
    public static class Updatepatch {
        @SpirePostfixPatch

        public static void Postfix(com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen __instance) {
            if (FirstOpen && AnyEnabled) {
                FirstOpen = false;
                ShouldRender = true;
            }

            buttonHB.update();
            if (ReflectionHacks.getPrivate(__instance, com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen.class, "anySelected")) {
                if (buttonHB.hovered && InputHelper.justClickedLeft) {
                    ShouldRender = !ShouldRender;
                    CardCrawlGame.sound.play("UI_CLICK_1");
                }
            }
            CharSelected = ReflectionHacks.<Boolean>getPrivate(__instance, com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen.class, "anySelected");


            if (CharSelected && (ShouldRender)) {
                for (Artifact a :
                        artifacts) {
                    a.hb.update();


                    a.Hovered = a.hb.hovered;
                    if (a.hb.justHovered) {
                        CardCrawlGame.sound.play("UI_HOVER");
                    }
                    if (InputHelper.justClickedLeft && a.hb.hovered) {
                        if (RiskOfRelics.UnlockedArtifacts.contains(a.artifact)) {
                            CardCrawlGame.sound.play("UI_CLICK_1");

                            if (RiskOfRelics.ActiveArtifacts.contains(a.artifact)) {
                                RiskOfRelics.ActiveArtifacts.remove(a.artifact);
                            } else {
                                RiskOfRelics.ActiveArtifacts.add(a.artifact);
                            }
                            saveData();
                        } else {
                            CardCrawlGame.sound.play("RELIC_DROP_CLINK");
                        }
                    }
                }
            }
        }
    }


}
