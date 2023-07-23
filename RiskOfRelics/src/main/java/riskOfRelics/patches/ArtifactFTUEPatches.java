package riskOfRelics.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import riskOfRelics.RiskOfRelics;

import java.util.Iterator;

public class ArtifactFTUEPatches {

    @SpirePatch2(clz = MainMenuScreen.class, method = "update")
    public static class MainMenuUpdatePatch {
        public static void Postfix(MainMenuScreen __instance) {
            if (FTUEHandler.open) {
                FTUEHandler.updateArtifactFTUE();

            }
        }
    }

    @SpirePatch2(clz = MainMenuScreen.class, method = "render")
    public static class MainMenuRenderPatch {
        public static void Postfix(MainMenuScreen __instance, SpriteBatch sb) {
            if (FTUEHandler.open) {
                FTUEHandler.renderArtifactFTUE(sb);
            }
        }
    }



    @SpirePatch2(clz = MainMenuScreen.class, method = "update")
    public static class MainMenuInteractPatch {

        @SpireInstrumentPatch
        public static ExprEditor instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(Iterator.class.getName()) && m.getMethodName().equals("hasNext")) {

                        m.replace(
                                "{" +
                                        "if (riskOfRelics.patches.ArtifactFTUEPatches.FTUEHandler.open){" +
                                            "$_ = false;" +
                                        "}else {" +
                                            "$_ = $proceed($$);" +
                                        "}" +
                                        "}"
                        );
                    }
                }
            };

        }
    }


    public static class FTUEHandler {

        public static boolean open = false;
        public static ConfirmButton confirmButton;

        public static void open() {
            open = true;
            confirmButton.show();
            confirmButton.isDisabled= false;
        }
        private static final Color tintColor = Color.BLACK.cpy().mul(1,1,1,0.5f);

        public static void renderArtifactFTUE(SpriteBatch sb) {
            sb.setColor(tintColor);// 90
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);// 89

            confirmButton.render(sb);
            sb.setColor(Color.WHITE);// 90
            sb.draw(ImageMaster.FTUE, Settings.WIDTH/2 - 311.0F, Settings.HEIGHT/2 - 142.0F, 311.0F, 142.0F, 622.0F, 284.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 622, 284, false, false);// 91
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, LABEL[0] , Settings.WIDTH/2 - 190.0F * Settings.scale, Settings.HEIGHT/2 + 80.0F * Settings.scale, Settings.GOLD_COLOR);// 113
            FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, TEXT[0], Settings.WIDTH/2 - 250.0F * Settings.scale, Settings.HEIGHT/2 + 30.0F * Settings.scale, 450.0F * Settings.scale, 26.0F * Settings.scale, Settings.CREAM_COLOR);// 121




        }
        public static void updateArtifactFTUE() {

            //confirmButton.show();


            confirmButton.update();
            if (confirmButton.hb.clicked) {
                onClose();
                open = false;
                confirmButton.hb.clicked = false;
                confirmButton.hb.hovered = false;
            }

        }

        private static void onClose() {
            confirmButton.hide();
        }

        static {
            confirmButton = new ConfirmButton(LABEL[1]);// 22
        }

    }



    private static final TutorialStrings tutorialStrings;
    public static final String[] LABEL;
    public static final String[] TEXT;
    static {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString(RiskOfRelics.makeID("ArtifactTutorial"));// 20
        TEXT = tutorialStrings.TEXT;// 21
        LABEL = tutorialStrings.LABEL;// 21

    }

}
