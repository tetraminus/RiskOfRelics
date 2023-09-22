
package riskOfRelics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;

public class ApplyBlockAllEnemiesAction extends AbstractGameAction {
    public ApplyBlockAllEnemiesAction(AbstractCreature source, int amount) {
        this.duration = 0.5F;// 15
        this.source = source;// 16
        this.amount = amount;// 17
        this.actionType = ActionType.BLOCK;// 18
    }// 19

    public void update() {
        if (this.duration == 0.5F) {// 23


            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {// 25
                if (m != this.source && m.intent != Intent.ESCAPE && !m.isDying) {// 26
                    m.addBlock(this.amount);
                }

            }


        }

        this.tickDuration();// 43
    }// 44
}