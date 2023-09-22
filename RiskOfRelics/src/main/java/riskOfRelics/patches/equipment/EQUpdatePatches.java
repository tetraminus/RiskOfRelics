package riskOfRelics.patches.equipment;

import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import javassist.CtBehavior;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class EQUpdatePatches {
    @SpirePatch2(
            clz = OverlayMenu.class,
            method = "update"
    )
    public static class UpdateEquipment {
        public static void Postfix(OverlayMenu __instance){
            if (EquipmentFieldPatch.PlayerEquipment.get(player) != null) {
                EquipmentFieldPatch.PlayerEquipment.get(player).update();
            }
        }
    }

    @SpirePatch2(
            clz = AbstractRelic.class,
            method = "update"
    )
    public static class UpdatePatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractRelic __instance){
            if (EquipmentFieldPatch.PlayerEquipment.get(player) != null && __instance == EquipmentFieldPatch.PlayerEquipment.get(player) && !(HitboxRightClick.rightClicked.get(__instance.hb) )) {
                __instance.hb.update();
            }

        }
        private static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(PotionPopUp.class, "isHidden");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }

    }



}
