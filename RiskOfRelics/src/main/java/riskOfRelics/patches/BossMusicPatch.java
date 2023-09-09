package riskOfRelics.patches;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;

import static riskOfRelics.RiskOfRelics.makeID;

@SpirePatch2(
        clz = TempMusic.class,
        method = "getSong"
)
public class BossMusicPatch {

    @SpirePostfixPatch
    public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
        if(key.equals(makeID("AMBRY_BOSS"))){

            return SpireReturn.Return(MainMusic.newMusic("riskOfRelicsResources/music/AmbryTheme.ogg"));
        }
        return SpireReturn.Continue();
    }

}
