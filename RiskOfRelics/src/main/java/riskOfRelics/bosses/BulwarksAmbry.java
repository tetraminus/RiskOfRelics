//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package riskOfRelics.bosses;

import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import riskOfRelics.actions.FixMonsterAction;
import riskOfRelics.cards.colorless.GlowingShard;
import riskOfRelics.patches.DissArtPatches;
import riskOfRelics.powers.Untargetable;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom;
import static riskOfRelics.RiskOfRelics.makeID;

public class BulwarksAmbry extends AbstractMonster {
    public static final String ID = makeID("BulwarksAmbry");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final byte BLOOD_SHOTS = 1;
    private static final byte ECHO_ATTACK = 2;
    private static final byte DEBILITATE = 3;
    private static final byte GAIN_ONE_STRENGTH = 4;
    public static final int DEBUFF_AMT = -1;
    private int bloodHitCount;
    private boolean isFirstMove = true;
    private int moveCount = 0;
    private int buffCount = 0;

    public BulwarksAmbry() {
        super(NAME, ID, 750, 30.0F, -30.0F, 476.0F, 410.0F, (String)null, -50.0F, 30.0F);// 54
        this.loadAnimation("images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", 1.0F);// 56
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);// 57
        e.setTimeScale(1.5F);// 58
        this.type = EnemyType.BOSS;// 60
        if (AbstractDungeon.ascensionLevel >= 9) {// 62
            this.setHp(1250);// 63
        } else {
            this.setHp(1000);// 65
        }
        this.drawY += 200.0F * Settings.scale;// 67
        this.drawX += 200.0F * Settings.scale;// 68
        this.hb.move(this.hb.x + 200 * Settings.scale, this.hb.y + 200.0F * Settings.scale);// 68

        if (AbstractDungeon.ascensionLevel >= 4) {// 68
            this.damage.add(new DamageInfo(this, 45));// 69
            this.damage.add(new DamageInfo(this, 2));// 70
            this.bloodHitCount = 15;// 71
        } else {
            this.damage.add(new DamageInfo(this, 40));// 73
            this.damage.add(new DamageInfo(this, 2));// 74
            this.bloodHitCount = 12;// 75
        }


    }// 77

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();// 81
        AbstractDungeon.scene.fadeOutAmbiance();// 82
        getCurrRoom().playBgmInstantly("BOSS_ENDING");// 83

        this.addToBot(new ApplyPowerAction(this, this, new Untargetable(this,this,-1)));// 85
    }// 96

    public void takeTurn() {
        switch (nextMove){
            case 1:
                SpawnElite();
                break;
            default:
                break;
        }


    }// 247


    public void SpawnElite(){
        MonsterGroup monsters = MonsterHelper.getEncounter(DissArtPatches.getTrulyRandomEliteEncounter());// 100
        monsters.monsters.forEach((m) -> {// 101
            this.addToBot(new SpawnMonsterAction(m, false));// 102
            this.addToBot(new FixMonsterAction(m));// 103
            m.hb.translate(-200.0F * Settings.scale, 0);// 104
            m.drawX -= 200.0F * Settings.scale;// 105
        });
    }

    protected void getMove(int num) {
        if (this.isFirstMove) {// 251
            this.setMove((byte)1, Intent.MAGIC);// 252
            this.isFirstMove = false;// 253
        } else {
            this.setMove((byte)1, Intent.MAGIC);// 252

            ++this.moveCount;// 277
        }
    }// 254 278

    public void die() {
        if (!getCurrRoom().cannotLose) {// 282
            super.die();// 283
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                 m.escape();
            }

            this.onBossVictoryLogic();// 285
            this.onFinalBossVictoryLogic();// 286
            CardCrawlGame.stopClock = true;// 287
        }

    }// 289

    @Override
    public void escape() {


    }


    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(makeID(ID));// 39
        NAME = monsterStrings.NAME;// 40
        MOVES = monsterStrings.MOVES;// 41
        DIALOG = monsterStrings.DIALOG;// 42
    }

    public void onMonsterDeath(AbstractMonster instance) {
        this.addToBot(new MakeTempCardInDrawPileAction(new GlowingShard(), 1, true, true));// 291
    }
}
