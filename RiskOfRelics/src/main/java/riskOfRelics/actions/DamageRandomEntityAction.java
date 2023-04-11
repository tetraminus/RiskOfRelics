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

    public DamageRandomEntityAction(DamageInfo damageInfo, AttackEffect attackEffect) {
        info = damageInfo;

        this.attackEffect = attackEffect;
    }

    @Override
    public void update() {
        List<AbstractMonster> monsters =  AbstractDungeon.getMonsters().monsters;
        monsters.removeIf(AbstractCreature::isDeadOrEscaped);
        List<AbstractCreature> creatures = new ArrayList<>(monsters);
        creatures.add(AbstractDungeon.player);
        this.addToBot(new DamageAction(creatures.get(AbstractDungeon.relicRng.random(creatures.size() - 1)), info, attackEffect));
        isDone = true;
    }
}
