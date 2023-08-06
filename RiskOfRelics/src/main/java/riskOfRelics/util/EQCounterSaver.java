package riskOfRelics.util;

import basemod.abstracts.CustomSavable;
import riskOfRelics.patches.equipment.EquipmentFieldPatch;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class EQCounterSaver implements CustomSavable<Integer> {
    @Override
    public Integer onSave() {
        if (EquipmentFieldPatch.PlayerEquipment.get(player) == null) {
            return null;
        }
        return EquipmentFieldPatch.PlayerEquipment.get(player).counter;
    }

    @Override
    public void onLoad(Integer integer) {
        if (EquipmentFieldPatch.PlayerEquipment.get(player) == null) {
            return;
        }
        EquipmentFieldPatch.PlayerEquipment.get(player).counter = integer;

    }

}
