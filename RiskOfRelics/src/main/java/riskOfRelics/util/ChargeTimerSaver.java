package riskOfRelics.util;

import basemod.abstracts.CustomSavable;
import riskOfRelics.patches.equipment.EquipmentFieldPatch;
import riskOfRelics.relics.equipment.HasChargeTimerEQ;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ChargeTimerSaver implements CustomSavable<Integer> {
    @Override
    public Integer onSave() {
        if (!(EquipmentFieldPatch.PlayerEquipment.get(player) instanceof HasChargeTimerEQ)) {
            return null;
        }
        return  ((HasChargeTimerEQ) EquipmentFieldPatch.PlayerEquipment.get(player)).getCharges();
    }

    @Override
    public void onLoad(Integer integer) {
        if (!(EquipmentFieldPatch.PlayerEquipment.get(player) instanceof HasChargeTimerEQ)) {
            return;
        }
        ((HasChargeTimerEQ) EquipmentFieldPatch.PlayerEquipment.get(player)).setCharges(integer);
    }
}
