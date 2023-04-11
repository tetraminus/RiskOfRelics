package riskOfRelics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import riskOfRelics.powers.BurningPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class KjarosBandAction extends AbstractGameAction {
    private AbstractPower powerToApply;
    private boolean isFast;
    private AbstractGameAction.AttackEffect effect;

    public KjarosBandAction(int Amount) {
        this.amount = Amount;


    }// 24

    public void update() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            this.target = m;
            this.addToBot(new ApplyPowerAction(m, player, new BurningPower(m,player,amount)));

        }


        this.isDone = true;// 49
    }// 50
}

