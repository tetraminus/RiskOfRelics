package riskOfRelics.chests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import org.apache.logging.log4j.Level;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.patches.scrap.ScrapField;
import riskOfRelics.util.ScrapInfo;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static riskOfRelics.RiskOfRelics.Hack3dEnabled;

public class Printer extends AbstractChest {
    public static final int COMMON_SCRAP_COST = 3;
    public static final int UNCOMMON_SCRAP_COST = 2;
    public static final int RARE_SCRAP_COST = 1;
    private AbstractRelic relicToPrint;

    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("riskOfRelics:Printer");
    private FrameBuffer fbo;

    private Camera cam;
    private ModelBatch mb;
    private ModelInstance modelInstance;
    private Environment environment;
    private AnimationController controller;
    public Printer() {
        super();
        this.img = ImageMaster.BOSS_CHEST;// 25
        this.openedImg = ImageMaster.BOSS_CHEST_OPEN;// 26
        this.hb = new Hitbox(256.0F * Settings.scale, 200.0F * Settings.scale);// 28
        this.hb.move(CHEST_LOC_X, CHEST_LOC_Y - 100.0F * Settings.scale);// 29
        AbstractRelic.RelicTier tier;
        do {
            tier = AbstractDungeon.returnRandomRelicTier();
        } while (!tierAvailable(tier));

        relicToPrint = AbstractDungeon.returnRandomRelic(tier);
        relicToPrint.currentX = CHEST_LOC_X;
        relicToPrint.currentY = CHEST_LOC_Y;
        relicToPrint.hb.move(CHEST_LOC_X, CHEST_LOC_Y);
        relicToPrint.hb.update();
        SetupGraphics();
        

    }



    public static boolean canSpawn(){
        if (tierAvailable(AbstractRelic.RelicTier.COMMON) || canPayWithScrap(AbstractRelic.RelicTier.COMMON)){
            return true;
        }
        if (tierAvailable(AbstractRelic.RelicTier.UNCOMMON) || canPayWithScrap(AbstractRelic.RelicTier.UNCOMMON)){
            return true;
        }
        if (tierAvailable(AbstractRelic.RelicTier.RARE) || canPayWithScrap(AbstractRelic.RelicTier.RARE)){
            return true;
        }

        return false;
    }

    private static boolean tierAvailable(AbstractRelic.RelicTier tier){
        for (AbstractRelic r : player.relics) {
            if (r.tier == tier) {
                return true;
            }
        }
        ScrapInfo info = ScrapField.scrapFieldPatch.scrapInfo.get(player);
        switch (tier) {
            case COMMON:
                return info.commonScrap >= 3;
            case UNCOMMON:
                return info.uncommonScrap >= 3;
            case RARE:
                return info.rareScrap >= 2;
            default:
                return false;
        }
    }


    public void render(SpriteBatch sb) {
        if (Hack3dEnabled) {
            try {
                Render3D(sb);
            } catch (NullPointerException e) {
                Render2D(sb);
            }
        } else {
            Render2D(sb);
        }

        if (Settings.isControllerMode && !this.isOpen) {// 224
            sb.setColor(Color.WHITE);// 225
            sb.draw(CInputActionSet.select.getKeyImg(), CHEST_LOC_X - 32.0F - 150.0F * Settings.scale, CHEST_LOC_Y - 32.0F - 210.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);// 226 227
        }
        sb.setColor(Color.WHITE);// 154
        if (this.relicToPrint != null) {
            relicToPrint.renderWithoutAmount(sb, new Color(0.0F, 0.0F, 0.0F, 0.25F));
        }

        this.hb.render(sb);// 245
    }// 246

