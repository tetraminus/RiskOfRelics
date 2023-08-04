package riskOfRelics.relics.equipment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import riskOfRelics.patches.equipment.EquipmentFieldPatch;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class EQRenderPatches {
    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "renderRelics"
    )
    public static class RenderEquipment {
        public static void Postfix(AbstractPlayer __instance, SpriteBatch sb){
            if (EquipmentFieldPatch.PlayerEquipment.get(__instance) != null && EquipmentFieldPatch.PlayerEquipment.get(player).isAvailable()) {
                EquipmentFieldPatch.PlayerEquipment.get(__instance).renderInTopPanel(sb);
            }
        }
    }

}
