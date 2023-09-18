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




}
