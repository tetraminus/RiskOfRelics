package riskOfRelics.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import riskOfRelics.DefaultMod;
import riskOfRelics.cards.AbstractEquipmentCard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class fuelCell extends BaseRelic {


    public static final int AMOUNT = 1;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("fuelCell");
    private static final String IMAGENAME = "fuelCell.png";

    public fuelCell() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }



    @Override
    public void obtain() {
        for (AbstractCard c:
             player.masterDeck.group) {
            if (c instanceof AbstractEquipmentCard){
                ((AbstractEquipmentCard) c).addCharges(AMOUNT);
            }

        }

        super.obtain();
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (c instanceof AbstractEquipmentCard){
            ((AbstractEquipmentCard) c).addCharges(AMOUNT);
        }
        super.onObtainCard(c);
    }

    @Override
    public void atPreBattle() {
        for (AbstractCard c: player.drawPile.group) {
            if (c instanceof AbstractEquipmentCard){
                ((AbstractEquipmentCard) c).addCharges(AMOUNT);
                c.initializeDescription();
            }
        }
        super.atPreBattle();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+AMOUNT+DESCRIPTIONS[1];
    }

}