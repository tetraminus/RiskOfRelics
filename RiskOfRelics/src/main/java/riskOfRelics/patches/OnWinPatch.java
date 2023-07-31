package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import riskOfRelics.RiskOfRelics;

@SpirePatch2(clz = TrueVictoryRoom.class, method = "onPlayerEntry")
public class OnWinPatch {
    @SpirePrefixPatch
    public static void onPlayerEntry(TrueVictoryRoom __instance) {
        for (RiskOfRelics.Artifacts a:
                RiskOfRelics.ActiveArtifacts) {
            if (!RiskOfRelics.UnlockedArtifacts.contains(a)) {
                RiskOfRelics.UnlockedArtifacts.add(a);
            }
        }
        RiskOfRelics.saveData();

    }

}

// I say hi