package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.artifacts.EnigmaArt;

import java.util.ArrayList;

public class EnigmaPatches {
    private static int enigmaCounter = 0;
    @SpirePatch2(clz = AbstractDungeon.class, method = "nextRoomTransition", paramtypez = {SaveFile.class})
    public static class NextRoomTransitionPatch {
        @SpireInsertPatch(
                locator = Locator.class

        )
        public static void Insert(AbstractDungeon __instance, SaveFile saveFile) {

            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.ENIGMA) && enigmaCounter % EnigmaArt.FREQUENCY == 0) {
                RiskOfRelics.DoEnigmaShtuff();
            }
            enigmaCounter++;
        }

        private static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "iterator");

                return new int[]{LineFinder.findAllInOrder(ctBehavior, finalMatcher)[1]};

            }
        }
    }

}
