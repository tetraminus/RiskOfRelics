package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import riskOfRelics.RiskOfRelics;
public class ArtifactUnlockPatch {
    @SpirePatch2(clz = CorruptHeart.class, method = "die")
    @SpirePatch2(clz = TrueVictoryRoom.class, method = "onPlayerEntry")
    public static class OnWinPatch {
        @SpirePrefixPatch
        public static void onPlayerEntry(TrueVictoryRoom __instance) {
            for (RiskOfRelics.Artifacts a :
                    RiskOfRelics.ActiveArtifacts) {
                if (!RiskOfRelics.UnlockedArtifacts.contains(a)) {
                    RiskOfRelics.UnlockedArtifacts.add(a);
                }
            }
            RiskOfRelics.saveData();

        }
    }
}

// I say hi