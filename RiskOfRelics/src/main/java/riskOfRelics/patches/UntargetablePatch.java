package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import javassist.CtBehavior;

import static riskOfRelics.RiskOfRelics.makeID;

public class UntargetablePatch {

    @SpirePatch2(
            clz = AbstractCard.class,
            method = "cardPlayable"
    )
    public static class UntargetablePatchMethod {
        public static SpireReturn<Boolean> Prefix(AbstractMonster m) {
            if (m != null && m.hasPower(makeID("UntargetablePower"))) {
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(
            clz = PotionPopUp.class,
            method = "updateTargetMode"

    )
    public static class UntargetablePotionPatchMethod {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"m"}
        )
        public static SpireReturn<Void> patch(AbstractMonster m) {
            if (m != null && m.hasPower(makeID("UntargetablePower"))) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "isDying");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }

    }
//    @SpirePatch2(
//            clz = DamageRandomEnemyAction.class,
//            method = "update"
//    )
//
//    public static class DamageRandomEnemyActionPatch {
//        @SpireInsertPatch(
//                locator = Locator.class
//        )
//        public static SpireReturn<Void> patch(DamageRandomEnemyAction __instance) {
//            if (__instance.target != null && __instance.target.hasPower(makeID("UntargetablePower"))) {
//                __instance.isDone = true;
//
//                return SpireReturn.Return(null);
//            }
//            return SpireReturn.Continue();
//        }
//        private static class Locator extends SpireInsertLocator {
//
//            @Override
//            public int[] Locate(CtBehavior ctBehavior) throws Exception {
//                Matcher matcher = new Matcher.FieldAccessMatcher(DamageRandomEnemyAction.class, "target");
//                return new int[] {LineFinder.findAllInOrder(ctBehavior, matcher)[0] + 1} ;
//            }
//        }
//
//    }
//    @SpirePatch2(
//            clz = AttackDamageRandomEnemyAction.class,
//            method = "update"
//    )
//
//    public static class AttackDamageRandomEnemyActionPatch {
//        @SpireInsertPatch(
//                locator = Locator.class
//        )
//        public static SpireReturn<Void> patch(AttackDamageRandomEnemyAction __instance) {
//            if (__instance.target != null && __instance.target.hasPower(makeID("UntargetablePower"))) {
//                __instance.isDone = true;
//
//                return SpireReturn.Return(null);
//            }
//            return SpireReturn.Continue();
//        }
//        private static class Locator extends SpireInsertLocator {
//
//            @Override
//            public int[] Locate(CtBehavior ctBehavior) throws Exception {
//                Matcher matcher = new Matcher.FieldAccessMatcher(AttackDamageRandomEnemyAction.class, "target");
//                return new int[] {LineFinder.findAllInOrder(ctBehavior, matcher)[0] + 1} ;
//            }
//        }
//
//    }




}
