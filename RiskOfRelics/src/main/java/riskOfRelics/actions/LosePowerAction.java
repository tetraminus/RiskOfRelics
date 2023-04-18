package riskOfRelics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class LosePowerAction extends AbstractGameAction {
    private final AbstractPower Pow;

    public LosePowerAction(AbstractCreature target, AbstractPower Pow) {
        this.Pow = Pow;
    }

    @Override
    public void update() {
        player.powers.remove(Pow);
        isDone = true;
    }
}
