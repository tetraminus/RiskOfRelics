//


//

package riskOfRelics.patches;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import javassist.*;
import org.clapper.util.classutil.*;
import riskOfRelics.RiskOfRelics;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DissArtPatches {
    public static List<String> MonsterStrings;
    public static List<String> BossStrings;
    public static ArrayList<String> dungeonIDs = new ArrayList();
    public static ArrayList<String> encounterIDs = new ArrayList();


    public DissArtPatches() {
    }
    static {

        MonsterStrings = new ArrayList<String>() {
            {
                this.add("Blue Slaver");
                this.add("Cultist");
                this.add("Jaw Worm");
                this.add("Looter");
                this.add("2 Louse");
                this.add("Small Slimes");
                this.add("Gremlin Gang");
                this.add("Red Slaver");
                this.add("Large Slime");
                this.add("Exordium Thugs");
                this.add("Exordium Wildlife");
                this.add("3 Louse");
                this.add("2 Fungi Beasts");
                this.add("Lots of Slimes");
                this.add("Gremlin Nob");
                this.add("Lagavulin Event");
                this.add("Lagavulin");
                this.add("3 Sentries");
                this.add("The Mushroom Lair");
                this.add("2 Thieves");
                this.add("3 Byrds");
                this.add("Chosen");
                this.add("Shell Parasite");
                this.add("Spheric Guardian");
                this.add("Cultist and Chosen");
                this.add("3 Cultists");
                this.add("4 Byrds");
                this.add("Chosen and Byrds");
                this.add("Sentry and Sphere");
                this.add("Snake Plant");
                this.add("Snecko");
                this.add("Centurion and Healer");
                this.add("Shelled Parasite and Fungi");
                this.add("Book of Stabbing");
                this.add("Gremlin Leader");
                this.add("Slavers");
                this.add("Masked Bandits");
                this.add("Colosseum Slavers");
                this.add("Colosseum Nobs");
                this.add("3 Darklings");
                this.add("3 Shapes");
                this.add("Orb Walker");
                this.add("Transient");
                this.add("Reptomancer");
                this.add("Spire Growth");
                this.add("Maw");
                this.add("4 Shapes");
                this.add("Sphere and 2 Shapes");
                this.add("Jaw Worm Horde");
                this.add("Snecko and Mystics");
                this.add("Writhing Mass");
                this.add("2 Orb Walkers");
                this.add("Nemesis");
                this.add("Giant Head");
                this.add("Mysterious Sphere");
                this.add("Mind Bloom Boss Battle");
                this.add("Shield and Spear");
                this.add("The Eyes");
                this.add("Apologetic Slime");
                this.add("Flame Bruiser 1 Orb");
                this.add("Flame Bruiser 2 Orb");
                this.add("Slaver and Parasite");
            }
        };
        BossStrings = new ArrayList() {
            {
                this.add("The Guardian");
                this.add("Hexaghost");
                this.add("Slime Boss");
                this.add("Automaton");
                this.add("Champ");
                this.add("Collector");
                this.add("Time Eater");
                this.add("Awakened One");
                this.add("Donu and Deca");
            }
        };
    }

    public static void addDungeonID(String id) {
        dungeonIDs.add(id);
    }

    public static void addEncounterID(String id) {
        if (!encounterIDs.contains(id)) {
            encounterIDs.add(id);
        }
    }

    public static void generateMonsters(AbstractDungeon __instance) {
        generateWeakEnemies(1, __instance);
        generateStrongEnemies(14, __instance);
        generateElites(14, __instance);
    }

    protected static void generateWeakEnemies(int count, AbstractDungeon __instance) {
        ArrayList<MonsterInfo> monsters = new ArrayList<>();
        for (String s : encounterIDs) {
            monsters.add(new MonsterInfo(s, 1.0F));
        }
        for(String s : MonsterStrings) {
            monsters.add(new MonsterInfo(s, 1.0F));
        }

        MonsterInfo.normalizeWeights(monsters);
        __instance.populateMonsterList(monsters, count, false);
    }

    protected static void generateStrongEnemies(int count, AbstractDungeon __instance) {
        ArrayList<MonsterInfo> monsters = new ArrayList<>();
        for (String s : encounterIDs) {
            monsters.add(new MonsterInfo(s, 1.0F));
        }
        for(String s : MonsterStrings) {
            monsters.add(new MonsterInfo(s, 1.0F));
        }

        MonsterInfo.normalizeWeights(monsters);
        __instance.populateFirstStrongEnemy(monsters, new ArrayList<>());
        __instance.populateMonsterList(monsters, count, false);
    }

    protected static void generateElites(int count, AbstractDungeon __instance) {
        ArrayList<MonsterInfo> monsters = new ArrayList();
        for (String s : encounterIDs) {
            monsters.add(new MonsterInfo(s, 1.0F));
        }
        for(String s : MonsterStrings) {
            monsters.add(new MonsterInfo(s, 1.0F));
        }

        MonsterInfo.normalizeWeights(monsters);
        __instance.populateMonsterList(monsters, count, true);
    }
    @SpirePatch(
            clz = CardCrawlGame.class,
            method = "<ctor>"
    )
    public static class PatchAllDungeons {

        @SpireRawPatch
        public static void Raw(CtBehavior ctBehavior) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
            ClassFinder finder = new ClassFinder();
            finder.add(new File(Loader.STS_JAR));
            ModInfo[] AllMods = Loader.MODINFOS;
            

            for (ModInfo info : AllMods) {
                if (info.jarURL != null) {
                    try {
                        finder.add(new File(info.jarURL.toURI()));
                    } catch (URISyntaxException ignored) {
                    }
                }
            }

            ClassPool pool = ctBehavior.getDeclaringClass().getClassPool();
            ClassFilter abstractDungeonFilter = new AndClassFilter(new NotClassFilter(new InterfaceOnlyClassFilter()), new ClassModifiersClassFilter(1), new OrClassFilter(new SubclassClassFilter(AbstractDungeon.class), (classInfox, classFinder) -> {
                return classInfox.getClassName().equals(AbstractDungeon.class.getName());
            }));
            
            ClassFilter baseModFilter = (classInfox, classFinder) -> {
                return classInfox.getClassName().equals(BaseMod.class.getName());
            };
            ArrayList<ClassInfo> abstractDungeonFoundClasses = new ArrayList<>();
            finder.findClasses(abstractDungeonFoundClasses, abstractDungeonFilter);
            ArrayList<ClassInfo> basemodFoundClasses = new ArrayList<>();
            finder.findClasses(basemodFoundClasses, baseModFilter);



            CtClass ctClass;
            CtMethod[] methods;
            
            for (ClassInfo classInfo : basemodFoundClasses) {
                ctClass = pool.get(classInfo.getClassName());
                methods = ctClass.getDeclaredMethods();
                for(CtMethod m : methods) {

                    if (m.getName().equals("addMonster")) {
                        m.insertBefore("{riskOfRelics.patches.DissArtPatches.addEncounterID(encounterID);}");
                    }
                }
            }

            for(ClassInfo classInfo : abstractDungeonFoundClasses) {

                ctClass = ctBehavior.getDeclaringClass().getClassPool().get(classInfo.getClassName());
                methods = ctClass.getDeclaredMethods();
                if (!classInfo.getClassName().equals(TheEnding.class.getName())) {
                    for (CtMethod m : methods) {
                        if (m.getName().equals("generateMonsters") && !m.isEmpty() && !Modifier.isNative(m.getModifiers())) {
                            m.insertBefore("{" +
                                    "if (riskOfRelics.RiskOfRelics.ActiveArtifacts.contains(riskOfRelics.RiskOfRelics.Artifacts.DISSONANCE)) {" +
                                    "riskOfRelics.patches.DissArtPatches.generateMonsters(this);" +
                                    "return;" +
                                    "}" +
                                    "}");
                        }
                        if (m.getName().equals("initializeBoss") && !m.isEmpty() && !Modifier.isNative(m.getModifiers())) {
                            m.insertBefore("{" +
                                    "if (riskOfRelics.RiskOfRelics.ActiveArtifacts.contains(riskOfRelics.RiskOfRelics.Artifacts.DISSONANCE) ) {" +
                                    "for (int i = 0; i < riskOfRelics.patches.DissArtPatches.BossStrings.size(); i++) {" +
                                    "bossList.add(riskOfRelics.patches.DissArtPatches.BossStrings.get(i));" +
                                    "}" +
                                    "java.util.Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));" +
                                    "return;" +
                                    "}" +
                                    "}");
                        }

                    }
                }
            }

        }
    }
}
