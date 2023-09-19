package riskOfRelics.patches.scrap;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;

public class PrinterReplacePatch {
    @SpirePatch2(
            clz = AbstractDungeon.class,
            method = "getRandomChest"

    )
    public static class PrinterReplace {
        @SpirePostfixPatch
        public static void method(@ByRef AbstractChest[] __result, int ___roll) {
            if (___roll < 100) {
                __result[0] = new riskOfRelics.chests.Printer();
            }
        }


    }

}
