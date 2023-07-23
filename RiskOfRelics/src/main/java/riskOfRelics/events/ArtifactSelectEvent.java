package riskOfRelics.events;

import basemod.BaseMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.*;
import riskOfRelics.screens.ArtifactSelectScreen;

import java.util.Arrays;
import java.util.List;

import static riskOfRelics.RiskOfRelics.makeEventPath;

public class ArtifactSelectEvent extends AbstractImageEvent {
    public static final String ID = RiskOfRelics.makeID("AmbrySelectEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("AspectEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;
    boolean HasAllArts;



    public ArtifactSelectEvent() {

        super(NAME, DESCRIPTIONS[0], IMG);



        String desc = DESCRIPTIONS[0];


        // The first dialogue options available to us.
       HasAllArts = (RiskOfRelics.UnlockedArtifacts.size() >= 16);
        if (!HasAllArts) {
            this.body = desc;
            imageEventText.setDialogOption(OPTIONS[0]);
        } else {
            this.body = DESCRIPTIONS[2];
            imageEventText.setDialogOption(OPTIONS[2]);
        }


    }



    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1; // Screen set the screen number to 1. Once we exit the switch (i) statement,
                        // we'll still continue the switch (screenNum) statement. It'll find screen 1 and do it's actions
                        // (in our case, that's the final screen, but you can chain as many as you want like that)


                        // Get a random starting relic
                        if (!HasAllArts) {
                            BaseMod.openCustomScreen(ArtifactSelectScreen.Enum.AMBRY_SCREEN, true);
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH/2,Settings.HEIGHT/2,AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE));
                        }

                        break; // Onto screen 1 we go.
                }
                break;
            case 1: // Welcome to screenNum = 1;
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        AbstractDungeon.overlayMenu.proceedButton.show();
                        AbstractDungeon.overlayMenu.endTurnButton.hide();
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
        }
    }

}
