package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.ThroughViolence;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.equipment.AbstractEquipment;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class MissileLauncher extends AbstractEquipment {


    public static final int AMOUNT = 5;
    public static final int DAMAGE= 3;
    private AbstractCard dummyCard = new ThroughViolence();
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("MissileLauncher");
    private static final String IMAGENAME = "MissileLauncher.png";

    public MissileLauncher() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        if (!CardCrawlGame.isInARun() || player == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT){
            return DESCRIPTIONS[0] + DAMAGE + DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];
        }


        CalcDamage();
        int damage = dummyCard.damage;
        return DESCRIPTIONS[0] + damage + DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];



    }

    private void CalcDamage() {
        dummyCard.baseDamage = DAMAGE;
        dummyCard.applyPowers();
        dummyCard.calculateCardDamage((AbstractMonster) null);
    }

    @Override
    public void update() {
        descriptionUpdate();
        super.update();
    }

    @Override
    public void onRightClick() {
        CalcDamage();
        int damage = dummyCard.damage;


        for (int i = 0; i < AMOUNT; i++) {
            this.addToBot(new DamageRandomEnemyAction(new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        }

        super.onRightClick();
    }
    @Override
    public void onEquip()
    {
        descriptionUpdate();
    }



    public void descriptionUpdate() {
        this.description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }
}