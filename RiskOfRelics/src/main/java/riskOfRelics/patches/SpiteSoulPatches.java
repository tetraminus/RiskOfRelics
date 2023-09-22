package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import javassist.CtBehavior;
import riskOfRelics.actions.FixMonsterAction;
import riskOfRelics.artifacts.KinArt;
import riskOfRelics.artifacts.SoulArt;
import riskOfRelics.artifacts.SpiteArt;
import riskOfRelics.bosses.BulwarksAmbry;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom;

public class SpiteSoulPatches {
    @SpirePatch2(
            clz = AbstractMonster.class,
            method = "die",
            paramtypez = {boolean.class}
    )
    public static class SpiteSoulPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractMonster __instance, boolean triggerRelics) {
            if (triggerRelics) {

                SpiteArt.onMonsterDeath();
                SoulArt.onMonsterDeath(__instance);
                KinArt.onMonsterDeath();
            }
            getCurrRoom().monsters.monsters.forEach( m -> {
                    if (m instanceof BulwarksAmbry) {
                ((BulwarksAmbry) m).onMonsterDeath(__instance);
            }
            });


        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "currentHealth");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }

        }


    }


    @SpirePatch2(
            clz = MonsterGroup.class,
            method = "areMonstersBasicallyDead"
    )
    @SpirePatch2(
            clz = MonsterGroup.class,
            method = "areMonstersDead"
    )
    public static class TriggerFinalKillPatch {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(MonsterGroup __instance) {
            AbstractDungeon.actionManager.actions.forEach(a -> {
                if (a instanceof SpawnMonsterAction || a instanceof FixMonsterAction) {
                   SpireReturn.Return(false);
                }
            });
            return SpireReturn.Continue();
        }
    }
}
