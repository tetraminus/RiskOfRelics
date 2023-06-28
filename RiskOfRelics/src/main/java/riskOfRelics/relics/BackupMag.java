package riskOfRelics.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import riskOfRelics.RiskOfRelics;

import java.util.ArrayList;
import java.util.Objects;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class BackupMag extends BaseRelic {


    public static final int AMOUNT = 1;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("BackupMag");
    private static final String IMAGENAME = "BackupMag.png";

    public BackupMag() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.FLAT);
    }
    public AbstractRelic relToAdd;
    @Override
    public void onEquip() {
        ArrayList<AbstractRelic> otherRelics = new ArrayList<AbstractRelic>();

        for (AbstractRelic r : player.relics) {
            if (!Objects.equals(r.relicId, this.relicId)){
                otherRelics.add(r);
            }
        }

        if (otherRelics.size() > 0) {
            relToAdd = player.relics.get(AbstractDungeon.relicRng.random(otherRelics.size()-1));

        }
    }

    public void postUpdate() {

        if (relToAdd != null){
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (this.currentX), (float) (this.currentY), relToAdd.makeCopy());
            relToAdd = null;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}