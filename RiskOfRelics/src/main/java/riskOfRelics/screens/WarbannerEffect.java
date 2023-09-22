//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package riskOfRelics.screens;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import riskOfRelics.cards.WarbannerCardmod;
import riskOfRelics.relics.Warbanner;
import riskOfRelics.vfx.ChangeCostVfx;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static riskOfRelics.RiskOfRelics.makeID;

public class WarbannerEffect extends AbstractGameEffect {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float DUR = 1.5F;
    private boolean openedScreen = false;
    private Color screenColor;
    private Warbanner caller;

    public WarbannerEffect(Warbanner caller) {
        //this.screenColor = AbstractDungeon.fadeColor.cpy();// 32
        this.duration = 2.5F;// 38
        //this.screenColor.a = 0.0F;// 39
        AbstractDungeon.overlayMenu.proceedButton.hide();// 40
        this.caller = caller;

    }// 41

    public void update() {
        if (!AbstractDungeon.isScreenUp) {// 45
            this.duration -= Gdx.graphics.getDeltaTime();// 46
            //this.updateBlackScreenColor();// 47
        }

        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && AbstractDungeon.gridSelectScreen.forPurge) {// 51
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                //AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));// 54
                CardModifierManager.addModifier(c, new WarbannerCardmod());
                caller.counter--;
                //player.bottledCardUpgradeCheck(c);// 58
                AbstractDungeon.effectsQueue.add(new ChangeCostVfx(c.makeStatEquivalentCopy()));// 59
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();// 61
            //((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();// 62
        }

        if (this.duration < 2.3F && !this.openedScreen) {// 66
            this.openedScreen = true;// 67
            CardGroup cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : player.masterDeck.group) {
                if (c.cost > 0 && c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                    cards.addToTop(c);
                }
            }
            AbstractDungeon.gridSelectScreen.open(cards, 1, TEXT[1], false, false, true, true);// 59 60
            //AbstractDungeon.overlayMenu.cancelButton.hide();// 3229
            //AbstractDungeon.gridSelectScreen.forClarity = true;// 3230

        }

        if (this.duration < 0.0F) {// 84
            this.isDone = true;// 85
            if (CampfireUI.hidden) {// 86
                AbstractRoom.waitTimer = 0.0F;// 87
                AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;// 88
                ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();// 89
            }
        }

    }// 92

    private void updateBlackScreenColor() {
//        if (this.duration > 1.0F) {// 98
//            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);// 99
//        } else {
//            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);// 101
//        }

    }// 103

    public void render(SpriteBatch sb) {
        //sb.setColor(this.screenColor);// 107
        //sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);// 108
        if (AbstractDungeon.screen == CurrentScreen.GRID) {// 110
            AbstractDungeon.gridSelectScreen.render(sb);// 111
        }

    }// 113

    public void dispose() {
    }// 118

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("WarbannerOption"));// 27
        TEXT = uiStrings.TEXT;// 28
    }
}
