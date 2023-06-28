package riskOfRelics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoseGoldAction extends AbstractGameAction {
    public LoseGoldAction(int amount) {
        this.amount = amount;// 9
    }// 10

    public void update() {
        AbstractDungeon.player.loseGold(this.amount);// 13
        this.isDone = true;// 14
    }// 15
}
