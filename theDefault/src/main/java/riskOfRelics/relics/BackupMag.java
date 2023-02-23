package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.DefaultMod;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCardWithoutRng;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class BackupMag extends BaseRelic {


    public static final int AMOUNT = 1;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("BackupMag");
    private static final String IMAGENAME = "BackupMag.png";

    public BackupMag() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        int randomrelic = AbstractDungeon.relicRng.random(player.relics.size()-2);

        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), player.relics.get(randomrelic).makeCopy());

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}