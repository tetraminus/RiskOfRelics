package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;
import riskOfRelics.artifacts.SpiteArt;

@SpirePatch2(
        clz = AbstractMonster.class,
        method = "die",
        paramtypez = {boolean.class}
)
public class SpitePatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(AbstractMonster __instance, boolean triggerRelics) {
        if (triggerRelics) {
            SpiteArt.onMonsterDeath();
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "currentHealth");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }

    }



}
