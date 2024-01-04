package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class CorpseBloom extends BaseRelic {

    boolean isCorpsebloomHeal;
    public static final float HEAL_AMOUNT = 0.33f;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("corpsebloom");


    private static final String IMAGENAME = "Corpsebloom.png";

    public CorpseBloom() {super(ID,IMAGENAME, RelicTier.BOSS, LandingSound.MAGICAL);}




    @Override
    public void atBattleStart() {
        super.atBattleStart();
    }

    @Override
    public void onPlayerEndTurn() {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && counter > 0) {
            isCorpsebloomHeal = true;
            float healed = counter*HEAL_AMOUNT;
            AbstractDungeon.actionManager.addToTop(new HealAction(player, player, (int)Math.ceil(healed)));
            counter -= healed;
            flash();
        }
        super.onPlayerEndTurn();
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if(counter > 0) {
            isCorpsebloomHeal = true;
            float healed = counter*HEAL_AMOUNT;
            player.heal((int)Math.ceil(healed));
            counter -= healed;
            flash();
        }
        super.onEnterRoom(room);
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase.equals(AbstractRoom.RoomPhase.COMBAT)) {
            if (isCorpsebloomHeal) {
                isCorpsebloomHeal = false;
                return super.onPlayerHeal(healAmount);

            } else {
                counter += healAmount * 2;
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(player, this));
                return 0;

            }
        } else {
            return super.onPlayerHeal(healAmount);
        }
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }



}
