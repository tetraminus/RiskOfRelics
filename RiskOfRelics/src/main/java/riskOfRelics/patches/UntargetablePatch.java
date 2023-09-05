package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
}
