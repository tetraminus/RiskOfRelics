package riskOfRelics.patches.equipment;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.OverlayMenu;

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



}
