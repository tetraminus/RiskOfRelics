//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package riskOfRelics.screens;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import riskOfRelics.relics.Warbanner;

import static riskOfRelics.RiskOfRelics.makeID;
import static riskOfRelics.RiskOfRelics.makeUIPath;

public class WarbannerOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private Warbanner caller;

    public WarbannerOption(boolean active, Warbanner caller) {
        this.label = TEXT[0];// 14
        this.usable = active;// 15
        this.updateUsability(active);// 16
        this.caller = caller;
    }// 17

    public void updateUsability(boolean canUse) {
        this.description = canUse ? TEXT[1] : TEXT[2];// 20
        this.img = ImageMaster.loadImage(makeUIPath("campfire/Warbanner.png"));// 21
    }// 22

    public void useOption() {
        if (this.usable) {// 26
            AbstractDungeon.effectList.add(new WarbannerEffect(caller));// 27
        }

    }// 29

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("WarbannerOption"));// 10
        TEXT = uiStrings.TEXT;// 11
    }


}
