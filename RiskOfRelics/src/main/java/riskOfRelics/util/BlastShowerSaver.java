package riskOfRelics.util;

import basemod.abstracts.CustomSavable;
import riskOfRelics.patches.equipment.EquipmentFieldPatch;
import riskOfRelics.relics.BlastShower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class BlastShowerSaver implements CustomSavable<Integer> {
    @Override
    public Integer onSave() {
        if (!(EquipmentFieldPatch.PlayerEquipment.get(player) instanceof BlastShower)) {
            return null;
        }
        return  ((BlastShower) EquipmentFieldPatch.PlayerEquipment.get(player)).rechargeCount;
    }

    @Override
    public void onLoad(Integer integer) {
        if (!(EquipmentFieldPatch.PlayerEquipment.get(player) instanceof BlastShower)) {
            return;
        }

        ((BlastShower) EquipmentFieldPatch.PlayerEquipment.get(player)).rechargeCount = integer;
    }
}
