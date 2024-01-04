package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.artifacts.EnigmaArt;
import riskOfRelics.artifacts.MetamorphosisArt;
import riskOfRelics.artifacts.SoulArt;

import java.util.ArrayList;

public class EnigmaAndMetaPatches {
    public static int enigmaCounter = 0;
    public static int metamorphCounter = 0;

    public static int vengCounter = 0; //USED IN VENG PATCHES; DO NOT DELETE. WHY? I DON'T KNOW. BUT IT WORKS.
    

    
    @SpirePatch2(clz = AbstractDungeon.class, method = "nextRoomTransition", paramtypez = {SaveFile.class})
    public static class NextRoomTransitionPatch {
        @SpireInsertPatch(
                locator = Locator.class

        )
        public static void Insert(AbstractDungeon __instance, SaveFile saveFile) {

            enigmaCounter++;
            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.ENIGMA) && enigmaCounter % EnigmaArt.FREQUENCY == 0) {
                RiskOfRelics.DoEnigmaShtuff();
            }

            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.METAMORPHOSIS) && metamorphCounter % MetamorphosisArt.FREQUENCY == 0
                && !CardCrawlGame.loadingSave) {
                if(RiskOfRelics.justLoadedMetamorphosis){
                    RiskOfRelics.justLoadedMetamorphosis = false;
                    RiskOfRelics.MetamorphCharacter = null;
                }
                RiskOfRelics.DoMetamorphosisShtuff();

            }
            metamorphCounter++;




            SoulArt.ClearLice();
            RiskOfRelics.logger.info(enigmaCounter);

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
