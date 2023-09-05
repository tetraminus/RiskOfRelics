package riskOfRelics.cards.colorless;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.bosses.BulwarksAmbry;
import riskOfRelics.cards.AbstractDefaultCard;

import static riskOfRelics.RiskOfRelics.makeCardPath;
import static riskOfRelics.RiskOfRelics.makeID;


public class GlowingShard extends AbstractDefaultCard {
    public static final String ID = makeID(GlowingShard.class.getSimpleName());


    private static final int DAMAGE = 250;
    private static final int UPG_DAMAGE = 0;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    public GlowingShard() {
        super(ID, cardStrings.NAME, makeCardPath("TonicAffliction.png"), 0, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, CardTarget.NONE);
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
           if (!mo.isDeadOrEscaped() && mo instanceof BulwarksAmbry) {
               addToBot(new LoseHPAction(mo, mo, DAMAGE));
           }

        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GlowingShard();
    }
}
