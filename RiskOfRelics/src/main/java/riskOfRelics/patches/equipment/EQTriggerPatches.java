package riskOfRelics.patches.equipment;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.nextRoom;
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
    @SpirePatch2(clz = AbstractDungeon.class, method = "nextRoomTransition", paramtypez = {SaveFile.class})
    public static class NextRoomTransitionPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractDungeon __instance, SaveFile saveFile) {
            if (EquipmentFieldPatch.PlayerEquipment.get(player) != null) {
                EquipmentFieldPatch.PlayerEquipment.get(player).onEnterRoom(nextRoom.room);
            }

        }

        private static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "iterator");

                return new int[]{LineFinder.findAllInOrder(ctBehavior, finalMatcher)[1]};

            }
        }
    }

}
