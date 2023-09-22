package riskOfRelics.patches.equipment;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import riskOfRelics.relics.equipment.AbstractEquipment;

@SpirePatch2(
        clz = AbstractPlayer.class,
        method = SpirePatch.CLASS
)
public class EquipmentFieldPatch {
    public static SpireField<AbstractEquipment> PlayerEquipment = new SpireField<>(() -> null); // 1
}
