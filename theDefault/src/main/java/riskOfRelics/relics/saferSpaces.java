package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import riskOfRelics.DefaultMod;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class saferSpaces extends BaseRelic {


    public static final int AMOUNT = 5;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("saferSpaces");
    private static final String IMAGENAME = "saferSpaces.png";

    public saferSpaces() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        counter= 0;
    }

    @Override
    public void obtain() {
        if (player.hasRelic(tougherTimes.ID)){
            flash();
            player.loseRelic(tougherTimes.ID);
        }
        super.obtain();
    }


    @Override
    public void atTurnStart() {
        if (counter == 6){
            this.addToBot(new ApplyPowerAction(player,player,new BufferPower(player,1)));
            this.addToBot(new RelicAboveCreatureAction(player, this));
            flash();
            counter = 0;

        }else {
            counter++;
        }
        super.atTurnStart();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}