package riskOfRelics.cards.curses;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.cards.AbstractDynamicCard;
import riskOfRelics.powers.TonicDebuff;

import static riskOfRelics.RiskOfRelics.makeCardPath;

public class TonicAffliction extends AbstractDynamicCard {


    // public static final String ID = DefaultMod.makeID(${NAME}.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String ID = RiskOfRelics.makeID("TonicAffliction"); // DELETE THIS ONE.
    public static final String IMG = makeCardPath("TonicAffliction.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.CURSE;       //
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = -2;  // COST = ${COST}
    private static final int UPGRADED_COST = 0; // UPGRADED_COST = ${UPGRADED_COST}

    private static final int MAGIC = 20;    // DAMAGE = ${DAMAGE}


    // /STAT DECLARATION/


    public TonicAffliction() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
          this.baseMagicNumber = 20;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TonicDebuff(AbstractDungeon.player, AbstractDungeon.player,1), 1));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {

    }


}
