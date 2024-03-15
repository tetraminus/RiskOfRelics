//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package riskOfRelics.bosses;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.esotericsoftware.spine.Skeleton;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.MoveNameEffect;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;
import org.apache.logging.log4j.Level;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.actions.ApplyBlockAllEnemiesAction;
import riskOfRelics.actions.FixMonsterAction;
import riskOfRelics.actions.loseRandomRelicAction;
import riskOfRelics.cards.colorless.GlowingShard;
import riskOfRelics.powers.Untargetable;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static riskOfRelics.RiskOfRelics.Hack3dEnabled;
import static riskOfRelics.RiskOfRelics.makeID;

public class BulwarksAmbry extends AbstractMonster implements AnimationController.AnimationListener {
    public static final String ID = makeID("BulwarksAmbry");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public static ArrayList<String> Act1Elites;
    public static ArrayList<String> Act2Elites;
    public static ArrayList<String> Act3Elites;



    private boolean isFirstMove = true;
    private int moveCount = 0;


    private ModelBatch mb;
    private ModelInstance modelInstance;
    private Environment environment;
    private AnimationController controller;

    private PointLight pointLight;
    private int ProtectBlock = 15;
    private int BUFFSTRAMOUNT = 2;
    private int relicsToLose = 2;
    private final float AnimSpeed = 0.5f;

