//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package riskOfRelics.patches;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;
import com.megacrit.cardcrawl.rewards.chests.BossChest;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.shop.ShopScreen;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import org.clapper.util.classutil.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;


public class RandomMonsters {
    public static ArrayList<String> dungeonIDs = new ArrayList();
    public static ArrayList<String> encounterIDs = new ArrayList();
    public static ArrayList<String> eventIDs = new ArrayList();

    public RandomMonsters() {
    }// 58

    public static void addDungeonID(String id) {
        dungeonIDs.add(id);// 247
    }// 248

    public static void addEventID(String id) {
        eventIDs.add(id);// 251
    }// 252

    public static void addEncounterID(String id) {
        if (!encounterIDs.contains(id)) {// 255
            encounterIDs.add(id);// 256
        }
    }// 257

    public static ArrayList<String> starterDeck() {
        ArrayList<String> retVal = new ArrayList();// 261

        for(int i = 0; i <= (new Random()).random(11, 16); ++i) {// 262
            retVal.add(((AbstractCard)CardLibrary.getAllCards().get((new Random()).random(0, CardLibrary.getAllCards().size() - 1))).cardID);// 264
        }

        return retVal;// 266
    }

    public static ArrayList<String> starterRelics() {
        ArrayList<String> retVal = new ArrayList();// 270
        ArrayList<String> illegalRelics = new ArrayList<String>() {
            {
                this.add("War Paint");// 272
                this.add("Whetstone");// 273
                this.add("Bottled Flame");// 274
                this.add("Bottled Lightning");// 275
                this.add("Bottled Tornado");// 276
                this.add("DollysMirror");// 277
                this.add("Orrery");// 278
                this.add("Astrolabe");// 279
                this.add("Calling Bell");// 280
                this.add("Empty Cage");// 281
                this.add("Pandora's Box");// 282
                this.add("Tiny House");// 283
            }// 284
        };// 271
        String tmp = "";// 285
        int errCount = 0;// 286
        Random random = new Random();// 287

        do {
            try {
                if (errCount >= 20) {// 291
                    tmp = "Circlet";// 293
                    break;
                }

                tmp = ((AbstractRelic)RelicLibrary.uncommonList.get(random.random(0, RelicLibrary.uncommonList.size() - 1))).relicId;// 296
            } catch (Exception var6) {// 298
                tmp = "Orrery";// 300
                ++errCount;// 301
            }
        } while(illegalRelics.contains(tmp));// 303

        retVal.add(tmp);// 305
        return retVal;// 307
    }

    public static AbstractCard someFix(ShopScreen __instance, AbstractCard hover, AbstractCard c) {
        AbstractCard yeet = getRandomCard().makeCopy();// 522
        yeet.current_x = hover.current_x;// 523
        yeet.current_y = hover.current_y;// 524
        yeet.target_x = yeet.current_x;// 525
        yeet.target_y = yeet.current_y;// 526

        try {
            ReflectionHacks.getCachedMethod(ShopScreen.class, "setPrice", new Class[]{AbstractCard.class}).invoke(__instance, yeet);// 528
        } catch (InvocationTargetException | IllegalAccessException var5) {// 529
            throw new RuntimeException(var5);// 530
        }

        return __instance.colorlessCards.contains(hover) ? (AbstractCard)__instance.colorlessCards.set(__instance.colorlessCards.indexOf(hover), yeet) : (AbstractCard)__instance.coloredCards.set(__instance.coloredCards.indexOf(hover), yeet);// 532 535 539
    }

    public static AbstractCard getRandomCard() {
        AbstractCard card = (AbstractCard)CardLibrary.getAllCards().get(AbstractDungeon.cardRng.random(0, CardLibrary.getAllCards().size() - 1));// 545
        if (card.cardID.equals("Omega")) {// 546
            card = (AbstractCard)CardLibrary.getAllCards().get(AbstractDungeon.cardRng.random(0, CardLibrary.getAllCards().size() - 1));// 548
        }

        return card;// 550
    }

    public static void generateEvents() {
        AbstractDungeon.eventList.clear();// 645
        AbstractDungeon.eventList.addAll(eventIDs);// 646
        AbstractDungeon.eventList.addAll(RandomiserMain.allEventStrings);// 647
    }// 649

    public static void generateShrines() {
        AbstractDungeon.eventList.clear();// 652
        AbstractDungeon.shrineList.addAll(eventIDs);// 653
        AbstractDungeon.eventList.addAll(RandomiserMain.allEventStrings);// 654
    }// 655

