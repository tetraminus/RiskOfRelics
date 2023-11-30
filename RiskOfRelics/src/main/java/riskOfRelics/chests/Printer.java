package riskOfRelics.chests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
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
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.Level;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.patches.scrap.ScrapField;
import riskOfRelics.util.ScrapInfo;

import java.util.ArrayList;
import java.util.Objects;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static riskOfRelics.RiskOfRelics.Hack3dEnabled;
import static riskOfRelics.RiskOfRelics.makeID;

public class Printer extends AbstractChest {
    public static final int COMMON_SCRAP_COST = 1;
    public static final int UNCOMMON_SCRAP_COST = 1;
    public static final int RARE_SCRAP_COST = 1;
    private AbstractRelic relicToPrint;

    private boolean freeUse = true;

    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("riskOfRelics:Printer");

    public static final Texture freeImg = ImageMaster.loadImage("riskOfRelicsResources/images/ui/Printer/FreeIcon.png");// 25
    private FrameBuffer fbo;
    private ArrayList <AbstractRelic> possibleRelics;

    private Camera cam;
    private ModelBatch mb;
    private ModelInstance modelInstance;
    private Environment environment;
    private AnimationController controller;
    private AbstractRelic relicToRemove;
    private static final float OFFSET_X;
    private static final float OFFSET_Y;
    private static final int MAX_POSSIBLE_RELICS = 3;

    private static final float DISPLAY_OFFSET_X;
    private static final float DISPLAY_OFFSET_Y;
    private boolean justPaidWithScrap;

    public Printer() {
        super();
        this.img = ImageMaster.loadImage("riskOfRelicsResources/images/2DPrinter_1.png");// 25
        this.openedImg = ImageMaster.loadImage("riskOfRelicsResources/images/2DPrinter_2.png");// 26
        this.hb = new Hitbox(256.0F * Settings.scale, 200.0F * Settings.scale);// 28
        this.hb.move(CHEST_LOC_X, CHEST_LOC_Y - 100.0F * Settings.scale);// 29
        SetRelicToPrint();


        SetupGraphics();

        

    }

    private void SetRelicToPrint() {
        AbstractRelic.RelicTier tier;
        do {
            tier = AbstractDungeon.returnRandomRelicTier();
        } while (!tierAvailable(tier) && !freeUse);

        relicToPrint = AbstractDungeon.returnRandomRelic(tier);
        relicToPrint.currentX = CHEST_LOC_X;
        relicToPrint.currentY = CHEST_LOC_Y;
        relicToPrint.hb.move(CHEST_LOC_X, CHEST_LOC_Y);
        relicToPrint.hb.update();

        possibleRelics = new ArrayList<>();
        for (AbstractRelic r : player.relics) {
            if (r.tier == relicToPrint.tier) {
                possibleRelics.add(r);
            }
        }

        while (possibleRelics.size() > MAX_POSSIBLE_RELICS){
            possibleRelics.remove(AbstractDungeon.miscRng.random(possibleRelics.size()-1));
        }
        justPaidWithScrap = false;
        isOpen = false;
    }


