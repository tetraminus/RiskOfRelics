package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import riskOfRelics.relics.Eulogy;


public class EulogyPatches {

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "returnRandomRelicKey"
    )
    public static class FromStartPatch {
        private static final Logger logger = LogManager.getLogger(EulogyPatches.class.getName());

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"retVal"}
        )
        public static void EulogyPatchMethod(AbstractRelic.RelicTier tier, @ByRef String[] retVal) {

            if (tier != AbstractRelic.RelicTier.BOSS
                    && AbstractDungeon.relicRng.random(99) < Eulogy.AMOUNT
                    && AbstractDungeon.player.hasRelic(Eulogy.ID)) {
                retVal[0] = AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS).relicId;
            }

        }
    }
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "returnEndRandomRelicKey"
    )
    public static class FromEndPatch {
        private static final Logger logger = LogManager.getLogger(EulogyPatches.class.getName());

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"retVal"}
        )
        public static void EulogyPatchMethod(AbstractRelic.RelicTier tier, @ByRef String[] retVal) {

            if (tier != AbstractRelic.RelicTier.BOSS
                    && AbstractDungeon.relicRng.random(99) < Eulogy.AMOUNT
                    && AbstractDungeon.player.hasRelic(Eulogy.ID)) {
                retVal[0] = AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS).relicId;
            }

        }
    }


    private static class Locator extends SpireInsertLocator { // Hey welcome to our SpireInsertLocator class!
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            
            Matcher finalMatcher = new Matcher.MethodCallMatcher(RelicLibrary.class, "getRelic");

            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

        }
    }
}