package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.Exordium;
import javassist.CtBehavior;
import riskOfRelics.RiskOfRelics;

import java.util.ArrayList;

public class EndlessUnlockPatch {

    @SpirePatch2(
            clz = Exordium.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {AbstractPlayer.class, ArrayList.class}
    )
    public static class ExordiumPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void WeDoALittleJank(Exordium __instance, AbstractPlayer p, ArrayList<String> emptyList) {
            if (Settings.isEndless) {
                for (RiskOfRelics.Artifacts a:
                        RiskOfRelics.ActiveArtifacts) {
                    if (!RiskOfRelics.UnlockedArtifacts.contains(a)) {
                        RiskOfRelics.UnlockedArtifacts.add(a);
                    }
                }
                RiskOfRelics.saveData();
            }
        }
        public static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(Settings.class, "isEndless");
                return new int[]{LineFinder.findAllInOrder(ctBehavior, finalMatcher)[0] + 1};
            }
        }

    }
}
