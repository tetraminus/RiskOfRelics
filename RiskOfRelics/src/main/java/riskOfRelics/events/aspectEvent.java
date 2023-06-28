package riskOfRelics.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.*;
import riskOfRelics.util.TextureLoader;

import java.util.Arrays;
import java.util.List;

import static riskOfRelics.RiskOfRelics.makeEventPath;

public class aspectEvent extends AbstractImageEvent {


    public static final String ID = RiskOfRelics.makeID("AspectEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final List<String> RELICS = Arrays.asList(fireAspect.ID, iceAspect.ID, earthAspect.ID, malAspect.ID, perfAspect.ID, lightningAspect.ID, ghostAspect.ID );



    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("AspectEvent.png");
    private final int relicNum;

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;


    private final AbstractRelic relic;

    public aspectEvent() {

        super(NAME, DESCRIPTIONS[0], IMG);
        relicNum = AbstractDungeon.miscRng.random(RELICS.size()-1);
        relic = RelicLibrary.getRelic((RELICS.get(relicNum)));
        this.img = TextureLoader.getTexture(getEventImage(relicNum));

        String desc = DESCRIPTIONS[0] + getEventText(relicNum);

        imageEventText.loadImage(getEventImage(relicNum));
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


    private String getEventImage(int num) {
        switch (num) {
            case 0:
                return makeEventPath("AspectEvent0.png"); //fire

            case 1:
                return makeEventPath("AspectEvent1.png"); //ice

            case 2:
                return makeEventPath("AspectEvent2.png"); //earth

            case 3:
                return makeEventPath("AspectEvent3.png");//mal

            case 4:
                return makeEventPath("AspectEvent4.png");//perf

            case 5:
                return makeEventPath("AspectEvent5.png");//lightning

            case 6:
                return makeEventPath("AspectEvent6.png");//celestine

            default:
                return makeEventPath("AspectEvent.png");//default

        }

    }
    private String getEventText(int num){
        switch (num) {
            case 0:
                return DESCRIPTIONS[3];

            case 1:
                return DESCRIPTIONS[4];

            case 2:
                return DESCRIPTIONS[5];

            case 3:
                return DESCRIPTIONS[6];

            case 4:
                return DESCRIPTIONS[7];

            case 5:
                return DESCRIPTIONS[8];

            case 6:
                return DESCRIPTIONS[9];

            default:
                return "Couldn't find text";
        }

    }
}

