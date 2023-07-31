package riskOfRelics.patches.interfaces;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import riskOfRelics.util.OnLoseArtifactRelic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class OnLoseArtifactPatch {

    @SpirePatch2(
            clz = ArtifactPower.class,
            method = "onSpecificTrigger"
    )
    public static class OnLoseArtifact {
        public static void Postfix(ArtifactPower __instance) {

            for (AbstractRelic relic : player.relics) {
                if (relic instanceof OnLoseArtifactRelic) {
                    ((OnLoseArtifactRelic) relic).onLoseArtifact();
                }

            }

        }
    }
}
