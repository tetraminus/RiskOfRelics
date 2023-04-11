package riskOfRelics.relics;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.DefaultMod;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class tougherTimes extends BaseRelic {


    public static final int AMOUNT = 10;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("tougherTimes");
    private static final String IMAGENAME = "tougherTimes.png";

    public tougherTimes() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void obtain() {
        if (player.hasRelic(saferSpaces.ID)){
            player.getRelic(saferSpaces.ID).flash();
            player.loseRelic(this.relicId);
         }else {
            super.obtain();
        }

    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        int ran = AbstractDungeon.miscRng.random(100);
        //logger.info(ran);
        if (ran<=AMOUNT && info.type != DamageInfo.DamageType.HP_LOSS){
            flash();

            return 0;
        }
        return super.onAttackedToChangeDamage(info, damageAmount);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}