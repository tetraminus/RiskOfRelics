package riskOfRelics.patches.equipment;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.relics.equipment.AbstractEquipment;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class EQGetBlightPatch {
    @SpirePatch2(
            clz = AbstractBlight.class,
            method = "obtain"
    )
    public static class EQGetBlight {
        public static void Postfix(AbstractBlight __instance){
            if (EquipmentFieldPatch.PlayerEquipment.get(player) != null && AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
                AbstractEquipment equipment = EquipmentFieldPatch.PlayerEquipment.get(player);
                equipment.GoHome();
            }
        }
    }
}
