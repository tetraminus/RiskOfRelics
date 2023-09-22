package riskOfRelics.patches.equipment;

import basemod.devcommands.relic.RelicRemove;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import java.util.ArrayList;

public class RelicCommandPatch {
    @SpirePatch2(clz = RelicRemove.class, method = "extraOptions")
    public static class RelicRemovePatch {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"result"}
        )
        public static void Insert(RelicRemove __instance, ArrayList<String> result) {
            if (EquipmentFieldPatch.PlayerEquipment.get(AbstractDungeon.player) != null) {
                result.add(EquipmentFieldPatch.PlayerEquipment.get(AbstractDungeon.player).relicId);
            }
        }

        private static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "iterator");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }


    }

}
