package riskOfRelics.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractEquipmentCard extends AbstractDynamicCard{
    public int MAX_CHARGES;
    public int CURRENT_CHARGES;
    public AbstractEquipmentCard(String id, String img, int cost, CardType type, CardTarget target) {
        super(id, img, cost, type, CardColor.COLORLESS, CardRarity.UNCOMMON, target);

        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, MAX_CHARGES);// 57
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, MAX_CHARGES);// 58
        ExhaustiveField.ExhaustiveFields.isExhaustiveUpgraded.set(this, true);
        CURRENT_CHARGES = ExhaustiveField.ExhaustiveFields.exhaustive.get(this);
        initializeDescription();

    }


    public void addCharges(int am){
        MAX_CHARGES +=am;
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, MAX_CHARGES);// 57
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, MAX_CHARGES);// 58
        CURRENT_CHARGES = ExhaustiveField.ExhaustiveFields.exhaustive.get(this);
        initializeDescription();
    }

    public void setCharges(int am){
        MAX_CHARGES = am;
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, MAX_CHARGES);// 57
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, MAX_CHARGES);// 58
        CURRENT_CHARGES = ExhaustiveField.ExhaustiveFields.exhaustive.get(this);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.CURRENT_CHARGES > 1 && this.type == CardType.POWER) {
            AbstractEquipmentCard copy = (AbstractEquipmentCard) this.makeStatEquivalentCopy();
            copy.setCharges(CURRENT_CHARGES-1);
            this.addToBot(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(), 1));// 38
        }


        CURRENT_CHARGES = ExhaustiveField.ExhaustiveFields.exhaustive.get(this);
        initializeDescription();
    }

    @Override
    public void atTurnStartPreDraw() {
        CURRENT_CHARGES = ExhaustiveField.ExhaustiveFields.exhaustive.get(this);
        initializeDescription();
        super.atTurnStartPreDraw();
    }
}
