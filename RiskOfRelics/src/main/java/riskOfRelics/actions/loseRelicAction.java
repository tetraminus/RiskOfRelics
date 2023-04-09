package riskOfRelics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class loseRelicAction extends AbstractGameAction {
    private final String relicId;

    public loseRelicAction(String relicId) {
        this.relicId = relicId;
    }

    @Override
    public void update() {
        player.loseRelic(relicId);
    }
}
