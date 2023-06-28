package riskOfRelics.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import riskOfRelics.DefaultMod;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static riskOfRelics.DefaultMod.makeEventPath;

public class ShrineOfChance extends AbstractImageEvent {


    public static final String ID = DefaultMod.makeID("ShrineOfChance");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("ShrineOfChance.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;
    private int relicObtainChance = 25;
    private int cost = 75;
    private static final int GOLD_GAIN = 20;
    private int totalGoldGiven = 0;

    public ShrineOfChance() {
        super(NAME, DESCRIPTIONS[0], IMG);


        if (AbstractDungeon.ascensionLevel >= 15) {// 35
            this.cost = 90;// 36
        }
        if (player.gold >= cost){
            this.imageEventText.setDialogOption(OPTIONS[0] + this.cost + OPTIONS[1] + this.relicObtainChance + OPTIONS[2]);// 39
        } else {
            this.imageEventText.setDialogOption(OPTIONS[5], true);// 39
        }


        this.imageEventText.setDialogOption(OPTIONS[3]);// 40



    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {// 52
            case 0:
                switch (buttonPressed) {// 54
                    case 0:
                        AbstractDungeon.player.loseGold(cost);// 56
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.cost/2));// 79
                        this.totalGoldGiven += this.cost;// 58
                        int random = AbstractDungeon.eventRng.random(0, 99);// 59
                        if (random >= 99 - this.relicObtainChance) {// 61
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);// 62
                            AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());// 63 64

                            this.imageEventText.updateDialogOption(0, OPTIONS[3]);// 66
                            this.imageEventText.removeDialogOption(1);// 67
                            this.screenNum = 1;// 68
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, r);// 69
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);// 74
                            this.relicObtainChance += 10;// 75
                            this.cost += GOLD_GAIN;// 76
                            if (player.gold >= cost){
                                this.imageEventText.updateDialogOption(0,OPTIONS[0] + this.cost + OPTIONS[1] + this.relicObtainChance + OPTIONS[2]);// 39
                            } else {
                                this.imageEventText.updateDialogOption(0,OPTIONS[5], true);// 39
                            }
                            this.imageEventText.updateDialogOption(1, OPTIONS[3]);// 80
                        }

                        return;// 101
                    case 1:

                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);// 86
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);// 87
                        this.imageEventText.removeDialogOption(1);// 88
                        this.screenNum = 1;// 89
                        return;
                    default:

                        return;
                }
            case 1:
                this.openMap();// 97
        }

    }

    public void update() { // We need the update() when we use grid screens (such as, in this case, the screen for selecting a card to remove)
        super.update(); // Do everything the original update()
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) { // Once the grid screen isn't empty (we selected a card for removal)
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0); // Get the card
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2))); // Create the card removal effect
            player.masterDeck.removeCard(c); // Remove it from the deck
            AbstractDungeon.gridSelectScreen.selectedCards.clear(); // Or you can .remove(c) instead of clear,
            // if you want to continue using the other selected cards for something
        }

    }

}
