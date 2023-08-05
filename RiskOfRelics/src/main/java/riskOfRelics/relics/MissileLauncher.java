package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.equipment.AbstractEquipment;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class MissileLauncher extends AbstractEquipment {


    public static final int AMOUNT = 5;
    public static final int DAMAGE= 5;

    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("MissileLauncher");
    private static final String IMAGENAME = "MissileLauncher.png";

    public MissileLauncher() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        if (!CardCrawlGame.isInARun() || player == null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
        }

        AbstractCard dummyCard = new Shiv();
        dummyCard.baseDamage = DAMAGE;
        dummyCard.applyPowers();
        dummyCard.calculateCardDamage((AbstractMonster) null);
        int damage = dummyCard.damage;
        return DESCRIPTIONS[0] + damage + DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];



    }

    @Override
    public void onRightClick() {
        AbstractCard dummyCard = new Shiv();
        dummyCard.baseDamage = DAMAGE;
        dummyCard.applyPowers();
        dummyCard.calculateCardDamage((AbstractMonster) null);
        int damage = dummyCard.damage;


        for (int i = 0; i < AMOUNT; i++) {
            this.addToBot(new DamageRandomEnemyAction(new DamageInfo(player, damage), AbstractGameAction.AttackEffect.FIRE));
        }

        super.onRightClick();
    }
}