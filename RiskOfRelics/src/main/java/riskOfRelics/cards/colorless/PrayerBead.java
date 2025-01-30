package riskOfRelics.cards.colorless;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import riskOfRelics.bosses.BulwarksAmbry;
import riskOfRelics.cards.AbstractDefaultCard;
import riskOfRelics.relics.PrayerBeads;

import static riskOfRelics.RiskOfRelics.makeCardPath;
import static riskOfRelics.RiskOfRelics.makeID;


public class PrayerBead extends AbstractDefaultCard {
    public static final String ID = makeID(PrayerBead.class.getSimpleName());


    private static final int DAMAGE = 250;
    private static final int UPG_DAMAGE = 0;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    public PrayerBead() {
        super(ID, cardStrings.NAME, null, -2, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, CardTarget.NONE);
        this.baseMagicNumber = DAMAGE;
        exhaust = true;

    }

    @Override
    public void onRemoveFromMasterDeck() {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r.relicId.equals(PrayerBeads.ID)) {
                PrayerBeads pb = (PrayerBeads) r;
                pb.PrayerBeadRemoved();
            }
        }

    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }


    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new PrayerBead();
    }
}
