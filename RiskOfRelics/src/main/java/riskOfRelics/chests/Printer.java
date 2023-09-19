package riskOfRelics.chests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import riskOfRelics.patches.scrap.ScrapField;
import riskOfRelics.util.ScrapInfo;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class Printer extends AbstractChest {
    private AbstractRelic relicToPrint;

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

        this.relicToPrint = AbstractDungeon.returnRandomRelic(tier);

    }

    private boolean tierAvailable(AbstractRelic.RelicTier tier){
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
        sb.setColor(Color.WHITE);// 154
        relicToPrint.renderWithoutAmount(sb, Color.WHITE.cpy());
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

        if (Settings.isControllerMode && !this.isOpen) {// 224
            sb.setColor(Color.WHITE);// 225
            sb.draw(CInputActionSet.select.getKeyImg(), CHEST_LOC_X - 32.0F - 150.0F * Settings.scale, CHEST_LOC_Y - 32.0F - 210.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);// 226 227
        }

        this.hb.render(sb);// 245
    }// 246

    @Override
    public void open(boolean bossChest) {

        AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);// 79

        CardCrawlGame.sound.play("CHEST_OPEN");// 83

        if (tryToPay(relicToPrint)) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, relicToPrint);// 87
            this.isOpen = true;// 85
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
        ArrayList<AbstractRelic> relics = player.relics;
        relics.removeIf(r -> r.tier != relicToPrint.tier);
        return !relics.isEmpty();
    }

    private void payForPrint(AbstractRelic relicToPrint) {

        if (canPayWithScrap(relicToPrint)) {
            ScrapInfo info = ScrapField.scrapFieldPatch.scrapInfo.get(player);
            switch (relicToPrint.tier) {
                case COMMON:
                    info.commonScrap -= 3;
                    break;
                case UNCOMMON:
                    info.uncommonScrap -= 3;
                    break;
                case RARE:
                    info.rareScrap -= 2;
                    break;
            }
        }else {
            ArrayList<AbstractRelic> relics = player.relics;


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

}
