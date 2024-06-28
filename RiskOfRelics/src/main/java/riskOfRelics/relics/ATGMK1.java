package riskOfRelics.relics;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ATGMK1 extends BaseRelic {

    public static final int AMOUNT = 6;
    public static final int PERCENT = 30;
    private int Sum = 0;

    public static final String ID = RiskOfRelics.makeID("ATGMK1");
    private static final String IMAGENAME = "ATGMK1.png";

    public ATGMK1() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        counter = 0;
        grayscale = false;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        super.onUseCard(targetCard, useCardAction);

        if (targetCard.type == AbstractCard.CardType.ATTACK) {
            Sum += targetCard.damage;
            counter++;
            if (counter >= AMOUNT) {
                counter = 0;
                addToBot(new DamageRandomEnemyAction(new DamageInfo(player, (int) Math.ceil(Sum * PERCENT / 100f), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                Sum = 0;
            } else {
                addToBot(new VFXAction(new TextAboveCreatureEffect(player.hb.cX - player.animX,
                        player.hb.cY + player.hb.height / 2.0F,
                        (int) Math.ceil(Sum * PERCENT / 100f) + " Damage!", Color.RED)));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1] + PERCENT + DESCRIPTIONS[2] + AMOUNT + DESCRIPTIONS[3];
    }
}