    public static void generateMonsters(AbstractDungeon __instance) {
        generateWeakEnemies(1, __instance);// 659
        generateStrongEnemies(14, __instance);// 660
        generateElites(14, __instance);// 661
    }// 662

    protected static void generateWeakEnemies(int count, AbstractDungeon __instance) {
        ArrayList<MonsterInfo> monsters = new ArrayList();// 666
        Iterator var3 = encounterIDs.iterator();// 667

        String s;
        while(var3.hasNext()) {
            s = (String)var3.next();
            monsters.add(new MonsterInfo(s, 1.0F));// 669
        }

        var3 = RandomiserMain.allNonBossMonsterStrings.iterator();// 671

        while(var3.hasNext()) {
            s = (String)var3.next();
            monsters.add(new MonsterInfo(s, 1.0F));// 673
        }

        MonsterInfo.normalizeWeights(monsters);// 675
        __instance.populateMonsterList(monsters, count, false);// 676
    }// 677

    protected static void generateStrongEnemies(int count, AbstractDungeon __instance) {
        ArrayList<MonsterInfo> monsters = new ArrayList();// 680
        Iterator var3 = encounterIDs.iterator();// 681

        String s;
        while(var3.hasNext()) {
            s = (String)var3.next();
            monsters.add(new MonsterInfo(s, 1.0F));// 683
        }

        var3 = RandomiserMain.allNonBossMonsterStrings.iterator();// 685

        while(var3.hasNext()) {
            s = (String)var3.next();
            monsters.add(new MonsterInfo(s, 1.0F));// 687
        }

        var3 = RandomiserMain.allBossMonsterStrings.iterator();// 689

        while(var3.hasNext()) {
            s = (String)var3.next();
            monsters.add(new MonsterInfo(s, 1.0F));// 691
        }

        MonsterInfo.normalizeWeights(monsters);// 693
        __instance.populateFirstStrongEnemy(monsters, new ArrayList());// 694
        __instance.populateMonsterList(monsters, count, false);// 695
    }// 696

    protected static void generateElites(int count, AbstractDungeon __instance) {
        ArrayList<MonsterInfo> monsters = new ArrayList();// 699
        Iterator var3 = encounterIDs.iterator();// 700

        String s;
        while(var3.hasNext()) {
            s = (String)var3.next();
            monsters.add(new MonsterInfo(s, 1.0F));// 702
        }

        var3 = RandomiserMain.allNonBossMonsterStrings.iterator();// 704

        while(var3.hasNext()) {
            s = (String)var3.next();
            monsters.add(new MonsterInfo(s, 1.0F));// 706
        }

        var3 = RandomiserMain.allBossMonsterStrings.iterator();// 708

        while(var3.hasNext()) {
            s = (String)var3.next();
            monsters.add(new MonsterInfo(s, 1.0F));// 710
        }

        MonsterInfo.normalizeWeights(monsters);// 712
        __instance.populateMonsterList(monsters, count, true);// 713
    }// 714

    @SpirePatch(
            clz = ShopScreen.class,
            method = "init"
    )
    public static class ShopScreenRandomCards {
        public ShopScreenRandomCards() {
        }// 625

        @SpirePrefixPatch
        public static void Prefix(ShopScreen __instance, @ByRef ArrayList<AbstractCard>[] coloredCards, @ByRef ArrayList<AbstractCard>[] colorlessCards) {
            coloredCards[0] = new ArrayList();// 630
            colorlessCards[0] = new ArrayList();// 631

            int i;
            for(i = 0; i < 5; ++i) {// 632
                coloredCards[0].add(RandomMonsters.getRandomCard().makeCopy());// 634
            }

            for(i = 0; i < 2; ++i) {// 636
                colorlessCards[0].add(RandomMonsters.getRandomCard().makeCopy());// 638
            }

        }// 640
    }

    @SpirePatch(
            clz = CardLibrary.class,
            method = "getAnyColorCard",
            paramtypez = {AbstractCard.CardType.class, AbstractCard.CardRarity.class}
    )
    public static class AntiShard2 {
        public AntiShard2() {
        }// 615

        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> Prefix(AbstractCard.CardType type, AbstractCard.CardRarity rarity) {
            return SpireReturn.Return(RandomMonsters.getRandomCard());// 620
        }
    }

    @SpirePatch(
            clz = CardLibrary.class,
            method = "getAnyColorCard",
            paramtypez = {AbstractCard.CardRarity.class}
    )
    public static class AntiShard {
        public AntiShard() {
        }// 606

        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> Prefix(AbstractCard.CardRarity rarity) {
            return SpireReturn.Return(RandomMonsters.getRandomCard());// 611
        }
    }

