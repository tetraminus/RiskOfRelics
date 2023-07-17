package riskOfRelics.patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ModHelper;
import javassist.CtBehavior;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.artifacts.ChaosArt;

public class ChaosPatches {


    @SpirePatch2(clz = AbstractPlayer.class, method = "applyStartOfCombatPreDrawLogic")
    public static class ChaosPatch {
        @SpirePrefixPatch
        public static void ThePearlyGatesAreClosedToMe(AbstractPlayer __instance) {
            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.CHAOS)) {
                __instance.drawPile.group.forEach(c -> {
                    if (!CardModifierManager.hasModifier(c, RiskOfRelics.makeID("ChaosCardmod"))) {
                        ChaosArt.ApplyMod(c);
                    }
                });


            }
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class makeStatEquivalentCopyPatch {
        @SpireInsertPatch(locator = Locator.class, localvars = {"card"})
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.CHAOS) && !CardModifierManager.hasModifier(card, RiskOfRelics.makeID("ChaosCardmod"))) {
                ChaosArt.ApplyMod(card);
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "name");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

    }

}