    private void Render2D(SpriteBatch sb) {
        sb.begin();
        sb.setColor(Color.WHITE);// 154


        float angle = 0.0F;// 155
        if (this.isOpen && this.openedImg == null) {// 157
            angle = 180.0F;// 158
        }

        float xxx = (float) Settings.WIDTH / 2.0F + 348.0F * Settings.scale;// 161
        if (this.isOpen && angle != 180.0F) {// 163
            sb.draw(this.openedImg, xxx - 256.0F, CHEST_LOC_Y - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, angle, 0, 0, 512, 512, false, false);// 205
        } else {
            sb.draw(this.img, CHEST_LOC_X - 256.0F, CHEST_LOC_Y - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, angle, 0, 0, 512, 512, false, false);// 164
            if (this.hb.hovered) {// 182
                  sb.setBlendFunction(770, 1);// 183
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));// 184
                sb.draw(this.img, CHEST_LOC_X - 256.0F, CHEST_LOC_Y - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, angle, 0, 0, 512, 512, false, false);// 185
                sb.setBlendFunction(770, 771);// 202
            }
        }
    }
    private void SetupGraphics(){
        if (Hack3dEnabled) {

            RiskOfRelics.logger.warn("3d Hack enabled, if you crash when fighting a certain boss, turn this off.");
            RiskOfRelics.logger.log(Level.DEBUG, "SetupModel1");
            mb = RiskOfRelics.mb;
            modelInstance = new ModelInstance(new G3dModelLoader(new JsonReader()).loadModel(Gdx.files.internal("riskOfRelicsResources/models/Printer.g3dj")));

            modelInstance.transform.scale(.8f * Settings.scale, .8f * Settings.scale, .8f * Settings.scale);
            modelInstance.transform.rotate(1, 0, 0, -10);
            modelInstance.transform.rotate(0, 1, 0, -5);
            //modelInstance.materials.get(2).set(new BlendingAttribute());
            controller = new AnimationController(modelInstance);

            controller.setAnimation("Armature|Armature|DuplicatorArmature|Idle|Base Layer", -1);

            fbo = RiskOfRelics.fbo;

            cam = RiskOfRelics.cam;

            cam.update();

            environment = new Environment();
            environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));
            //environment.add(new DirectionalLight().set(1f, 0.5f, 1f, -0.3f, -0.3f, -1f));

            //RiskOfRelics.logger.log(Level.DEBUG, "SetupModel3");
        }
        //this.img = ImageMaster.loadImage("riskOfRelicsResources/models/textures/Fallback.png");// 81
    }

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
        sb.setColor(Color.WHITE);

        sb.draw(fbo.getColorBufferTexture(), 0, 0, Settings.WIDTH, Settings.HEIGHT, 0, 0, fbo.getWidth(), fbo.getHeight(), false, false);// 890
        sb.setBlendFunction(770, 771);// 891
    }

    private String GetTipDesc() {
        String desc ="";
        desc += uiStrings.TEXT[1];
        if (canPayWithScrap(relicToPrint)) {

            switch (relicToPrint.tier) {
                case COMMON:
                    desc += COMMON_SCRAP_COST;
                    desc += uiStrings.TEXT[6];
                    break;
                case UNCOMMON:
                    desc += UNCOMMON_SCRAP_COST;
                    desc += uiStrings.TEXT[7];
                    break;
                case RARE:
                    desc += RARE_SCRAP_COST;
                    desc += uiStrings.TEXT[8];
                    break;
            }

            desc += uiStrings.TEXT[3];
        }else {
            desc += 1;
            switch (relicToPrint.tier) {

                case COMMON:
                    desc += uiStrings.TEXT[6];
                    break;
                case UNCOMMON:
                    desc += uiStrings.TEXT[7];
                    break;
                case RARE:
                    desc += uiStrings.TEXT[8];
                    break;
            }
            desc += uiStrings.TEXT[2];
        }
        desc += uiStrings.TEXT[4];
        desc += relicToPrint.name;
        desc += uiStrings.TEXT[5];



        return desc;

    }

    private String GetTipName() {
        return uiStrings.TEXT[0];
    }

    @Override
    public void open(boolean bossChest) {

        AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);// 79

        CardCrawlGame.sound.play("CHEST_OPEN");// 83

        if ( relicToPrint != null && tryToPay(relicToPrint)) {

            this.isOpen = true;// 85

            controller.action("Armature|Armature|DuplicatorArmature|IdleToOpenToIdle|Base Layer", 1, 1, new AnimationController.AnimationListener() {
                @Override
                public void onEnd(AnimationController.AnimationDesc animation) {
                    //controller.setAnimation("Armature|Armature|DuplicatorArmature|Idle|Base Layer", -1);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(CHEST_LOC_X,CHEST_LOC_Y, relicToPrint);// 87
                    relicToPrint = null;
                }

                @Override
                public void onLoop(AnimationController.AnimationDesc animation) {

                }
            }, 0.2f);

        }
        AbstractDungeon.overlayMenu.proceedButton.show();


    }

    private boolean tryToPay(AbstractRelic relicToPrint) {
        if (canPayWithScrap(relicToPrint) || canPayWithRelic(relicToPrint)) {
            payForPrint(relicToPrint);
            return true;
        }
        return false;
    }

    private boolean canPayWithRelic(AbstractRelic relicToPrint) {
        ArrayList<AbstractRelic> relics = (ArrayList<AbstractRelic>) player.relics.clone();
        relics.removeIf(r -> r.tier != relicToPrint.tier);
        return !relics.isEmpty();
    }

    private void payForPrint(AbstractRelic relicToPrint) {

        if (canPayWithScrap(relicToPrint)) {
            ScrapInfo info = ScrapField.scrapFieldPatch.scrapInfo.get(player);
            switch (relicToPrint.tier) {
                case COMMON:
                    info.commonScrap -= COMMON_SCRAP_COST;
                    break;
                case UNCOMMON:
                    info.uncommonScrap -= UNCOMMON_SCRAP_COST;
                    break;
                case RARE:
                    info.rareScrap -= RARE_SCRAP_COST;
                    break;
            }
        }else {
            ArrayList<AbstractRelic> relics = (ArrayList<AbstractRelic>) player.relics.clone();


            relics.removeIf(r -> r.tier != relicToPrint.tier);

            if (!relics.isEmpty()) {
                player.loseRelic(relics.get(AbstractDungeon.miscRng.random(relics.size() - 1)).relicId);
            }
        }
    }


    private boolean canPayWithScrap(AbstractRelic relic) {

        ScrapInfo info = ScrapField.scrapFieldPatch.scrapInfo.get(player);

        switch (relic.tier) {
            case COMMON:
                return info.commonScrap >= 3;
            case UNCOMMON:
                return info.uncommonScrap >= 3;
            case RARE:
                return info.rareScrap >= 2;
            default:
                return false;
        }

    }
    private static boolean canPayWithScrap(AbstractRelic.RelicTier tier) {

        ScrapInfo info = ScrapField.scrapFieldPatch.scrapInfo.get(player);

        switch (tier) {
            case COMMON:
                return info.commonScrap >= 3;
            case UNCOMMON:
                return info.uncommonScrap >= 3;
            case RARE:
                return info.rareScrap >= 2;
            default:
                return false;
        }

    }

    @Override
    public void update() {
            Vector3 screenpos = new Vector3(CHEST_LOC_X, (float) (CHEST_LOC_Y-125*Settings.scale), 0);// 878
            //Vector3 screenpos = new Vector3(InputHelper.mX, InputHelper.mY, 0);// 878
            //modelInstance.transform.rotate(0, 1, 0, Gdx.graphics.getDeltaTime() *5);

            if (Hack3dEnabled) {
                if (controller != null){
                    controller.update(Gdx.graphics.getDeltaTime());
                }
                if (modelInstance != null){
                    modelInstance.transform.setTranslation(cam.unproject(screenpos).add(0, 0, -cam.position.z));// 880
                }

            }
            if (relicToPrint != null && hb.hovered){
                TipHelper.renderGenericTip((float)InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY + 180.0F * Settings.scale, GetTipName(), GetTipDesc());// 183

            }

        if (relicToPrint != null){
            relicToPrint.hb.update();
            relicToPrint.currentX = CHEST_LOC_X;
            relicToPrint.currentY = CHEST_LOC_Y;
            relicToPrint.hb.move(relicToPrint.currentX, relicToPrint.currentY);// 54
            relicToPrint.hb.update();// 57
            if (relicToPrint.hb.hovered) {// 58
                relicToPrint.scale = Settings.scale * 1.25F;// 63
            } else {
                relicToPrint.scale = MathHelper.scaleLerpSnap(relicToPrint.scale, Settings.scale);// 65
            }

            if (relicToPrint.hb.hovered && InputHelper.justClickedRight) {// 68
                CardCrawlGame.relicPopup.open(relicToPrint);// 70
            }
        }

        super.update();
    }
}