    @SpirePatch(
            clz = AbstractRelic.class,
            method = "<ctor>"
    )
    public static class AllRelicsUncommon {
        public AllRelicsUncommon() {
        }// 596

        @SpirePostfixPatch
        public static void Postfix(AbstractRelic __instance) {
            __instance.tier = RelicTier.UNCOMMON;// 601
        }// 602
    }

    @SpirePatch(
            clz = BossChest.class,
            method = "<ctor>"
    )
    public static class UncommonBossRelic {
        public UncommonBossRelic() {
        }// 581

        @SpirePostfixPatch
        public static void Postfix(BossChest __instance) {
            if (__instance.relics.size() > 0) {// 586
                __instance.relics = new ArrayList();// 588

                for(int i = 0; i < 3; ++i) {// 589
                    __instance.relics.add(AbstractDungeon.returnRandomRelic(RelicTier.UNCOMMON));// 590
                }
            }

        }// 592
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "returnRandomRelicTier"
    )
    public static class UncommonDungeonRelics {
        public UncommonDungeonRelics() {
        }// 572

        @SpirePrefixPatch
        public static SpireReturn<AbstractRelic.RelicTier> Prefix() {
            return SpireReturn.Return(RelicTier.UNCOMMON);// 577
        }
    }

    @SpirePatch(
            clz = MonsterRoomElite.class,
            method = "returnRandomRelicTier"
    )
    public static class UncommonEliteRelics {
        public UncommonEliteRelics() {
        }// 563

        @SpirePrefixPatch
        public static SpireReturn<AbstractRelic.RelicTier> Prefix(MonsterRoomElite __instance) {
            return SpireReturn.Return(RelicTier.UNCOMMON);// 568
        }
    }

    @SpirePatch(
            clz = MonsterRoom.class,
            method = "returnRandomRelicTier"
    )
    public static class UncommonRelics {
        public UncommonRelics() {
        }// 554

        @SpirePrefixPatch
        public static SpireReturn<AbstractRelic.RelicTier> Prefix(MonsterRoom __instance) {
            return SpireReturn.Return(RelicTier.UNCOMMON);// 559
        }
    }

    @SpirePatch(
            clz = AbstractRelic.class,
            method = "getPrice"
    )
    public static class PriceHack2 {
        public PriceHack2() {
        }// 511

        @SpirePrefixPatch
        public static SpireReturn<Integer> Prefix(AbstractRelic __instance) {
            return SpireReturn.Return(AbstractDungeon.miscRng.random(50, 400));// 516
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "getPrice"
    )
    public static class PriceHack {
        public PriceHack() {
        }// 495

        @SpirePrefixPatch
        public static SpireReturn<Integer> Prefix(AbstractCard.CardRarity rarity) {
            switch (rarity) {// 500
                case CURSE:
                    return SpireReturn.Return(10);// 503
                default:
                    return SpireReturn.Return(AbstractDungeon.miscRng.random(1, 300));// 505
            }
        }
    }

