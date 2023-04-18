package riskOfRelics.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import riskOfRelics.DefaultMod;
import riskOfRelics.relics.*;

import java.util.Arrays;
import java.util.List;

import static riskOfRelics.DefaultMod.makeEventPath;

public class aspectEvent extends AbstractImageEvent {


    public static final String ID = DefaultMod.makeID("aspectEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final List<String> RELICS = Arrays.asList(fireAspect.ID, iceAspect.ID, earthAspect.ID, malAspect.ID, perfAspect.ID, lightningAspect.ID, ghostAspect.ID );



    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("aspectEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;


    private final AbstractRelic relic;

    public aspectEvent() {

        super(NAME, DESCRIPTIONS[0], IMG);
        relic = RelicLibrary.getRelic((RELICS.get(AbstractDungeon.miscRng.random(RELICS.size()-1))));


        String desc = DESCRIPTIONS[0] + getEventText(relic);

        // The first dialogue options available to us.
        this.body = desc;
        imageEventText.setDialogOption(OPTIONS[0]); // Inspiration - Gain a Random Starting Relic
        imageEventText.setDialogOption(OPTIONS[1]); // Denial - lose healthDamage Max HP

    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1; // Screen set the screen number to 1. Once we exit the switch (i) statement,
                        // we'll still continue the switch (screenNum) statement. It'll find screen 1 and do it's actions
                        // (in our case, that's the final screen, but you can chain as many as you want like that)

                        AbstractRelic relicToAdd = relic.makeCopy();
                        // Get a random starting relic

                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), relicToAdd);


                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Denial

                        //imageEventText.loadImage("riskOfRelicsResources/images/events/IdentityCrisisEvent2.png"); // Change the shown image
                        // Other than that, this option doesn't do anything special.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1;
                        break;
                }
                break;
            case 1: // Welcome to screenNum = 1;
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
        }
    }

    public void update() { // We need the update() when we use grid screens (such as, in this case, the screen for selecting a card to remove)
        super.update(); // Do everything the original update()
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) { // Once the grid screen isn't empty (we selected a card for removal)
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0); // Get the card
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2))); // Create the card removal effect
            AbstractDungeon.player.masterDeck.removeCard(c); // Remove it from the deck
            AbstractDungeon.gridSelectScreen.selectedCards.clear(); // Or you can .remove(c) instead of clear,
            // if you want to continue using the other selected cards for something
        }

    }
    private String getEventText(AbstractRelic r){
        if (r == RelicLibrary.getRelic(RELICS.get(0))){//fire
            return DESCRIPTIONS[3];
        }
        else if (r == RelicLibrary.getRelic(RELICS.get(1))){//Ice
            return DESCRIPTIONS[4];
        }
        else if (r == RelicLibrary.getRelic(RELICS.get(2))){//Earth
            return DESCRIPTIONS[5];
        }
        else if (r == RelicLibrary.getRelic(RELICS.get(3))){//Malachite
            return DESCRIPTIONS[6];
        }
        else if (r == RelicLibrary.getRelic(RELICS.get(4))){//Perfect
            return DESCRIPTIONS[7];
        }
        else if (r == RelicLibrary.getRelic(RELICS.get(5))){//Lightning
            return DESCRIPTIONS[8];
        }
        else if (r == RelicLibrary.getRelic(RELICS.get(6))){//Celestine
            return DESCRIPTIONS[9];
        }
        else{
            return "COULD NOT GET TEXT";
        }

    }
}

