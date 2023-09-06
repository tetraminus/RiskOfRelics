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
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.actions.ApplyBlockAllEnemies;
import riskOfRelics.actions.FixMonsterAction;
import riskOfRelics.cards.colorless.GlowingShard;
import riskOfRelics.patches.DissArtPatches;
import riskOfRelics.powers.Untargetable;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom;
import static riskOfRelics.RiskOfRelics.makeID;

public class BulwarksAmbry extends AbstractMonster implements AnimationController.AnimationListener {
    public static final String ID = makeID("BulwarksAmbry");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;


    private boolean isFirstMove = true;
    private int moveCount = 0;


    private ModelBatch mb;
    private ModelInstance modelInstance;
    private Environment environment;
    private AnimationController controller;

    private PointLight pointLight;
    private int ProtectBlock = 15;


    private FrameBuffer fbo;

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
            this.damage.add(new DamageInfo(this, 15));// 69
            ProtectBlock = 20;


        } else {
            this.damage.add(new DamageInfo(this, 15));// 73

        }
        SetupModel();


    }// 77

    private void SetupModel(){
        mb = new ModelBatch();
        modelInstance = new ModelInstance(new G3dModelLoader(new JsonReader()).loadModel(Gdx.files.internal("riskOfRelicsResources/models/ambry.g3dj")));

        modelInstance.transform.scale(.8f*Settings.scale,.8f*Settings.scale,.8f*Settings.scale);

        controller = new AnimationController(modelInstance);

        onLoop(null);
        //controller.action("Armature|bounce", -1,0.5f ,this,0);

        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, true);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        //modelInstance.materials.get(2).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 1f));

        cam = new OrthographicCamera(Settings.WIDTH/100f, Settings.HEIGHT/100f);
        cam.position.set(0, 0, 5);
        cam.lookAt(0, 0, 0);
        cam.near = 0.1f;
        cam.far = 10f;

        cam.update();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        environment.add(pointLight = new PointLight().set(1f, 0.5f, 1f, 0, 0, 0, 5f));


    }

    @Override
    public void dispose() {


        super.dispose();
        mb.dispose();

    }

    @Override
    public void update() {



        Vector3 screenpos = new Vector3(hb.cX, (float) (hb.cY - hb_h*Settings.scale), 0);// 878

        controller.update(Gdx.graphics.getDeltaTime());



        modelInstance.transform.setTranslation(cam.unproject(screenpos).add(0, 0, -cam.position.z));// 880
        pointLight.position.set(modelInstance.transform.getTranslation(Vector3.Zero).cpy());// 880

        super.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!this.isDead && !this.escaped) {// 862



                sb.end();// 886


                fbo.begin();// 887
                Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                mb.begin(cam);// 887
                mb.render(modelInstance,environment);// 888
                mb.end();// 889
                fbo.end();// 889
                sb.begin();// 890
                sb.setColor(Color.WHITE);
                sb.draw(fbo.getColorBufferTexture(), 0, 0, Settings.WIDTH, Settings.HEIGHT, 0, 0, fbo.getWidth(), fbo.getHeight(), false, true);// 890



                sb.setBlendFunction(770, 771);// 891



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
            case 2:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));// 247
                break;
            case 3:
                this.addToBot(new ApplyBlockAllEnemies(this, ProtectBlock));
            default:
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));// 246


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
            moveCount++;
        } else {
            if (isLastAlive()){
                this.setMove((byte)1, Intent.MAGIC);// 252
            }
            else{
                moveCount++;
                switch (moveCount %2){
                    case 0:
                        this.setMove((byte)2, Intent.ATTACK, this.damage.get(0).base);// 252
                        break;
                    case 1:
                        this.setMove((byte)3, Intent.BUFF);// 252
                        break;
                }

            }

        }
    }// 254 278

    public boolean isLastAlive(){
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m != this) {
                return false;
            }
        }
        return true;
    }

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

    @Override
    public void onEnd(AnimationController.AnimationDesc animationDesc) {

    }

    @Override
    public void onLoop(AnimationController.AnimationDesc animationDesc) {
        switch ((int) Math.ceil(currentHealth/250f)){
            case 5:
                controller.setAnimation("Armature|onFull", -1,0.5f ,this );
                break;
            case 4:
                controller.setAnimation("Armature|on3", -1,0.5f ,this );
                break;
            case 3:
                controller.setAnimation("Armature|on2", -1,0.5f ,this );
                break;
            case 2:
                controller.setAnimation("Armature|on1", -1,0.5f ,this );
                break;
            case 1:
                controller.setAnimation("Armature|off", -1,0.5f ,this );
                break;
        }


    }
}