    @SuppressWarnings("unused") //Used in patch
    public static boolean canSpawn(){
        if (Objects.equals(AbstractDungeon.getCurrRoom().getMapSymbol(), "T")){
            return false;
        }
        if (!RiskOfRelics.PrintersEnabled ||(AbstractDungeon.actNum == 3 && !Settings.hasSapphireKey)){
            return false;
        }

        if (tierAvailable(AbstractRelic.RelicTier.COMMON) || canPayWithScrap(AbstractRelic.RelicTier.COMMON)){
            return true;
        }
        if (tierAvailable(AbstractRelic.RelicTier.UNCOMMON) || canPayWithScrap(AbstractRelic.RelicTier.UNCOMMON)){
            return true;
        }
        return tierAvailable(AbstractRelic.RelicTier.RARE) || canPayWithScrap(AbstractRelic.RelicTier.RARE);
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
                return info.commonScrap >= COMMON_SCRAP_COST;
            case UNCOMMON:
                return info.uncommonScrap >= UNCOMMON_SCRAP_COST;
            case RARE:
                return info.rareScrap >= RARE_SCRAP_COST;
            default:
                return false;
        }
    }


    public void render(SpriteBatch sb) {


        if (Hack3dEnabled) {
            try {
                Render3D(sb);
            } catch (NullPointerException e) {
                RiskOfRelics.logger.log(Level.WARN, "3d Render failed, falling back to 2d");
                sb.begin();
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
        sb.flush();

        Rectangle scissors = new Rectangle();
        Rectangle clipBounds = new Rectangle(0,CHEST_LOC_Y-85*Settings.scale,Settings.WIDTH,Settings.HEIGHT-CHEST_LOC_Y-85*Settings.scale);
        ScissorStack.calculateScissors(CardCrawlGame.viewport.getCamera(), sb.getTransformMatrix(), clipBounds, scissors);
        if (ScissorStack.pushScissors(scissors)) {
            if (this.relicToRemove != null) {
                relicToRemove.renderWithoutAmount(sb, new Color(0.0F, 0.0F, 0.0F, 0.25F));
            }
            if (this.relicToPrint != null) {
                relicToPrint.renderWithoutAmount(sb, new Color(0.0F, 0.0F, 0.0F, 0.25F));
            }

            sb.flush();
            ScissorStack.popScissors();
        }

        if (freeUse){
            float scale = .25f;
            Vector2 offset = new Vector2((freeImg.getWidth()*scale * Settings.scale)/2,(freeImg.getHeight()*scale * Settings.scale)/2);
            offset.y += 100*Settings.scale;
            sb.draw(freeImg,CHEST_LOC_X - offset.x, CHEST_LOC_Y - offset.y, freeImg.getWidth()*scale * Settings.scale, freeImg.getHeight()*scale * Settings.scale);
        }


        this.hb.render(sb);// 245
    }// 246

    private void Render2D(SpriteBatch sb) {

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
        if (freeUse) {
            desc += uiStrings.TEXT[13];
        }else {
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
            } else {
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
        }
        desc += uiStrings.TEXT[4];
        desc += relicToPrint.name;
        desc += uiStrings.TEXT[5];
        if (freeUse){
            desc += uiStrings.TEXT[12];
        }else if (!canPayWithScrap(relicToPrint) ){
            desc += uiStrings.TEXT[9];
            for (AbstractRelic r : possibleRelics) {
                if (r.tier == relicToPrint.tier) {
                    desc += uiStrings.TEXT[10];
                    desc += r.name;
                }
            }
        }



        return desc;

    }

    private String GetTipName() {
        return uiStrings.TEXT[0];
    }

    @Override
    public void open(boolean bossChest) {

        AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;// 77

        AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);// 79
        AbstractDungeon.overlayMenu.proceedButton.hideInstantly();



        if ( relicToPrint != null && tryToPay(relicToPrint)) {

            this.isOpen = true;// 85
            if (!Hack3dEnabled) {
                EndAnim();
            }

        }else{
            AbstractDungeon.overlayMenu.proceedButton.show();
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;// 77
        }





    }

    private boolean tryToPay(AbstractRelic relicToPrint) {
        if (canPayWithScrap(relicToPrint) || canPayWithRelic(relicToPrint) || freeUse) {
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
        if (freeUse){
            freeUse = false;
            justPaidWithScrap = true;
            return;
        }

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
            justPaidWithScrap = true;
        }else {


            if (!possibleRelics.isEmpty()) {
                relicToRemove = possibleRelics.get(AbstractDungeon.miscRng.random(possibleRelics.size() - 1));
                player.loseRelic(relicToRemove.relicId);
                player.reorganizeRelics();
            }
        }
    }


    private boolean canPayWithScrap(AbstractRelic relic) {

        ScrapInfo info = ScrapField.scrapFieldPatch.scrapInfo.get(player);

        switch (relic.tier) {
            case COMMON:
                return info.commonScrap >= COMMON_SCRAP_COST;
            case UNCOMMON:
                return info.uncommonScrap >= UNCOMMON_SCRAP_COST;
            case RARE:
                return info.rareScrap >= RARE_SCRAP_COST;
            default:
                return false;
        }

    }
    private static boolean canPayWithScrap(AbstractRelic.RelicTier tier) {

        ScrapInfo info = ScrapField.scrapFieldPatch.scrapInfo.get(player);

        switch (tier) {
            case COMMON:
                return info.commonScrap >= COMMON_SCRAP_COST;
            case UNCOMMON:
                return info.uncommonScrap >= UNCOMMON_SCRAP_COST;
            case RARE:
                return info.rareScrap >= RARE_SCRAP_COST;
            default:
                return false;
        }

    }

    @Override
    public void update() {
        
        Vector3 screenpos = new Vector3(CHEST_LOC_X, (float) (CHEST_LOC_Y-125*Settings.scale), 0);// 878
            //Vector2 relicpos = new Vector2(InputHelper.mX, InputHelper.mY);// 878
            //modelInstance.transform.rotate(0, 1, 0, Gdx.graphics.getDeltaTime() *5);
        if (relicToPrint != null){
            relicToPrint.hb.update();

            float wigglerX = MathUtils.cosDeg((float) (System.currentTimeMillis() / 10L % 360L)) * 3.0F * Settings.scale;// 51
            float wigglerY = MathUtils.sinDeg((float) (System.currentTimeMillis() / 6L % 360L)) * 5.0F * Settings.scale;// 52


            relicToPrint.currentX = CHEST_LOC_X + DISPLAY_OFFSET_X + wigglerX;
            relicToPrint.currentY = CHEST_LOC_Y + DISPLAY_OFFSET_Y + wigglerY;
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
        
        if (relicToRemove != null){
            relicToRemove.hb.update();
            relicToRemove.targetX = CHEST_LOC_X + OFFSET_X;
            relicToRemove.targetY = CHEST_LOC_Y + OFFSET_Y;
            relicToRemove.hb.move(relicToRemove.currentX, relicToRemove.currentY);// 54

            if (relicToRemove.currentX != relicToRemove.targetX) {// 366
                relicToRemove.currentX = MathUtils.lerp(relicToRemove.currentX, relicToRemove.targetX, Gdx.graphics.getDeltaTime() * 12.0F);// 367
                if (Math.abs(relicToRemove.currentX - relicToRemove.targetX) < 0.5F) {// 368
                    relicToRemove.currentX = relicToRemove.targetX;// 369
                }
            }

            if (relicToRemove.currentY != relicToRemove.targetY) {// 372
                relicToRemove.currentY = MathUtils.lerp(relicToRemove.currentY, relicToRemove.targetY, Gdx.graphics.getDeltaTime() * 3.0F);// 373
                if (Math.abs(relicToRemove.currentY - relicToRemove.targetY) < 0.5F) {// 374
                    relicToRemove.currentY = relicToRemove.targetY;// 375
                }
            }
        }
        if (justPaidWithScrap ||( relicToRemove != null && relicToRemove.currentX == relicToRemove.targetX && relicToRemove.currentY == relicToRemove.targetY)  ) {// 378
            relicToRemove = null;// 379
            justPaidWithScrap = false;
            CardCrawlGame.sound.play(makeID("PRINTER_USE"));// 83
            controller.action("Armature|Armature|DuplicatorArmature|IdleToOpenToIdle|Base Layer", 1, 1, new AnimationController.AnimationListener() {
                @Override
                public void onEnd(AnimationController.AnimationDesc animation) {
                    EndAnim();

                }

                @Override
                public void onLoop(AnimationController.AnimationDesc animation) {

                }
            }, 0.2f);
        }

        if (Hack3dEnabled) {
            if (controller != null){
                controller.update(Gdx.graphics.getDeltaTime());
            }
            if (modelInstance != null){
                modelInstance.transform.setTranslation(cam.unproject(screenpos).add(0, 0, -cam.position.z));// 880
            }

        }
        if (relicToPrint != null && !relicToPrint.hb.hovered && hb.hovered){
            TipHelper.renderGenericTip((float)InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY + 180.0F * Settings.scale, GetTipName(), GetTipDesc());// 183

        }





        this.hb.update();// 141
        if ( relicToPrint != null && !this.relicToPrint.hb.hovered &&(this.hb.hovered && InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) && !AbstractDungeon.isScreenUp && !this.isOpen && this.keyRequirement()) {// 142 143
            InputHelper.justClickedLeft = false;// 144
            this.open(false);// 145
        }
    }

    private void EndAnim() {
        //controller.setAnimation("Armature|Armature|DuplicatorArmature|Idle|Base Layer", -1);
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(CHEST_LOC_X, CHEST_LOC_Y, relicToPrint);// 87
        relicToPrint.onEquip();
        relicToPrint = null;
        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;// 77

        relicToRemove = null;
        player.reorganizeRelics();

        SetRelicToPrint();
    }

    static {
        DISPLAY_OFFSET_X = -70*Settings.scale;// 31
        DISPLAY_OFFSET_Y = -25*Settings.scale;// 32
        OFFSET_X = 75*Settings.scale;// 31
        OFFSET_Y = -125*Settings.scale;// 32

    }
}
