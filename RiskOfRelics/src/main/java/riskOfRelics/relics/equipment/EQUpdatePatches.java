package riskOfRelics.relics.equipment;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.patches.equipment.EquipmentFieldPatch;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class EQUpdatePatches {
    @SpirePatch2(
            clz = OverlayMenu.class,
            method = "update"
    )
    public static class UpdateEquipment {
        public static void Postfix(OverlayMenu __instance){
            if (EquipmentFieldPatch.PlayerEquipment.get(player) != null && EquipmentFieldPatch.PlayerEquipment.get(player).isAvailable()) {
                EquipmentFieldPatch.PlayerEquipment.get(player).update();
            }
        }
    }
    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "applyStartOfCombatLogic"
    )
    public static class EQStartCombat {
        public static void Postfix(AbstractPlayer __instance){
            if (EquipmentFieldPatch.PlayerEquipment.get(__instance) != null) {
                EquipmentFieldPatch.PlayerEquipment.get(__instance).atBattleStart();
            }
        }
    }
    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnRelics"
    )
    public static class EQStartTurn {
        public static void Postfix(AbstractPlayer __instance){
            if (EquipmentFieldPatch.PlayerEquipment.get(__instance) != null) {
                EquipmentFieldPatch.PlayerEquipment.get(__instance).atTurnStart();
            }
        }
    }
    @SpirePatch2(
            clz = AbstractRoom.class,
            method = "applyEndOfTurnRelics"
    )
    public static class EQEndTurn {
        public static void Postfix(){
            if (EquipmentFieldPatch.PlayerEquipment.get(player) != null) {
                EquipmentFieldPatch.PlayerEquipment.get(player).onPlayerEndTurn();
            }
        }
    }


}