    private int elitesSpawned = 0;


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
            this.damage.add(new DamageInfo(this, 35));// 69
            ProtectBlock = 30;
            BUFFSTRAMOUNT = 5;
            relicsToLose = 3;



        } else {
            this.damage.add(new DamageInfo(this, 25));// 73
            ProtectBlock = 25;
            BUFFSTRAMOUNT = 3;
            relicsToLose = 2;


        }
        SetupGraphics();
        dialogY = -99999f;

    }// 77

    public static void addEliteEncounterID(String dungeonID, String id) {
        switch (dungeonID) {
            case Exordium.ID: // 81
                Act1Elites.add(id);// 82

                break;
            case TheCity.ID: // 83
                Act2Elites.add(id);// 84

                break;
            case TheBeyond.ID: // 85
                Act3Elites.add(id);// 86

                break;
        }
    }

    private void ChooseFirstAnim() {
            switch ((int) Math.ceil(currentHealth/250f)){
                case 5:
                    controller.setAnimation("Armature|onFull", -1,AnimSpeed ,this );
                    break;
                case 4:
                    controller.setAnimation("Armature|on3", -1,AnimSpeed ,this );
                    break;
                case 3:
                    controller.setAnimation("Armature|on2", -1,AnimSpeed ,this );
                    break;
                case 2:
                    controller.setAnimation("Armature|on1", -1,AnimSpeed ,this );
                    break;
                case 1:
                    controller.setAnimation("Armature|off", -1,AnimSpeed ,this );
                    break;
            }
    }

    private void SetupGraphics(){

        if (Hack3dEnabled) {


            RiskOfRelics.logger.warn("3d Hack enabled, if you crash when fighting a certain boss, turn this off.");
            RiskOfRelics.logger.log(Level.DEBUG, "SetupModel1");
            mb = RiskOfRelics.mb;
            modelInstance = new ModelInstance(new G3dModelLoader(new JsonReader()).loadModel(Gdx.files.internal("riskOfRelicsResources/models/ambry.g3dj")));

            modelInstance.transform.scale(.8f * Settings.scale, .8f * Settings.scale, .8f * Settings.scale);
            //modelInstance.materials.get(2).set(new BlendingAttribute());
            controller = new AnimationController(modelInstance);




            ChooseFirstAnim();
            //controller.action("Armature|bounce", -1,0.5f ,this,0);

            fbo = RiskOfRelics.fbo;


            modelInstance.materials.get(2).set(new BlendingAttribute());

            cam = RiskOfRelics.cam;

            cam.update();

            environment = new Environment();
            environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
            environment.add(new DirectionalLight().set(1f, 0.5f, 1f, -0.3f, -0.3f, -1f));
            pointLight = new PointLight().set(1f, 0.5f, 1f, 0, 0, 0, 20f);
            environment.add(pointLight);

            //RiskOfRelics.logger.log(Level.DEBUG, "SetupModel3");
        }
        this.img = ImageMaster.loadImage("riskOfRelicsResources/models/textures/Fallback.png");// 81
    }



    @Override
    public void update() {


        Vector3 screenpos = new Vector3(drawX+32*Settings.scale, (float) ((drawY-32*Settings.scale) + hb_h/2), 0);// 878
        Vector3 lightpos = screenpos.add(0, 0, -0.05f);// 879



        if (Hack3dEnabled) {
            if (controller != null){
                controller.update(Gdx.graphics.getDeltaTime());
            }
            if (modelInstance != null){
                modelInstance.transform.setTranslation(cam.unproject(screenpos).add(0, 0, -cam.position.z));// 880
                if (pointLight != null){
                    pointLight.setPosition(cam.unproject(lightpos));// 880)

                }
            }

        }


        super.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!this.isDead && !this.escaped) {// 862
            sb.setColor(Color.WHITE);
            if (Hack3dEnabled){
                try {
                    Render3D(sb);
                } catch (NullPointerException e ){
                    sb.begin();
                    sb.draw(this.img, this.drawX -hb_w/3, this.drawY + this.animY, (float)this.hb.width, (float)this.hb.height, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);// 866 868 870 871 874 875
                }

            } else {
                sb.draw(this.img, this.drawX - hb_w /3, this.drawY + this.animY, (float)this.hb.width, (float)this.hb.height, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);// 866 868 870 871 874 875
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
    public void refreshIntentHbLocation() {
        float INTENT_HB_W = 64.0F * Settings.scale;// 210
        this.intentHb.move(this.hb.cX + this.intentOffsetX - this.hb_w / 2.0F, this.hb.cY );// 212
    }// 213


    private void Render3D(SpriteBatch sb) {
        sb.end();// 886
        fbo.begin();// 887
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        mb.begin(cam);// 887
        mb.render(modelInstance,environment);// 888
        mb.end();// 889
        fbo.end();// 889
        sb.begin();// 890

        sb.draw(fbo.getColorBufferTexture(), 0, 0, Settings.WIDTH, Settings.HEIGHT, 0, 0, fbo.getWidth(), fbo.getHeight(), false, false);// 890
        sb.setBlendFunction(770, 771);// 891
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();// 81
        AbstractDungeon.scene.fadeOutAmbiance();// 82
//        ArrayList<TempMusic> musics =  ReflectionHacks.getPrivate(CardCrawlGame.music, MusicMaster.class, "tempTrack");
//        BossMusic music = new BossMusic(this);
//        music.silenceInstantly();
//        musics.add(music);
//        ReflectionHacks.setPrivate(CardCrawlGame.music, MusicMaster.class, "tempTrack", musics);// 8
        CardCrawlGame.music.playTempBgmInstantly(makeID("AMBRY_BOSS"));// 83
        //BaseMod.addAudio();
        this.addToBot(new ApplyPowerAction(this, this, new Untargetable(this,this,-1)));// 85
    }// 96

    public void takeTurn() {
        switch (nextMove){
            case 1:
                // elite spawn
                SpawnElite();
                break;
            case 2:
                // attack
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));// 247
                this.addToBot(new loseRandomRelicAction(2));// 291
                break;
            case 3:
                // buff
                this.addToBot(new ApplyBlockAllEnemiesAction(this, ProtectBlock));
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDeadOrEscaped()) {
                        this.addToBot(new ApplyPowerAction(m, this, new StrengthPower(m,BUFFSTRAMOUNT)));// 85
                    }

                }


            default:
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));// 246


    }// 247


    public void SpawnElite(){
        String eliteID;
        switch (elitesSpawned){
            // pattern: 1 1 2 3 4
            case 0:

                eliteID = Act1Elites.get(AbstractDungeon.aiRng.random(Act1Elites.size() - 1));
                break;
            case 1:
            case 2:
                eliteID = Act2Elites.get(AbstractDungeon.aiRng.random(Act2Elites.size() - 1));
                break;
            case 3:
                eliteID = Act3Elites.get(AbstractDungeon.aiRng.random(Act3Elites.size() - 1));
                break;
            default:
                eliteID = "Shield and Spear";
                break;
        }

//        if (eliteID.equals("Shield and Spear")){
//            player.movePosition((Settings.WIDTH / 2.0F) - 200.0F * Settings.scale,AbstractDungeon.floorY);
//        } else {
//           // player.movePosition((Settings.WIDTH / 4.0F),AbstractDungeon.floorY);
//        }



        MonsterGroup monsters = MonsterHelper.getEncounter(eliteID);// 100
        monsters.monsters.forEach((m) -> {// 101
            this.addToBot(new SpawnMonsterAction(m, false));// 102
            this.addToBot(new FixMonsterAction(m));// 103
            m.drawX = Settings.WIDTH - m.hb_w/2;
            m.updateAnimations();
            Skeleton skel = ReflectionHacks.<Skeleton>getPrivate(m, AbstractCreature.class, "skeleton");

            if (skel != null){
                skel.getRootBone().setScale(skel.getRootBone().getScaleX() * 0.8f);
                skel.updateWorldTransform();
            }else {
                RiskOfRelics.logger.warn("Skeleton is null");
            }

            m.hb.width *= 0.8f;
            m.hb.height *= 0.8f;

            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    SetFreeSpawnPoint(m);

                    if (m.drawX < 0){
                        scaleSpawnsToFit();
                    }

                    isDone = true;
                }
            });
            addToBot(new ApplyPowerAction(m, this, new StrengthPower(m, 2)));
        });


        this.elitesSpawned++;
    }

    public void scaleSpawnsToFit(){
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m == this){
                continue;
            }
            Skeleton skel = ReflectionHacks.<Skeleton>getPrivate(m, AbstractCreature.class, "skeleton");

            if (skel != null){
                skel.getRootBone().setScale(skel.getRootBone().getScaleX() * 0.8f);
                skel.updateWorldTransform();
            }else {
                RiskOfRelics.logger.warn("Skeleton is null");
            }


            //ReflectionHacks.<Skeleton>getPrivate(m, AbstractMonster.class, "skeleton").setScale(0.8f);
            m.hb.width *= 0.8f;
            m.hb.height *= 0.8f;

            m.drawX = Settings.WIDTH - m.hb_w/2;
            m.updateAnimations();

            SetFreeSpawnPoint(m);


        }
    }

    public void SetFreeSpawnPoint(AbstractCreature m){
        m.drawX -= 2;
        m.updateAnimations();
        if (checkOverlap(m)){
            if (m.drawX < 0){
                scaleSpawnsToFit();
                return;
            }
            SetFreeSpawnPoint(m);

        }
    }

    public boolean checkOverlap(AbstractCreature m){
        for (AbstractCreature c : AbstractDungeon.getCurrRoom().monsters.monsters) {

            if (c.hb.intersects(m.hb) && c != m && !c.isDeadOrEscaped() && c!= this) {
                return true;
            }


        }
        //on or past player
        if (m.hb.intersects(player.hb) || m.drawX < player.drawX){
            return true;
        }
        return false;
    }

    protected void getMove(int num) {
        if (this.isFirstMove) {// 251
            this.setMove(monsterStrings.MOVES[2],(byte)1, Intent.MAGIC);// 252
            this.isFirstMove = false;// 253
        } else {
            if (isLastAlive()){
                this.setMove(monsterStrings.MOVES[2],(byte)1, Intent.MAGIC);// 252
                if (moveCount %3 == 1){
                    moveCount++;
                }
            }
            else{
                moveCount++;
                switch (moveCount %3){
                    case 0:
                        this.setMove(monsterStrings.MOVES[0],(byte)2, Intent.ATTACK_DEBUFF, this.damage.get(0).base);// 252
                        break;
                    case 1:
                        this.setMove(monsterStrings.MOVES[1],(byte)3, Intent.DEFEND_BUFF);// 252
                        break;
                    case 2:
                        // if there are less than 3 other monsters left, spawn one\
                        int alive = 0;
                        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                            if (!m.isDeadOrEscaped() && m != this) {
                                alive++;
                            }
                        }
                        if (alive < 3){
                            this.setMove(monsterStrings.MOVES[2],(byte)1, Intent.MAGIC);// 252
                        } else {
                            this.setMove(monsterStrings.MOVES[0],(byte)2, Intent.ATTACK_DEBUFF, this.damage.get(0).base);// 252
                        }
                        break;
                }

            }

        }
    }// 254 278

    public boolean isLastAlive(){
        int alive = 0;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m != this) {
                alive++;
            }
        }
        return alive == 0;
    }

    public void die() {
        for (RiskOfRelics.Artifacts a:
                RiskOfRelics.ActiveArtifacts) {
            if (!RiskOfRelics.UnlockedArtifacts.contains(a)) {
                RiskOfRelics.UnlockedArtifacts.add(a);
            }
        }
        RiskOfRelics.saveData();
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

    @SpirePatch2(clz = ShowMoveNameAction.class, method = "update")
    public static class ShowMoveNameActionPatch {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(NewExpr expr) throws CannotCompileException {
                    if (expr.getClassName().equals(MoveNameEffect.class.getName())) {
                        expr.replace(
                                "{" +
                                    "if (source instanceof "+BulwarksAmbry.class.getName()+") {" +
                                        // MoveNameEffect(this.source.hb.cX - this.source.animX * 2, this.source.hb.cY, this.msg)
                                        //RiskOfRelics.class.getName()+".logger.info(\"BulwarksAmbry MoveNameEffect x:\" + ($1 - this.source.animX * 2) + \" y:\" + ($2 - this.source.hb.height / 2.0F) + \" msg:\" + $3);" +
                                        "$_ =  $proceed( $1 - this.source.animX * 2, $2 - this.source.hb.height / 2.0F, $3);" +

                                    "} else" +
                                    " {" +
                                            //MoveNameEffect(this.source.hb.cX - this.source.animX, this.source.hb.cY + this.source.hb.height / 2.0F, this.msg)
                                        "$_ = $proceed($$);" +
                                    "}" +
                                "}");
                    }
                }
            };
        }
    }

    public void onMonsterDeath(AbstractMonster instance) {

        if (instance == null || instance.hasPower(MinionPower.POWER_ID)) return;

        if (instance != this && ( instance.isDying || instance.isEscaping || instance.halfDead || instance.isDead) && isLastAlive() ) {// 295
            this.addToBot(new MakeTempCardInDrawPileAction(new GlowingShard(), 1, true, true));// 291


        }

        int alive = 0;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m != this) {
                alive++;
            }
        }
        if (alive < 2){
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    setMove((byte)1, Intent.MAGIC);
                    createIntent();
                    isDone = true;
                }
            });
        }
    }

    @Override
    public void onEnd(AnimationController.AnimationDesc animationDesc) {

    }



    @Override
    public void onLoop(AnimationController.AnimationDesc animationDesc) {
        if (controller == null){
            return;
        }
        if (controller.current == null){
            return;
        }
        switch ((int) Math.ceil(currentHealth/250f)){
            case 5:
                if (controller.current.animation.id.equals("Armature|onFull")){
                    return;
                }
                controller.setAnimation("Armature|onFull", -1,AnimSpeed ,this );
                break;
            case 4:
                if (controller.current.animation.id.equals("Armature|on3")){
                    return;
                }
                controller.setAnimation("Armature|on3", -1,AnimSpeed ,this );
                break;
            case 3:
                if (controller.current.animation.id.equals("Armature|on2")){
                    return;
                }
                controller.setAnimation("Armature|on2", -1,AnimSpeed ,this );
                break;
            case 2:
                if (controller.current.animation.id.equals("Armature|on1")){
                    return;
                }
                controller.setAnimation("Armature|on1", -1,AnimSpeed ,this );
                break;
            case 1:
                if (controller.current.animation.id.equals("Armature|off")){
                    return;
                }
                controller.setAnimation("Armature|off", -1,AnimSpeed ,this );
                break;
        }


  }

    @Override
    public void renderHealth(SpriteBatch sb) {
        if (!Settings.hideCombatElements) {// 1012
            float x = this.hb.cX - this.hb.width / 2.0F;// 1016
            float y = this.hb.cY + this.hb.height / 2.0F + ReflectionHacks.<Float>getPrivate(this, AbstractCreature.class, "hbYOffset");// 1017
            ReflectionHacks.privateMethod(AbstractCreature.class, "renderHealthBg",SpriteBatch.class, float.class, float.class).invoke(this, sb, x, y);// 1018
            if (ReflectionHacks.<Float>getPrivate(this, AbstractCreature.class, "targetHealthBarWidth") != 0.0F) {// 1019
                //this.renderOrangeHealthBar(sb, x, y);// 1022
                ReflectionHacks.privateMethod(AbstractCreature.class, "renderOrangeHealthBar",SpriteBatch.class, float.class, float.class).invoke(this, sb, x, y);// 1022
                if (this.hasPower("Poison")) {// 1023
                    //this.renderGreenHealthBar(sb, x, y);// 1024
                    ReflectionHacks.privateMethod(AbstractCreature.class, "renderGreenHealthBar",SpriteBatch.class, float.class, float.class).invoke(this, sb, x, y);// 1024
                }

                //this.renderRedHealthBar(sb, x, y);// 1026
                ReflectionHacks.privateMethod(AbstractCreature.class, "renderRedHealthBar",SpriteBatch.class, float.class, float.class).invoke(this, sb, x, y);// 1026
            }

            if (this.currentBlock != 0 && this.hbAlpha != 0.0F) {// 1030
                //this.renderBlockOutline(sb, x, y);// 1031
                ReflectionHacks.privateMethod(AbstractCreature.class, "renderBlockOutline",SpriteBatch.class, float.class, float.class).invoke(this, sb, x, y);// 1031
            }

            //this.renderHealthText(sb, y);// 1034
            ReflectionHacks.privateMethod(AbstractCreature.class, "renderHealthText",SpriteBatch.class, float.class).invoke(this, sb, y);// 1034
            if (this.currentBlock != 0 && this.hbAlpha != 0.0F) {// 1037
                //this.renderBlockIconAndValue(sb, x, y);// 1038
                ReflectionHacks.privateMethod(AbstractCreature.class, "renderBlockIconAndValue",SpriteBatch.class, float.class, float.class).invoke(this, sb, x, y);// 1038
            }

            //this.renderPowerIcons(sb, x, y);// 1041
            ReflectionHacks.privateMethod(AbstractCreature.class, "renderPowerIcons",SpriteBatch.class, float.class, float.class).invoke(this, sb, x, y);// 1041
        }
    }

    static {


        Act1Elites = new ArrayList<String>() {
            {
                this.add("Gremlin Nob");// 182
                this.add("Lagavulin");// 183
                this.add("3 Sentries");// 184
            }
        };
        Act2Elites = new ArrayList<String>() {
            {
                this.add("Gremlin Leader");// 188
                this.add("Book of Stabbing");// 189
                this.add("Slavers");// 190
                this.add("Snecko and Mystics");// 191
            }
        };
        Act3Elites = new ArrayList<String>() {
            {
                this.add("Giant Head");// 194
                this.add("Nemesis");// 195
                this.add("Reptomancer");// 196
            }
        };


  }
}
