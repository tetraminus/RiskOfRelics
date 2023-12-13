package riskOfRelics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

public class DamageRandomEntityAction extends AbstractGameAction {
    private DamageInfo info;
    private AttackEffect attackEffect;
    private float playerDamageMultiplier = 1.0F;

    public DamageRandomEntityAction(DamageInfo damageInfo, AttackEffect attackEffect) {
        info = damageInfo;

        this.attackEffect = attackEffect;

    }

    public DamageRandomEntityAction(DamageInfo damageInfo, AttackEffect attackEffect, float playerDamageMultiplier) {
        info = damageInfo;
        this.attackEffect = attackEffect;
        this.playerDamageMultiplier = playerDamageMultiplier;

    }

    @Override
    public void update() {
        List<AbstractMonster> monsters =  AbstractDungeon.getMonsters().monsters;
        monsters.removeIf(AbstractCreature::isDeadOrEscaped);
        List<AbstractCreature> creatures = new ArrayList<>(monsters);
        creatures.add(AbstractDungeon.player);
        AbstractCreature target = creatures.get(AbstractDungeon.cardRandomRng.random(creatures.size() - 1));
        if (target == AbstractDungeon.player) {
            info.base = (int) (info.base * playerDamageMultiplier);
        }
        this.addToTop(new DamageAction(target, info, attackEffect));
        isDone = true;
    }
}
