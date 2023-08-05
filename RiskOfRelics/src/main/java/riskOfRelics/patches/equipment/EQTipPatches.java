package riskOfRelics.patches.equipment;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;

public class EQTipPatches {
    @SpirePatch2(
            clz = TipHelper.class,
            method = "getPowerTipHeight"
    )
    public static class EQTipPatch {
        public static float Postfix(float __result, PowerTip powerTip) {
            __result += 7f * Settings.scale;
            __result -= getHeaderHeight(powerTip.header);

            return __result;
        }
    }

    @SpirePatch2(
            clz = TipHelper.class,
            method = "renderTipBox"
    )
    public static class EQTipPatch2 {
        @SpireInsertPatch(
                rloc = 2,
                localvars = {"h"}
        )
        public static void Insert(float x, float y, SpriteBatch sb, String title, String description,@ByRef float[] h) {
            h[0] += getHeaderHeight( title);
        }



    }

    private static float getHeaderHeight(String header) {
        return FontHelper.getSmartHeight(FontHelper.tipHeaderFont, header, ReflectionHacks.getPrivateStatic(TipHelper.class, "BODY_TEXT_WIDTH"), ReflectionHacks.getPrivateStatic(TipHelper.class, "TIP_DESC_LINE_SPACING"));
    }
}
