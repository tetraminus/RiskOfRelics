package riskOfRelics.cards;

import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.RiskOfRelics;

public class ChaosCardmod extends AbstractCardModifier {
    @Override
    public String identifier(AbstractCard card) {
        return RiskOfRelics.makeID("ChaosCardmod");
    }




    public float originalMagicNumber = 0;

    public float damageMultiplier = 1;
    public float blockMultiplier = 1;
    public float magicMultiplier = 1;



    @Override
    public void onInitialApplication(AbstractCard card) {
        originalMagicNumber = card.baseMagicNumber;
        Randomize();



        super.onInitialApplication(card);
    }

    @Override
    public void onDrawn(AbstractCard card) {
        Randomize();
        super.onDrawn(card);
    }

    private void Randomize() {
        damageMultiplier = AbstractDungeon.cardRng.random(.1f, 2f);
        blockMultiplier = AbstractDungeon.cardRng.random(.1f, 2f);
        magicMultiplier = AbstractDungeon.cardRng.random(0.1f, 2f);
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        return (float) Math.ceil(originalMagicNumber * magicMultiplier);
    }

    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return super.modifyName("~"+cardName+"~", card);
    }

    @Override
    public void onRemove(AbstractCard card) {

        card.baseMagicNumber = (int) originalMagicNumber;
        super.onRemove(card);
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return super.modifyDamage(Math.round(damage * damageMultiplier), type, card, target);
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        return super.modifyBlock(Math.round(block * blockMultiplier), card);
    }

    public ChaosCardmod makeCopy() {
        return new ChaosCardmod();
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }
}
