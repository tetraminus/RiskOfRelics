package riskOfRelics.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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

public class CleansingPoolEvent extends AbstractImageEvent {


    public static final String ID = DefaultMod.makeID("CleansingPoolEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("CleansingPoolEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;
    private List<AbstractRelic> BossRelics = new ArrayList<>();
    //The actual number of how much Max HP we're going to lose.

    public CleansingPoolEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);



        // The first dialogue options available to us.
        for (AbstractRelic r: player.relics) {
            if (r.tier == AbstractRelic.RelicTier.BOSS){
                BossRelics.add(r);
            }
        }


        if (BossRelics.isEmpty()){
            imageEventText.setDialogOption(OPTIONS[2], true);
        } else {
            imageEventText.setDialogOption(OPTIONS[0]);
        }

        imageEventText.setDialogOption(OPTIONS[1]);

    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                         // Screen set the screen number to 1. Once we exit the switch (i) statement,
                        // we'll still continue the switch (screenNum) statement. It'll find screen 1 and do it's actions
                        // (in our case, that's the final screen, but you can chain as many as you want like that)
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions();
                        player.loseRelic(BossRelics.get(
                                AbstractDungeon.eventRng.random(BossRelics.size()-1)).relicId);

                        if (AbstractDungeon.eventRng.random(99) < 33){
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new IrradiantPearl());

                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new Pearl());

                        }

                        // Get a random starting relic


                        screenNum = 1;
                        break; // Onto screen 1 we go.

                    case 1: // If you press button the first button (Button at index 0), in this case: Inspiration.

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1;
                        break;
                }
                break;
            case 1: // Welcome to screenNum = 1;

                if (i == 0) { // If you press the first (and this should be the only) button,
                    openMap(); // You'll open the map and end the event.
                }
                break;
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
