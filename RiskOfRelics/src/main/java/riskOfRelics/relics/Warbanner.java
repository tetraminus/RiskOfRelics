package riskOfRelics.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.screens.WarbannerOption;

import java.util.ArrayList;


public class Warbanner extends BaseRelic {


    public static final int AMOUNT = 3;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Warbanner");
    private static final String IMAGENAME = "Warbanner.png";

    public Warbanner() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        counter = AMOUNT;


    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        if (this.counter > 0 && hasUsableCard()) {
            options.add(new WarbannerOption(true, this));
        }
        super.addCampfireOption(options);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    public boolean hasUsableCard() {
        for (AbstractCard c: AbstractDungeon.player.masterDeck.group) {
            if (c.cost > 0 && c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                return true;
            }
        }
        return false;

    }

}