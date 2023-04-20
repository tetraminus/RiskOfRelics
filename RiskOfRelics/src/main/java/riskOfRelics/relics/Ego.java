package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import riskOfRelics.DefaultMod;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class Ego extends BaseRelic {


    public static final int AMOUNT = 10;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Ego");
    private static final String IMAGENAME = "Ego.png";
    public AbstractRelic relicToremove = null;

    public Ego() {
        super(ID, IMAGENAME, RelicTier.BOSS, LandingSound.MAGICAL);
        this.counter = 0;
    }
    // after entering 5 rooms, remove a random relic and increase the counter by one.
    @Override
    public void onVictory() {
            flash();
            ArrayList<AbstractRelic> otherRelics = new ArrayList<AbstractRelic> (player.relics);
            otherRelics.remove(this);
            if (otherRelics.size() > 0) {
                relicToremove = otherRelics.get(AbstractDungeon.relicRng.random(otherRelics.size() - 1));
                if (counter < 0) {
                    counter = 0;
                }
                this.counter += 1;
            }
    }
    public void postUpdate(){
        if (relicToremove != null) {
            if (relicToremove instanceof Ego) {
                this.counter += relicToremove.counter;
            }
            player.loseRelic(relicToremove.relicId);
            relicToremove = null;
        }
    }

    @Override
    public void atTurnStart() {
        for (int i = 0; i < this.counter; i++) {
            this.addToBot(new DamageRandomEnemyAction(new DamageInfo(player, AMOUNT, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
        super.atTurnStart();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}