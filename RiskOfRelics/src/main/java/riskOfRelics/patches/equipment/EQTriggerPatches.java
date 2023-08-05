package riskOfRelics.patches.equipment;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class EQTriggerPatches {
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

    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "hasRelic"
    )
    public static class EQHasRelic {
        public static boolean Postfix(boolean __result, AbstractPlayer __instance, String targetID){
            if (EquipmentFieldPatch.PlayerEquipment.get(__instance) != null && EquipmentFieldPatch.PlayerEquipment.get(__instance).relicId.equals(targetID)) {
                return true;
            }
            return __result;
        }
    }
    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "onVictory"
    )
    public static class EQVictory {
        public static void Prefix(AbstractPlayer __instance){
            if (EquipmentFieldPatch.PlayerEquipment.get(__instance) != null) {
                EquipmentFieldPatch.PlayerEquipment.get(__instance).onVictory();
            }
        }


    }

}
