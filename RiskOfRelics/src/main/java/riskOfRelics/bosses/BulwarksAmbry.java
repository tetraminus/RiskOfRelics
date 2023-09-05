//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package riskOfRelics.bosses;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
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
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.lwjgl.opengl.GLContext;
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

    private ModelBatch mb;
    private ModelInstance modelInstance;
    private Environment environment;
    private AnimationController controller;

    private Camera cam;

    public BulwarksAmbry() {
        super(NAME, ID, 750, 30.0F, -30.0F, 476.0F, 410.0F, (String)null, -50.0F, 30.0F);// 54
        //
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
        SetupModel();


    }// 77

    private void SetupModel(){
        mb = new ModelBatch();
        modelInstance = new ModelInstance(new G3dModelLoader(new JsonReader()).loadModel(Gdx.files.internal("riskOfRelicsResources/models/ambry.g3dj")));

        modelInstance.transform.scale(100f,100f,100f);
        modelInstance.animations.get(0).id = "idle";

        controller = new AnimationController(modelInstance);
        controller.setAnimation("idle", -1);




        cam = new OrthographicCamera(Settings.WIDTH, Settings.HEIGHT);
        cam.position.set(0, 0, 200);
        cam.lookAt(0, 0, 0);
        cam.near = 0.1f;
        cam.far = 1000f;




        cam.update();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


    }

    @Override
    public void dispose() {
        mb.dispose();
        modelInstance.model.dispose();

        super.dispose();
    }

    @Override
    public void update() {

        controller.update(Gdx.graphics.getDeltaTime());

        Vector3 screenpos = new Vector3(hb.cX, (float) (hb.cY-(hb.height/1.5)), 0);// 878

        //screenpos.y += hb.height/2 * Settings.scale;// 879

        //screenpos = screenpos.add(Gdx.graphics.getWidth() / 2.0F, Gdx.graphics.getHeight() / 2.0F, 0.0F);// 879
        modelInstance.transform.setTranslation(cam.unproject(screenpos).add(0, 0, -cam.position.z));// 880

        super.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!this.isDead && !this.escaped) {// 862



                sb.end();// 886
                mb.begin(cam);// 887
                mb.render(modelInstance,environment);// 888
                mb.end();// 889
                sb.begin();// 890

                sb.setBlendFunction(770, 771);// 891


            if (this == AbstractDungeon.getCurrRoom().monsters.hoveredMonster && this.atlas == null) {// 894 895
                sb.setBlendFunction(770, 1);// 896
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));// 897
                if (this.img != null) {// 898
                    sb.draw(this.img, this.drawX - (float)this.img.getWidth() * Settings.scale / 2.0F + this.animX, this.drawY + this.animY, (float)this.img.getWidth() * Settings.scale, (float)this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);// 899 901 903 904 907 908
                    sb.setBlendFunction(770, 771);// 911
                }
            }

            if (!this.isDying && !this.isEscaping && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.player.isDead && !AbstractDungeon.player.hasRelic("Runic Dome") && this.intent != AbstractMonster.Intent.NONE && !Settings.hideCombatElements) {// 916 918

                ReflectionHacks.privateMethod(AbstractMonster.class, "renderIntentVfxBehind", SpriteBatch.class).invoke(this, sb);// 924
                ReflectionHacks.privateMethod(AbstractMonster.class, "renderIntent",SpriteBatch.class).invoke(this, sb);// 925
                ReflectionHacks.privateMethod(AbstractMonster.class, "renderIntentVfxAfter",SpriteBatch.class).invoke(this, sb);// 926
                ReflectionHacks.privateMethod(AbstractMonster.class, "renderDamageRange",SpriteBatch.class).invoke(this, sb);// 927


            }

            this.hb.render(sb);// 927
            this.intentHb.render(sb);// 928
            this.healthHb.render(sb);// 929
        }

        if (!AbstractDungeon.player.isDead) {// 932
            this.renderHealth(sb);// 933


            ReflectionHacks.privateMethod(AbstractMonster.class, "renderName",SpriteBatch.class).invoke(this, sb);// 935
        }
    }

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
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);// 39
        NAME = monsterStrings.NAME;// 40
        MOVES = monsterStrings.MOVES;// 41
        DIALOG = monsterStrings.DIALOG;// 42
    }

    public void onMonsterDeath(AbstractMonster instance) {
        this.addToBot(new MakeTempCardInDrawPileAction(new GlowingShard(), 1, true, true));// 291
    }
}
