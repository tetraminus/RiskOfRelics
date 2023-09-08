package riskOfRelics;

import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import riskOfRelics.artifacts.DeathArt;
import riskOfRelics.artifacts.GlassArt;
import riskOfRelics.bosses.BulwarksAmbry;
import riskOfRelics.cards.AbstractDynamicCard;
import riskOfRelics.events.*;
import riskOfRelics.patches.ArtifactFTUEPatches;
import riskOfRelics.patches.EnigmaAndMetaPatches;
import riskOfRelics.patches.RerollRewardPatch;
import riskOfRelics.potions.BottledChaos;
import riskOfRelics.potions.EnergyDrink;
import riskOfRelics.potions.TonicPotion;
import riskOfRelics.relics.BackupMag;
import riskOfRelics.relics.BaseRelic;
import riskOfRelics.relics.Ego;
import riskOfRelics.rewards.RerollReward;
import riskOfRelics.screens.ArtifactSelectScreen;
import riskOfRelics.screens.ArtifactTopPanelItem;
import riskOfRelics.util.*;
import riskOfRelics.vfx.ArtifactAboveCreatureAction;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.megacrit.cardcrawl.core.CardCrawlGame.dungeon;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;


@SpireInitializer
public class RiskOfRelics implements
        EditCardsSubscriber,
        PostUpdateSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        StartGameSubscriber,
        MaxHPChangeSubscriber


    {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(RiskOfRelics.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties riskOfRelicsDefaultSettings = new Properties();
    public static final String ENABLE_ASPECT_DESC_SETTINGS = "enableAspectDesc";
    public static final String ENABLE_3D_HACK_SETTINGS = "enable3dHack";
    public static boolean AspectDescEnabled = true; // The boolean we'll be setting on/off (true/false)
    public static boolean Hack3dEnabled = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Risk of Relics";
    private static final String AUTHOR = "Tetraminus"; // And pretty soon - You!
    private static final String DESCRIPTION = "Risk of Rain 2 mod for Slay the Spire.";

    public static ArrayList<Artifacts> ActiveArtifacts = new ArrayList<Artifacts>();
    public static ArrayList<Artifacts> UnlockedArtifacts = new ArrayList<Artifacts>();
    public static SpireConfig ModConfig;

    // =============== INPUT TEXTURE LOCATION =================







    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "riskOfRelicsResources/images/Badge.png";


    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeBossPath(String resourcePath) {
        return getModID() + "Resources/images/ui/map/icon/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public RiskOfRelics() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);


        setModID("riskOfRelics");

        logger.info("Done subscribing");


        logger.info("Done creating the color");




    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = RiskOfRelics.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = RiskOfRelics.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = RiskOfRelics.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

    // ====== YOU CAN EDIT AGAIN ======


    public static void initialize() {
        logger.info("========================= Initializing Risk Of Relics. Hi. =========================");
        RiskOfRelics riskOfRelics = new RiskOfRelics();
        logger.info("========================= /Risk Of Relics Initialized. Hello World./ =========================");
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        riskOfRelicsDefaultSettings.setProperty(ENABLE_ASPECT_DESC_SETTINGS, "FALSE");
        riskOfRelicsDefaultSettings.setProperty(ENABLE_3D_HACK_SETTINGS, "TRUE");
        riskOfRelicsDefaultSettings.setProperty("hasShownFTUE", "FALSE");
        try {
            ModConfig = new SpireConfig("riskOfRelicsMod", "riskOfRelicsConfig", riskOfRelicsDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            ModConfig.load(); // Load the setting and set the boolean to equal it
            AspectDescEnabled = ModConfig.getBool(ENABLE_ASPECT_DESC_SETTINGS);
            Hack3dEnabled = ModConfig.getBool(ENABLE_3D_HACK_SETTINGS);


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Hack3dEnabled){
            logger.warn("3d Hack enabled, if you crash when fighting a certain boss, turn this off.");
        }

        logger.info("Done adding mod settings");

        ActiveArtifacts = new ArrayList<>();
        try {
                for (String A: ModConfig.getString("Artifacts").split(",")) {
                    if (!UnlockedArtifacts.contains(getArtifactfromName(A))) {
                        UnlockedArtifacts.add(getArtifactfromName(A));
                    }
                }
        } catch (Exception e) {
                ModConfig.setString("Artifacts","");
        }
        try {
                ModConfig.save();
        } catch (IOException e) {
                throw new RuntimeException(e);
        }


    }
    public static boolean hasShownFTUE() {

        return ModConfig.getBool("hasShownFTUE");
    }
    public static void setHasShownFTUE(boolean b) {
        ModConfig.setBool("hasShownFTUE", b);
        saveData();

    }



    public static void saveData() {
        try {
            ModConfig.setString("Artifacts","");
            for (Artifacts A:
                    UnlockedArtifacts) {
                if (!ModConfig.getString("Artifacts").contains(A.name()+",")) {
                    ModConfig.setString("Artifacts",ModConfig.getString("Artifacts")+A.name()+",");
                }


            }
            ModConfig.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ModConfig.setString("EnabledArtifacts","");

            //remove duplicates from ActiveArtifacts
            Set<Artifacts> hs = new HashSet<>(ActiveArtifacts);
            ActiveArtifacts.clear();
            ActiveArtifacts.addAll(hs);


            for (Artifacts A:
                    ActiveArtifacts) {

                ModConfig.setString("EnabledArtifacts",ModConfig.getString("EnabledArtifacts")+A.name()+",");

            }
            ModConfig.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ModConfig.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Texture getArtifactImage(Artifacts artifact, boolean On) {
        if (On) {
            return TextureLoader.getTexture("riskOfRelicsResources/images/ui/ambrySelect/Artifact" + (artifact.ordinal()+1) +  "_on.png");
        } else {
            return TextureLoader.getTexture("riskOfRelicsResources/images/ui/ambrySelect/Artifact" + (artifact.ordinal()+1) + "_off.png");
        }
    }




        // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        //logger.info("Beginning to edit characters. " + "Add " + TheDefault.Enums.THE_DEFAULT.toString());

        //BaseMod.addCharacter(new TheDefault("the Default", TheDefault.Enums.THE_DEFAULT),
        //THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheDefault.Enums.THE_DEFAULT);

        // receiveEditPotions();
        //logger.info("Added " + TheDefault.Enums.THE_DEFAULT.toString());
    }

    // =============== /LOAD THE CHARACTER/ =================


    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {

        logger.info("Loading badge image and mod options");

        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        BaseMod.addCustomScreen(new ArtifactSelectScreen());
        //BaseMod.addCustomScreen(new ArtifactInfoScreen());
        BaseMod.addTopPanelItem(new ArtifactTopPanelItem());
        BaseMod.addSaveField(makeID("ActiveArtifacts"),new ArtifactSaver());
        BaseMod.addSaveField(makeID("MetamorphCharacter"),new MetamorphSaver());
        BaseMod.addSaveField(makeID("EnigmaCounter"),new CounterSavers.EnigmaCounterSaver());
        BaseMod.addSaveField(makeID("MetamorphCounter"),new CounterSavers.MetamorphCounterSaver());
        BaseMod.addSaveField(makeID("VengCounter") ,new CounterSavers.VengCounterSaver());
        BaseMod.addSaveField(makeID("PlayerEquipment") , new EquipmentSaver());
        BaseMod.addSaveField(makeID("ChargeTimer") , new ChargeTimerSaver());
        BaseMod.addSaveField(makeID("PlayerEquipmentCounter") , new EQCounterSaver());

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();



//         Create the on/off button:
        ModLabeledToggleButton enableAspectDescButton = new ModLabeledToggleButton("Enable Aspect Descriptions(Default: OFF) | Restart required.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                AspectDescEnabled, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {
                }, // thing??????? idk
                (button) -> { // The actual button:

                    AspectDescEnabled = button.enabled; // The boolean true/false will be whether the button is enabled or not
                    try {
                        // And based on that boolean, set the settings and save them
                        SpireConfig config = new SpireConfig("riskOfRelicsMod", "riskOfRelicsConfig", riskOfRelicsDefaultSettings);;
                        config.setBool(ENABLE_ASPECT_DESC_SETTINGS, AspectDescEnabled);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        settingsPanel.addUIElement(enableAspectDescButton); // Add the button to the settings panel. Button is a go.

        ModLabeledToggleButton enable3dButton = new ModLabeledToggleButton("3d Hack (Default: ON) | Restart required. turn off if getting crashes in the bulwarks ambry.",
                350.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                Hack3dEnabled, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {
                }, // thing??????? idk
                (button) -> { // The actual button:

                    Hack3dEnabled = button.enabled; // The boolean true/false will be whether the button is enabled or not
                    try {
                        // And based on that boolean, set the settings and save them
                        SpireConfig config = new SpireConfig("riskOfRelicsMod", "riskOfRelicsConfig", riskOfRelicsDefaultSettings);;
                        config.setBool(ENABLE_3D_HACK_SETTINGS, Hack3dEnabled);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        settingsPanel.addUIElement(enable3dButton); // Add the button to the settings panel. Button is a go.
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);


        // =============== EVENTS =================
        // https://github.com/daviscook477/BaseMod/wiki/Custom-Events

        // You can add the event like so:
        // BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        // Then, this event will be exclusive to the City (act 2), and will show up for all characters.
        // If you want an event that's present at any part of the game, simply don't include the dungeon ID

        // If you want to have more specific event spawning (e.g. character-specific or so)
        // deffo take a look at that basemod wiki link as well, as it explains things very in-depth
        // btw if you don't provide event type, normal is assumed by default

        // Create a new event builder
        // Since this is a builder these method calls (outside of create()) can be skipped/added as necessary
        AddEventParams eventParams = new AddEventParams.Builder(IdentityCrisisEvent.ID, IdentityCrisisEvent.class) // for this specific event
                .dungeonID(TheCity.ID) // The dungeon (act) this event will appear in
                .create();

        // Add the event
        //BaseMod.addEvent(eventParams);
        eventParams = new AddEventParams.Builder(aspectEvent.ID, aspectEvent.class) // for this specific event
                .dungeonID(TheCity.ID) // The dungeon (act) this event will appear in
                .create();

        // Add the event
        BaseMod.addEvent(eventParams);

        eventParams = new AddEventParams.Builder(CleansingPoolEvent.ID, CleansingPoolEvent.class) // for this specific event
                .eventType(EventUtils.EventType.SHRINE)
                .spawnCondition(() -> AbstractDungeon.actNum > 1)
                .create();

        BaseMod.addEvent(eventParams);
        eventParams = new AddEventParams.Builder(ShrineOfChance.ID, ShrineOfChance.class) // for this specific event
                .eventType(EventUtils.EventType.SHRINE)

                .create();

        BaseMod.addEvent(eventParams);
        eventParams = new AddEventParams.Builder(ArtifactSelectEvent.ID, ArtifactSelectEvent.class) // for this specific event

                //can only spawn in endless
                .eventType(EventUtils.EventType.SHRINE)
                .spawnCondition(() -> Settings.isEndless )
                .create();

        BaseMod.addEvent(eventParams);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");




        BaseMod.registerCustomReward(RerollRewardPatch.RISKOFRELICS_REROLL, (rewardSave) -> { // this handles what to do when this quest type is loaded.
                    return new RerollReward();
                },
                (customReward) -> { // this handles what to do when this quest type is saved.
                    return new RewardSave(customReward.type.toString(), null,0, 0);
                });

        if (!RiskOfRelics.hasShownFTUE()) {
            ArtifactFTUEPatches.FTUEHandler.open();
            RiskOfRelics.setHasShownFTUE(true);

        }

        BaseMod.addMonster(BulwarksAmbry.ID , BulwarksAmbry::new);



    }

    // =============== / POST-INITIALIZE/ =================



    // ================ ADD POTIONS ===================

    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(TonicPotion.class, TonicPotion.POTION_LIQUID, TonicPotion.POTION_HYBRID, null, TonicPotion.POTION_ID);
        BaseMod.addPotion(EnergyDrink.class, EnergyDrink.POTION_LIQUID, EnergyDrink.POTION_HYBRID, null, EnergyDrink.POTION_ID);
        BaseMod.addPotion(BottledChaos.class, BottledChaos.POTION_LIQUID, BottledChaos.POTION_HYBRID, null, BottledChaos.POTION_ID);

        logger.info("Done editing potions");
    }

    // ================ /ADD POTIONS/ ===================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // Take a look at https://github.com/daviscook477/BaseMod/wiki/AutoAdd
        // as well as
        // https://github.com/kiooeht/Bard/blob/e023c4089cc347c60331c78c6415f489d19b6eb9/src/main/java/com/evacipated/cardcrawl/mod/bard/BardMod.java#L319
        // for reference as to how to turn this into an "Auto-Add" rather than having to list every relic individually.
        // Of note is that the bard mod uses it's own custom relic class (not dissimilar to our AbstractDefaultCard class for cards) that adds the 'color' field,
        // in order to automatically differentiate which pool to add the relic too.

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        //BaseMod.addRelicToCustomPool(new PlaceholderRelic(), TheDefault.Enums.COLOR_GRAY);
        //BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), TheDefault.Enums.COLOR_GRAY);

        // This adds a relic to the Shared pool. Every character can find this relic.
        // This finds and adds all relics inheriting from CustomRelic that are in the same package
        // as MyRelic, keeping all as unseen except those annotated with @AutoAdd.Seen
        new AutoAdd("RiskOfRelics")
                .packageFilter(BaseRelic.class)
                .any(BaseRelic.class, (info, relic) -> {
                    logger.info("Adding relic: " + relic.getClass().getSimpleName() + " to pool: " + relic.relicType);

                        BaseMod.addRelic(relic, relic.relicType);

                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });




        logger.info(BaseMod.getAllCustomRelics());
        logger.info("Done adding relics!");
        receiveEditPotions();
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables



        logger.info("Adding cards");
        // Add the cards
        // Don't delete these default cards yet. You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        // This method automatically adds any cards so you don't have to manually load them 1 by 1
        // For more specific info, including how to exclude cards from being added:
        // https://github.com/daviscook477/BaseMod/wiki/AutoAdd

        // The ID for this function isn't actually your modid as used for prefixes/by the getModID() method.
        // It's the mod id you give MTS in ModTheSpire.json - by default your artifact ID in your pom.xml

        //TODO: Rename the "DefaultMod" with the modid in your ModTheSpire.json file
        //TODO: The artifact mentioned in ModTheSpire.json is the artifactId in pom.xml you should've edited earlier
        new AutoAdd("RiskOfRelics") // ${project.artifactId}
                .packageFilter(AbstractDynamicCard.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
                .setDefaultSeen(true)
                .cards();

        // .setDefaultSeen(true) unlocks the cards
        // This is so that they are all "seen" in the library,
        // for people who like to look at the card list before playing your mod

        logger.info("Done adding cards!");
    }


    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        //logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/RiskOfRelics-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/RiskOfRelics-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/RiskOfRelics-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/RiskOfRelics-Event-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/RiskOfRelics-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/RiskOfRelics-Character-Strings.json");

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/RiskOfRelics-Orb-Strings.json");

        // UIStrings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/eng/RiskOfRelics-UI-Strings.json");

        BaseMod.loadCustomStringsFile(TutorialStrings.class,
                getModID() + "Resources/localization/eng/RiskOfRelics-Tutorial-Strings.json");

        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                getModID() + "Resources/localization/eng/RiskOfRelics-Monster-Strings.json");

        logger.info("Done editing strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/RiskOfRelics-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

        public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

        @Override
        public void receiveStartGame() {
            if (!CardCrawlGame.loadingSave && ActiveArtifacts.contains(Artifacts.GLASS)) {
                ApplyGlassArtHealth();

            }
            if(!CardCrawlGame.loadingSave){
                EnigmaAndMetaPatches.enigmaCounter = -1;
                EnigmaAndMetaPatches.vengCounter = 1;
                EnigmaAndMetaPatches.metamorphCounter = -1;
            }

            if (!CardCrawlGame.loadingSave){
                MetamorphCharacter = null;
            }
            if (ActiveArtifacts.contains(Artifacts.METAMORPHOSIS)) {
                DoMetamorphosisShtuff();
            }


        }

        public static void ApplyGlassArtHealth() {
            player.maxHealth = player.maxHealth / GlassArt.GlassHealthReduction;
            player.currentHealth = player.maxHealth;
        }

        @Override
        public int receiveMaxHPChange(int amount) {
            if (ActiveArtifacts.contains(Artifacts.DEATH)){
                if (amount > 0) {
                    amount = amount * DeathArt.MULTIPLIER;
                }

            }
            if (ActiveArtifacts.contains(Artifacts.GLASS)) {
                amount = amount / GlassArt.GlassHealthReduction;

                if (amount > 0) {
                    amount = Math.max(amount, 1);
                }
            }




            return amount;
        }
        public static String MetamorphCharacter;
        public static boolean justLoadedMetamorphosis = false;



        public static void DoMetamorphosisShtuff() {
            if (MetamorphCharacter != null ) {
                for (AbstractPlayer p : CardCrawlGame.characterManager.getAllCharacters()) {
                    if (p.chosenClass.name().equals(MetamorphCharacter)) {
                        ReinitializeCardPools(p,true);
                        break;
                    }
                }
            }else {
                ReinitializeCardPools();
            }
            effectsQueue.add(new ArtifactAboveCreatureAction((float) Settings.WIDTH /2, (float) Settings.HEIGHT /2, Artifacts.METAMORPHOSIS));
        }

        private static void ReinitializeCardPools() {
            AbstractPlayer NewChar = CardCrawlGame.characterManager.getAllCharacters().get(MathUtils.random(0, CardCrawlGame.characterManager.getAllCharacters().size() - 1));
            ReinitializeCardPools(NewChar, false);
        }

        public static void ReinitializeCardPools(AbstractPlayer NewChar, boolean isSave) {
            logger.info("INIT CARD POOL");// 1419
            long startTime = System.currentTimeMillis();// 1420
            commonCardPool.clear();// 1423
            uncommonCardPool.clear();// 1424
            rareCardPool.clear();// 1425
            colorlessCardPool.clear();// 1426
            curseCardPool.clear();// 1427
            ArrayList<AbstractCard> tmpPool = new ArrayList();// 1429
            if (ModHelper.isModEnabled("Colorless Cards")) {// 1430
                CardLibrary.addColorlessCards(tmpPool);// 1431
            }


            ReflectionHacks.privateMethod(AbstractDungeon.class, "addColorlessCards").invoke(dungeon);// 1447
            ReflectionHacks.privateMethod(AbstractDungeon.class, "addCurseCards").invoke(dungeon);// 1448
            tmpPool.addAll(NewChar.getCardPool(tmpPool));
            if (!isSave) {
                MetamorphCharacter = NewChar.chosenClass.name();
            } else {
                justLoadedMetamorphosis = true;
            }

            Iterator var4 = tmpPool.iterator();// 1449

            AbstractCard c;
            while(var4.hasNext()) {
                c = (AbstractCard)var4.next();
                switch (c.rarity) {// 1450
                    case COMMON:
                        commonCardPool.addToTop(c);// 1452
                        break;// 1453
                    case UNCOMMON:
                        uncommonCardPool.addToTop(c);// 1455
                        break;// 1456
                    case RARE:
                        rareCardPool.addToTop(c);// 1458
                        break;// 1459
                    case CURSE:
                        curseCardPool.addToTop(c);// 1461
                        break;// 1462
                    default:
                        logger.info("Unspecified rarity: " + c.rarity.name() + " when creating pools! AbstractDungeon: Line 827");// 1464 1465
                }
            }

            srcColorlessCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);// 1471
            srcCurseCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);// 1472
            srcRareCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);// 1473
            srcUncommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);// 1474
            srcCommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);// 1475
            var4 = colorlessCardPool.group.iterator();// 1477

            while(var4.hasNext()) {
                c = (AbstractCard)var4.next();
                srcColorlessCardPool.addToBottom(c);// 1478
            }

            var4 = curseCardPool.group.iterator();// 1480

            while(var4.hasNext()) {
                c = (AbstractCard)var4.next();
                srcCurseCardPool.addToBottom(c);// 1481
            }

            var4 = rareCardPool.group.iterator();// 1483

            while(var4.hasNext()) {
                c = (AbstractCard)var4.next();
                srcRareCardPool.addToBottom(c);// 1484
            }

            var4 = uncommonCardPool.group.iterator();// 1486

            while(var4.hasNext()) {
                c = (AbstractCard)var4.next();
                srcUncommonCardPool.addToBottom(c);// 1487
            }

            var4 = commonCardPool.group.iterator();// 1489

            while(var4.hasNext()) {
                c = (AbstractCard)var4.next();
                srcCommonCardPool.addToBottom(c);// 1490
            }

            logger.info("Cardpool load time: " + (System.currentTimeMillis() - startTime) + "ms");// 1493
        }// 1494

        public static ArrayList<AbstractRelic> enigmatoremove = new ArrayList<>();

        public static void DoEnigmaShtuff() {
            ArrayList<AbstractRelic> relicsToAdd = new ArrayList<>();
            if (ActiveArtifacts.contains(Artifacts.ENIGMA)) {
                enigmatoremove.addAll(player.relics);
                enigmatoremove.removeIf(r -> r.tier == AbstractRelic.RelicTier.STARTER);
                AbstractDungeon.relicsToRemoveOnStart.clear();
                ReflectionHacks.privateMethod(AbstractDungeon.class, "initializeRelicList").invoke(dungeon);
                for (AbstractRelic r:
                     player.relics) {
                    if (r.tier != AbstractRelic.RelicTier.STARTER) {
                        relicsToAdd.add(GetActualNonScreenRelic(r.tier));
                    }
                }
                for (AbstractRelic r:
                        enigmatoremove) {
                    player.loseRelic(r.relicId);
                }
                for (AbstractRelic r:
                        relicsToAdd) {
                    r.instantObtain();
                }
            }



        }

        public static final ArrayList<String> NonScreenBlacklist = new ArrayList<String>() {{
            add(EmptyCage.ID);
            add(CallingBell.ID);
            add(PandorasBox.ID);
            add(Orrery.ID);
            add(Strawberry.ID);
            add(Pear.ID);
            add(Mango.ID);
            add(Waffle.ID);
            add(TinyHouse.ID);
        }};

        private static AbstractRelic GetActualNonScreenRelic(AbstractRelic.RelicTier r) {
            AbstractRelic relic;
            do {
                if (!(r == AbstractRelic.RelicTier.SPECIAL)) {
                    relic = AbstractDungeon.returnRandomScreenlessRelic(r);
                } else {
                    relic = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE);
                }
            } while (NonScreenBlacklist.contains(relic.relicId));



            return relic;
        }




        public enum Artifacts {
        SPITE,
        COMMAND,
        DEATH,
        HONOR,
        EVOLUTION,
        WEAKASSKNEES,
        CHAOS,
        GLASS,
        KIN,
        METAMORPHOSIS,
        DISSONANCE,
        ENIGMA,
        SACRIFICE,
        VENGEANCE,
        SWARMS,
        SOUL
    }

    public static Artifacts getArtifact(int artifactNum) {
        return Artifacts.values()[artifactNum];
    }
    public static Artifacts getArtifactfromName(String a) {
        return Artifacts.valueOf(a);
    }

    public static String getArtifactName(Artifacts a){
        UIStrings Str = CardCrawlGame.languagePack.getUIString(makeID(a.toString()));
        return Str.TEXT[0];


    }

    public static String getArtifactDescription(Artifacts a) {
        UIStrings Str = CardCrawlGame.languagePack.getUIString(makeID(a.toString()));
        return Str.TEXT[1];
    }


    @Override
    public void receivePostUpdate() {
        if (player != null) {
            List<Ego> playeregos = new ArrayList<>();
            for (AbstractRelic r:
                 player.relics) {
                if (r instanceof Ego) {
                    playeregos.add((Ego)r);
                }
            }

            for (Ego e:
                 playeregos) {
                e.postUpdate();
            }
            List<BackupMag> playerMags = new ArrayList<>();
            for (AbstractRelic r:
                    player.relics) {
                if (r instanceof BackupMag) {
                    playerMags.add((BackupMag)r);
                }
            }

            for (BackupMag b:
                    playerMags) {
                b.postUpdate();
            }
        }
    }
}
