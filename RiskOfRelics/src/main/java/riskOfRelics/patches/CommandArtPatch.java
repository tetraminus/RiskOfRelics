package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import riskOfRelics.RiskOfRelics;

import java.util.ArrayList;
public class CommandArtPatch {

    @SpirePatch2(
            clz = AbstractDungeon.class,
            method = "getRewardCards"
    )
    public static class CommandArtPatch2 {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"numCards"}

        )
        public static void Insert(@ByRef int[] numCards) {
            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.COMMAND)) {
                numCards[0] += 20 ;
            }
        }
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ModHelper.class, "isModEnabled");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }

    }

    @SpirePatch2(
            clz = AbstractDungeon.class,
            method = "getRewardCards"


    )
    public static class allowDupesPatch {
        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(String.class.getName()) && m.getMethodName().equals("equals")) {
                        //break after the found line
                        m.replace(
                                "{" +

                                            "if (riskOfRelics.RiskOfRelics.ActiveArtifacts.contains(riskOfRelics.RiskOfRelics.Artifacts.COMMAND)){" +
                                                "$_ = false;" +
                                            "}" +
                                            "else{" +
                                                "$_ = $proceed($$);" +
                                            "}" +
                                        "}"
                        );






                    }
                }
            };
        }


    }
}