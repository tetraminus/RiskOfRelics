package riskOfRelics.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

import java.util.ArrayList;

public class RelicDestroyEffect extends AbstractGameEffect {
    ArrayList<AbstractRelic> relicsToRemove;
    ArrayList<AbstractRelic> destroyedRelics = new ArrayList<>();


    public RelicDestroyEffect(ArrayList<AbstractRelic> relicsToRemove) {
        super();
        color = Color.WHITE.cpy();
        this.relicsToRemove = relicsToRemove;
        duration = 0.5F;
        startingDuration = 0.5F;
        for (AbstractRelic relic : relicsToRemove) {
            relic.targetY = -100.0F * Settings.scale;
            relic.isDone = false;
            relic.isAnimating = true;

        }
    }

    @Override
    public void update() {

        super.update();

        for (AbstractRelic relic : relicsToRemove) {
            relic.update();
            if (relic.currentY < 10 * Settings.scale && !destroyedRelics.contains(relic)) {
                AbstractDungeon.effectsQueue.add(new ExplosionSmallEffect(relic.currentX, relic.currentY));
                destroyedRelics.add(relic);
            }
        }

        if (destroyedRelics.size() == relicsToRemove.size()) {
            isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setColor(Color.WHITE.cpy());
        for (AbstractRelic relic : relicsToRemove) {
            relic.render(spriteBatch);

        }
    }

    @Override
    public void dispose() {

    }
}
