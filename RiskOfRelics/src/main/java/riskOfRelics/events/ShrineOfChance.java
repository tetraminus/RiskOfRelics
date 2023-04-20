package riskOfRelics.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import riskOfRelics.DefaultMod;
import riskOfRelics.relics.IrradiantPearl;
import riskOfRelics.relics.Pearl;

import java.util.ArrayList;
import java.util.List;

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
    private int dmg = 3;
    private int totalDamageDealt = 0;

    public ShrineOfChance() {
        super(NAME, DESCRIPTIONS[0], IMG);




        imageEventText.setDialogOption(OPTIONS[1]);

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {// 52
            case 0:
                switch (buttonPressed) {// 54
                    case 0:
                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, this.dmg));// 56
                        CardCrawlGame.sound.play("ATTACK_POISON");// 57
                        this.totalDamageDealt += this.dmg;// 58
                        int random = AbstractDungeon.miscRng.random(0, 99);// 59
                        if (random >= 99 - this.relicObtainChance) {// 61
                            this.imageEventText.updateBodyText(SUCCESS_MSG);// 62
                            AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());// 63 64
                            AbstractEvent.logMetricObtainRelicAndDamage("Scrap Ooze", "Success", r, this.totalDamageDealt);// 65
                            this.imageEventText.updateDialogOption(0, OPTIONS[3]);// 66
                            this.imageEventText.removeDialogOption(1);// 67
                            this.screenNum = 1;// 68
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, r);// 69
                        } else {
                            this.imageEventText.updateBodyText(FAIL_MSG);// 74
                            this.relicObtainChance += 10;// 75
                            ++this.dmg;// 76
                            this.imageEventText.updateDialogOption(0, OPTIONS[4] + this.dmg + OPTIONS[1] + this.relicObtainChance + OPTIONS[2]);// 77
                            this.imageEventText.updateDialogOption(1, OPTIONS[3]);// 80
                        }

                        return;// 101
                    case 1:
                        AbstractEvent.logMetricTakeDamage("Scrap Ooze", "Fled", this.totalDamageDealt);// 85
                        this.imageEventText.updateBodyText(ESCAPE_MSG);// 86
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);// 87
                        this.imageEventText.removeDialogOption(1);// 88
                        this.screenNum = 1;// 89
                        return;
                    default:
                        logger.info("ERROR: case " + buttonPressed + " should never be called");// 92
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
