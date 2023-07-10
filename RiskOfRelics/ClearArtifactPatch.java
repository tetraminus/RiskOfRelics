package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import riskOfRelics.RiskOfRelics;

@SpirePatch2(clz = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = {boolean.class}
)
public class CharselectPatch{
    @SpirePostfixPatch

    public static void Postfix(com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen __instance, boolean playBgm) {
        RiskOfRelics.ActiveArtifacts.clear();
    }
}\