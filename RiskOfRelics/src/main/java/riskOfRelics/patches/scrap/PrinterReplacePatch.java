package riskOfRelics.patches.scrap;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.rewards.chests.SmallChest;
import javassist.CtBehavior;
import riskOfRelics.chests.Printer;

public class PrinterReplacePatch {
    @SpirePatch2(
            clz = AbstractDungeon.class,
            method = "getRandomChest"

    )
    public static class PrinterReplace {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"roll"}
        )
        public static SpireReturn<AbstractChest> method(int roll) {
            if (roll < 100 && Printer.canSpawn()) {
                return SpireReturn.Return(new Printer());
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator{
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.NewExprMatcher(SmallChest.class);
                return new int[]{LineFinder.findInOrder(ctBehavior, finalMatcher)[0]-2};
            }
        }


    }

}
