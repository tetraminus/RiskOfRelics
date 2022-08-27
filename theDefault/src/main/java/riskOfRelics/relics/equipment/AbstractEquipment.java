package riskOfRelics.relics.equipment;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;

public abstract class AbstractEquipment extends CustomRelic implements ClickableRelic {

    public AbstractEquipment(String id, Texture texture, LandingSound sfx) {
        super(id, texture, RelicTier.BOSS, sfx);
    }

    // when obtained, check if the player has any equipment, if not obtain it


}
