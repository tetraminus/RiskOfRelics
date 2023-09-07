package riskOfRelics.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import riskOfRelics.bosses.BulwarksAmbry;


    @SpirePatch2(
            clz = SuicideAction.class,
            method = "update"
    )
    public class Semicolon {
        public static SpireReturn<Object> Prefix(SuicideAction __instance) {
            if (ReflectionHacks.getPrivate(__instance, SuicideAction.class, "m") instanceof BulwarksAmbry) {
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

