package riskOfRelics.relics;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.RiskOfRelics;

public class Shuriken extends BaseRelic{

    public static final int AMOUNT = 3;

    public static final int DAMAGE = 6;



    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Shuriken");
    private static final String IMAGENAME = "Shuriken.png";

    public Shuriken() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);

    }

    @Override
    public void atBattleStart() {
        counter = AMOUNT;
        grayscale = false;

    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (c.tags.contains(AbstractCard.CardTags.STARTER_STRIKE) && counter > 0) {
            counter--;
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(DAMAGE, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            if (counter == 0) {
                grayscale = true;
            }
        }
    }

    @Override
    public void onVictory() {
        counter = -1;
        grayscale = false;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1] + DAMAGE + DESCRIPTIONS[2];
    }
}
