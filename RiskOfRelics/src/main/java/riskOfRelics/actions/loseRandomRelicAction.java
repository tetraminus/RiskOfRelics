package riskOfRelics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import riskOfRelics.vfx.RelicDestroyEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static riskOfRelics.RiskOfRelics.makeID;

public class loseRandomRelicAction extends AbstractGameAction {
    private final int number;
    private UIStrings uiStrings = languagePack.getUIString(makeID("RelicDestroy"));
    public loseRandomRelicAction(int number) {
        this.number = number;

    }

    @Override
    public void update() {
        ArrayList<AbstractRelic> relicsToRemove = new ArrayList<>();
        ArrayList<AbstractRelic> relicList = new ArrayList<>(player.relics);
        for (int i = 0; i < number; i++) {

            if (!relicList.isEmpty()) {
                int relicIndex = (int) (AbstractDungeon.monsterRng.random(0, relicList.size() - 1));
                relicsToRemove.add(relicList.get(relicIndex));
            }
        }

        for (AbstractRelic relic : relicsToRemove) {
            player.loseRelic(relic.relicId);
        }
        AbstractDungeon.topLevelEffects.add(new RelicDestroyEffect(relicsToRemove));
        for (AbstractRelic relic : relicsToRemove) {
            addToBot(new BetterTextCenteredAction( AbstractDungeon.player, relic.name + uiStrings.TEXT[0], 1f));
        }



        isDone = true;
    }
}
