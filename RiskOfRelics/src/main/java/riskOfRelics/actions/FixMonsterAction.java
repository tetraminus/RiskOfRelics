package riskOfRelics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FixMonsterAction extends AbstractGameAction {

    private AbstractMonster m;
    public FixMonsterAction(AbstractMonster m) {
        this.duration = 0.0F;
        this.m = m;
    }

    public void update() {
        m.createIntent();
        m.usePreBattleAction();

        this.isDone = true;
    }
}
