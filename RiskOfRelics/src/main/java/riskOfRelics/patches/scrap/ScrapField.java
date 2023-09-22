package riskOfRelics.patches.scrap;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import riskOfRelics.util.ScrapInfo;

public class ScrapField {
    @SpirePatch2(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
    public static class scrapFieldPatch {
        public static SpireField<ScrapInfo> scrapInfo = new SpireField<>(ScrapInfo::new);
    }

}
