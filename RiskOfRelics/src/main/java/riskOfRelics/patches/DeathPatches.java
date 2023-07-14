package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.artifacts.DeathArt;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class DeathPatches {

    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class DeathPatch {
        @SpireInsertPatch(
                locator = Locator.class

        )
        public static void Insert(AbstractPlayer __instance) {
            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.DEATH)) {
                player.decreaseMaxHealth(DeathArt.HEALTH_LOSS);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "onLoseHp");
                return new int[] {LineFinder.findInOrder(ctMethodToPatch, finalMatcher)[0] - 1};

            }
        }

    }

}
