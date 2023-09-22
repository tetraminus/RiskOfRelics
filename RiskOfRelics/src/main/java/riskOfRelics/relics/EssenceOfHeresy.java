package riskOfRelics.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.powers.RuinPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class EssenceOfHeresy extends BaseRelic implements ClickableRelic {


    public static final int STRENGH_LOSS = 4;
    public static final int RUIN_AMOUNT = 4;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("EssenceOfHeresy");
    private static final String IMAGENAME = "EssenceOfHeresy.png";
    private boolean isPlayerTurn = false;

    public EssenceOfHeresy() {
        super(ID, IMAGENAME, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.addToBot(new ApplyPowerAction(player, player,
                new StrengthPower(player, (-STRENGH_LOSS)), (-STRENGH_LOSS)));
        super.atBattleStart();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > target.currentBlock && target != player) {
            this.addToBot(new ApplyPowerAction(target, player,
                    new RuinPower(target, RUIN_AMOUNT), RUIN_AMOUNT));
        }

        super.onAttack(info, damageAmount, target);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+STRENGH_LOSS+DESCRIPTIONS[1]+(RUIN_AMOUNT)+DESCRIPTIONS[2];
    }

    @Override
    public void onRightClick() {
        if (!isObtained || !isPlayerTurn || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            // If it has been used this turn, the player doesn't actually have the relic (i.e. it's on display in the shop room), or it's the enemy's turn
            return; // Don't do anything.
        }
        for (AbstractCreature m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped()) {
                if (m.hasPower(RuinPower.POWER_ID)) {
                    m.getPower(RuinPower.POWER_ID).onSpecificTrigger();
                }
            }
        }


    }
    @Override
    public void atTurnStart() {
        //usedThisTurn = false;  // Resets the used this turn. You can remove this to use a relic only once per combat rather than per turn.
        isPlayerTurn = true; // It's our turn!
//        beginLongPulse(); // Pulse while the player can click on it.
    }

    @Override
    public void onPlayerEndTurn() {
        isPlayerTurn = false; // Not our turn now.
        stopPulse();
    }


}