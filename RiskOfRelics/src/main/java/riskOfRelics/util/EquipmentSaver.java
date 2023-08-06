package riskOfRelics.util;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import riskOfRelics.patches.equipment.EquipmentFieldPatch;
import riskOfRelics.relics.equipment.AbstractEquipment;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class EquipmentSaver implements CustomSavable<String> {
    @Override
    public String onSave() {
        if (EquipmentFieldPatch.PlayerEquipment.get(player) == null) {
            return null;
        }
        return EquipmentFieldPatch.PlayerEquipment.get(player).relicId;
    }

    @Override
    public void onLoad(String s) {
        if (s == null) {
            return;
        }
        EquipmentFieldPatch.PlayerEquipment.set(player, (AbstractEquipment) RelicLibrary.getRelic(s).makeCopy());
        EquipmentFieldPatch.PlayerEquipment.get(player).instantObtain(player, 0, false);

    }
}
