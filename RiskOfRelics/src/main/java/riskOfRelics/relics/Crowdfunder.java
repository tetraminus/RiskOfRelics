package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.equipment.AbstractEquipment;
import riskOfRelics.vfx.CoinPingEffect;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class Crowdfunder extends AbstractEquipment {


    public static final int AMOUNT = 3;
    public static final int COST = 10;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Crowdfunder");
    private static final String IMAGENAME = "Crowdfunder.png";
    private static final int BASE_COUNTER = -1;


    public Crowdfunder() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);
        lockedCharges = true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + COST + DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];
    }


    @Override
    public boolean canClick() {

        return isPlayerTurn && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    @Override
    public int GetBaseCounter() {
        return BASE_COUNTER;
    }

    @Override
    public void onRightClick() {
        if (player.gold >= COST) {

            AbstractMonster m = AbstractDungeon.getRandomMonster();
            if (m == null){
                return;
            }
            player.loseGold(COST);
            CardCrawlGame.sound.play("GOLD_JINGLE");
            this.addToBot(new VFXAction(new CoinPingEffect(player.hb.cX, player.hb.cY, m.hb.cX, m.hb.cY )));
            this.addToBot(new DamageAction(m, new DamageInfo(player, AMOUNT, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
        }
        else {
            this.flash();
        }


        super.onRightClick();
    }
}