    @SpirePatch(
            clz = ShopScreen.class,
            method = "purchaseCard"
    )
    public static class whyDoesCourierSuck {
        public whyDoesCourierSuck() {
        }// 476

        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {// 481
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(ArrayList.class.getName()) && m.getMethodName().equals("set")) {// 485
                        m.replace("{$_ = randomiser.patches.RandomMonsters.someFix(this, hoveredCard, c);}");// 487
                    }

                }// 489
            };
        }
    }

    @SpirePatch(
            clz = MonsterRoomBoss.class,
            method = "onPlayerEntry"
    )
    public static class BossYeet {
        public BossYeet() {
        }// 445

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(MonsterRoomBoss __instance) {
            AtomicBoolean f = new AtomicBoolean(false);// 450
            __instance.monsters.monsters.forEach((m) -> {// 451
                if (m.id.equals("CorruptHeart")) {// 452
                    f.set(true);// 454
                }

            });// 456
            if (!f.get()) {
                ArrayList<String> possBoss = new ArrayList();// 458
                possBoss.addAll(RandomMonsters.encounterIDs);// 459
                possBoss.addAll(RandomiserMain.allNonBossMonsterStrings);// 460
                possBoss.addAll(RandomiserMain.allBossMonsterStrings);// 461
                Collections.shuffle(possBoss, new java.util.Random(AbstractDungeon.monsterRng.randomLong()));// 462
                __instance.monsters = MonsterHelper.getEncounter((String)possBoss.get(0));// 463
            }
        }// 457 464

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }// 465

            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "size");// 469
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList(), finalMatcher);// 470
            }
        }
    }

    @SpirePatch(
            clz = CombatRewardScreen.class,
            method = "setupItemReward"
    )
    public static class ScuffedRewards {
        public ScuffedRewards() {
        }// 383

        @SpirePostfixPatch
        public static void Postfix(CombatRewardScreen __instance) {
            __instance.rewards = new ArrayList();// 388

            for(boolean gold = AbstractDungeon.miscRng.randomBoolean(); gold; gold = AbstractDungeon.miscRng.random(0, 2) == 1) {// 389 393
                __instance.rewards.add(new RewardItem(AbstractDungeon.miscRng.random(15, 100)));// 392
            }

            int temp = AbstractDungeon.miscRng.random(0, 2);// 395
            boolean cards = temp == 0 || temp == 1;// 396
            int errCount = 0;// 397

            while(cards) {
                try {
                    __instance.rewards.add(new RewardItem());// 402
                    cards = AbstractDungeon.miscRng.randomBoolean();// 403
                } catch (Exception var7) {// 405
                    cards = true;// 407
                    ++errCount;// 408
                    if (errCount > 20) {// 409
                        cards = false;// 411
                    }
                }
            }

            boolean relics = AbstractDungeon.miscRng.random(0, 5) == 2;// 415
            if (Settings.isFinalActAvailable && !Settings.hasSapphireKey && AbstractDungeon.getCurrRoom() instanceof TreasureRoom) {// 416
                __instance.rewards.add(new RewardItem(AbstractDungeon.returnRandomRelic(RelicTier.UNCOMMON)));// 418
                __instance.rewards.add(new RewardItem((RewardItem)__instance.rewards.get(__instance.rewards.size() - 1), RewardType.SAPPHIRE_KEY));// 419
            }

            if (Settings.isFinalActAvailable && !Settings.hasEmeraldKey && AbstractDungeon.getCurrMapNode().hasEmeraldKey) {// 421
                __instance.rewards.add(new RewardItem(AbstractDungeon.returnRandomRelic(RelicTier.UNCOMMON)));// 423
                __instance.rewards.add(new RewardItem((RewardItem)__instance.rewards.get(__instance.rewards.size() - 1), RewardType.EMERALD_KEY));// 424
            }

            while(relics) {
                __instance.rewards.add(new RewardItem(AbstractDungeon.returnRandomRelic(RelicTier.UNCOMMON)));// 428
                AbstractDungeon.player.relics.stream().filter((r) -> {// 429
                    return r.relicId.equals("Black Star");
                }).forEach((bs) -> {
                    __instance.rewards.add(new RewardItem(AbstractDungeon.returnRandomRelic(RelicTier.UNCOMMON)));// 430
                });// 431
                relics = AbstractDungeon.miscRng.random(0, 4) == 2;// 432
            }

            for(boolean potions = AbstractDungeon.miscRng.random(0, 3) == 1; potions; potions = AbstractDungeon.miscRng.random(0, 3) == 1) {// 434 438
                __instance.rewards.add(new RewardItem(PotionHelper.getRandomPotion()));// 437
            }

            __instance.positionRewards();// 440
        }// 441
    }

    @SpirePatch(
            clz = CharSelectInfo.class,
            method = "<ctor>",
            paramtypez = {String.class, String.class, int.class, int.class, int.class, int.class, int.class, AbstractPlayer.class, ArrayList.class, ArrayList.class, boolean.class}
    )
    public static class HpGoldCurse {
        public HpGoldCurse() {
        }// 369

        @SpirePostfixPatch
        public static void Postfix(CharSelectInfo __instance, String name, String flavorText, int currentHp, int maxHp, int maxOrbs, int gold, int cardDraw, AbstractPlayer player, ArrayList<String> relics, ArrayList<String> deck, boolean resumeGame) {
            int newHP = (new Random()).random(40, 120);// 374
            __instance.currentHp = newHP;// 375
            __instance.maxHp = newHP;// 376
            __instance.gold = (new Random()).random(0, 250);// 377
            __instance.maxOrbs = (new Random()).random(1, 3);// 378
        }// 379
    }

    @SpirePatch(
            clz = NeowEvent.class,
            method = "<ctor>",
            paramtypez = {boolean.class}
    )
    public static class Eggy {
        public Eggy() {
        }// 331

        @SpirePostfixPatch
        public static void Postfix(NeowEvent __instance, boolean b) {
            AbstractDungeon.player.relics.forEach((r) -> {// 336
                if (r.relicId.equals("Toxic Egg 2")) {// 337
                    AbstractDungeon.player.masterDeck.group.forEach((c) -> {// 339
                        if (c.type.equals(CardType.SKILL)) {// 340
                            c.upgrade();// 342
                        }

                    });// 344
                }

                if (r.relicId.equals("Frozen Egg 2")) {// 346
                    AbstractDungeon.player.masterDeck.group.forEach((c) -> {// 348
                        if (c.type.equals(CardType.POWER)) {// 349
                            c.upgrade();// 351
                        }

                    });// 353
                }

                if (r.relicId.equals("Molten Egg 2")) {// 355
                    AbstractDungeon.player.masterDeck.group.forEach((c) -> {// 357
                        if (c.type.equals(CardType.ATTACK)) {// 358
                            c.upgrade();// 360
                        }

                    });// 362
                }

            });// 364
        }// 365
    }

    @SpirePatch(
            clz = NeowReward.class,
            method = "activate"
    )
    public static class NeowRelicTierFix {
        public NeowRelicTierFix() {
        }// 312

        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {// 317
                public void edit(FieldAccess f) throws CannotCompileException {
                    if (f.getClassName().equals(AbstractRelic.RelicTier.class.getName()) && (f.getFieldName().equals("RARE") || f.getFieldName().equals("COMMON") || f.getFieldName().equals("BOSS"))) {// 321
                        f.replace("{$_ = com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON;}");// 323
                    }

                }// 325
            };
        }
    }

    @SpirePatch(
            clz = CardCrawlGame.class,
            method = "<ctor>"
    )
    public static class HellItselfHasOpened {
        public HellItselfHasOpened() {
        }// 61

        @SpireRawPatch
        public static void Rawr(CtBehavior ctBehavior) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
            ClassFinder finder = new ClassFinder();// 65
            finder.add(new File(Loader.STS_JAR));// 66
            ModInfo[] var2 = Loader.MODINFOS;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {// 68
                ModInfo info = var2[var4];
                if (info.jarURL != null) {// 69
                    try {
                        finder.add(new File(info.jarURL.toURI()));// 71
                    } catch (URISyntaxException var17) {// 72
                    }
                }
            }

            ClassPool pool = ctBehavior.getDeclaringClass().getClassPool();// 78
            ClassFilter abstractDungeonFilter = new AndClassFilter(new ClassFilter[]{new NotClassFilter(new InterfaceOnlyClassFilter()), new ClassModifiersClassFilter(1), new OrClassFilter(new ClassFilter[]{new SubclassClassFilter(AbstractDungeon.class), (classInfox, classFinder) -> {// 80
                return classInfox.getClassName().equals(AbstractDungeon.class.getName());// 85
            }})});
            ClassFilter abstractPlayerFilter = new AndClassFilter(new ClassFilter[]{new NotClassFilter(new InterfaceOnlyClassFilter()), new ClassModifiersClassFilter(1), new OrClassFilter(new ClassFilter[]{new SubclassClassFilter(AbstractPlayer.class), (classInfox, classFinder) -> {// 89
                return classInfox.getClassName().equals(AbstractPlayer.class.getName());// 94
            }})});
            ClassFilter baseModFilter = (classInfox, classFinder) -> {
                return classInfox.getClassName().equals(BaseMod.class.getName());
            };// 98
            ArrayList<ClassInfo> abstractDungeonFoundClasses = new ArrayList();// 100
            finder.findClasses(abstractDungeonFoundClasses, abstractDungeonFilter);// 101
            ArrayList<ClassInfo> abstractPlayerFoundClasses = new ArrayList();// 103
            finder.findClasses(abstractPlayerFoundClasses, abstractPlayerFilter);// 104
            ArrayList<ClassInfo> basemodFoundClasses = new ArrayList();// 106
            finder.findClasses(basemodFoundClasses, baseModFilter);// 107
            Iterator var9 = basemodFoundClasses.iterator();// 109

            ClassInfo classInfo;
            CtClass ctClass;
            CtMethod[] var12;
            int var13;
            int var14;
            CtMethod m;
            while(var9.hasNext()) {
                classInfo = (ClassInfo)var9.next();
                ctClass = pool.get(classInfo.getClassName());// 111
                var12 = ctClass.getDeclaredMethods();
                var13 = var12.length;

                for(var14 = 0; var14 < var13; ++var14) {// 112
                    m = var12[var14];
                    if (m.getName().equals("addEvent")) {// 114
                        if (m.getParameterTypes().length == 1) {// 116
                            m.insertBefore("{randomiser.patches.RandomMonsters.addEventID(params.eventID);}");// 118
                        } else {
                            m.insertBefore("{randomiser.patches.RandomMonsters.addEventID(eventID);}");// 122
                        }
                    }

                    if (m.getName().equals("addMonster")) {// 125
                        m.insertBefore("{randomiser.patches.RandomMonsters.addEncounterID(encounterID);}");// 127
                    }
                }
            }

            var9 = abstractPlayerFoundClasses.iterator();// 132

            while(var9.hasNext()) {
                classInfo = (ClassInfo)var9.next();
                ctClass = pool.get(classInfo.getClassName());// 134
                var12 = ctClass.getDeclaredMethods();
                var13 = var12.length;

                for(var14 = 0; var14 < var13; ++var14) {// 135
                    m = var12[var14];
                    if (m.getName().equals("getStartingDeck") && !m.isEmpty() && !Modifier.isNative(m.getModifiers())) {// 137
                        m.insertAfter("{return ($r) randomiser.patches.RandomMonsters.starterDeck();}");// 139
                    }

                    if (m.getName().equals("getStartingRelics") && !m.isEmpty() && !Modifier.isNative(m.getModifiers())) {// 143
                        m.insertAfter("{return ($r) randomiser.patches.RandomMonsters.starterRelics();}");// 145
                    }

                    if (m.getName().equals("initializeStarterRelics") && !m.isEmpty() && !Modifier.isNative(m.getModifiers())) {// 149
                        CtClass etype = ClassPool.getDefault().get("java.lang.Exception");// 151
                        m.addCatch("{ initializeStarterRelics(chosenClass); return; }", etype);// 152
                    }
                }
            }

            var9 = abstractDungeonFoundClasses.iterator();// 167

            while(var9.hasNext()) {
                classInfo = (ClassInfo)var9.next();
                ctClass = ctBehavior.getDeclaringClass().getClassPool().get(classInfo.getClassName());// 169
                var12 = ctClass.getDeclaredMethods();
                var13 = var12.length;

                for(var14 = 0; var14 < var13; ++var14) {// 172
                    m = var12[var14];
                    if (m.getName().equals("generateMonsters") && !m.isEmpty() && !Modifier.isNative(m.getModifiers())) {// 174
                        m.insertBefore("{randomiser.patches.RandomMonsters.generateMonsters(this);return;}");// 176
                    }

                    if (m.getName().equals("initializeLevelSpecificChances") && !m.isEmpty() && !Modifier.isNative(m.getModifiers())) {// 181
                        m.insertAfter("{shopRoomChance = 0.15F;\nrestRoomChance = 0.1F;\ntreasureRoomChance = 0.1F;\neventRoomChance = 0.15F;\neliteRoomChance = 0.08F;\nsmallChestChance = 34;\nmediumChestChance = 33;\nlargeChestChance = 33;\ncommonRelicChance = 50;\nuncommonRelicChance = 33;\nrareRelicChance = 17;\ncolorlessRareChance = 0.5F;\ncardUpgradedChance = 0.2F;}");// 183
                    }

                    if ((m.getName().equals("returnRandomCard") || m.getName().equals("returnTrulyRandomCard") || m.getName().equals("returnTrulyRandomCardInCombat") || m.getName().equals("returnTrulyRandomColorlessCardInCombat") || m.getName().equals("returnTrulyRandomColorlessCardFromAvailable") || m.getName().equals("returnTrulyRandomCardFromAvailable") || m.getName().equals("getCard")) && !m.isEmpty() && !Modifier.isNative(m.getModifiers())) {// 199 200
                        m.insertBefore("{return randomiser.patches.RandomMonsters.getRandomCard();}");// 202
                    }

                    if (m.getName().equals("initializeEventList") && !m.isEmpty() && !Modifier.isNative(m.getModifiers())) {// 206
                        m.insertBefore("{randomiser.patches.RandomMonsters.generateEvents();return;}");// 208
                    }

                    if (m.getName().equals("initializeShrineList") && !m.isEmpty() && !Modifier.isNative(m.getModifiers())) {// 213
                        m.insertBefore("{randomiser.patches.RandomMonsters.generateShrines();return;}");// 215
                    }
                }
            }

        }// 240
    }
}
