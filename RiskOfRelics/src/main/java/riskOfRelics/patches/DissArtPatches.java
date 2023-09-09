//


//

package riskOfRelics.patches;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireRawPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import javassist.*;
import org.clapper.util.classutil.*;
import riskOfRelics.bosses.BulwarksAmbry;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class DissArtPatches {
    public static List<MonsterInfo> WeakMonsterStrings;
    public static List<MonsterInfo> NormalMonsterStrings;
    public static List<MonsterInfo> EliteMonsterStrings;
    public static List<String> BossStrings;
    public static ArrayList<String> dungeonIDs = new ArrayList();
    public static ArrayList<String> weakencounterIDs = new ArrayList();
    public static ArrayList<String> strongencounterIDs = new ArrayList();
    public static ArrayList<String> eliteencounterIDs = new ArrayList();


    public DissArtPatches() {
    }
    static {

        WeakMonsterStrings = new ArrayList<MonsterInfo>() {
            {
                this.add(new MonsterInfo("Cultist", 2.0F));// 155
                this.add(new MonsterInfo("Jaw Worm", 2.0F));// 156
                this.add(new MonsterInfo("2 Louse", 2.0F));// 157
                this.add(new MonsterInfo("Small Slimes", 2.0F));// 158
                
                this.add(new MonsterInfo("Spheric Guardian", 2.0F));// 127
                this.add(new MonsterInfo("Chosen", 2.0F));// 128
                this.add(new MonsterInfo("Shell Parasite", 2.0F));// 129
                this.add(new MonsterInfo("3 Byrds", 2.0F));// 130
                this.add(new MonsterInfo("2 Thieves", 2.0F));// 131

                this.add(new MonsterInfo("3 Darklings", 2.0F));// 118
                this.add(new MonsterInfo("Orb Walker", 2.0F));// 119
                this.add(new MonsterInfo("3 Shapes", 2.0F));// 120

                //this.add(new MonsterInfo("The Mushroom Lair", 2.0F));// 120


                



            }
        };
        NormalMonsterStrings = new ArrayList<MonsterInfo>() {
            {
                this.add(new MonsterInfo("Blue Slaver", 2.0F));// 165
                this.add(new MonsterInfo("Gremlin Gang", 1.0F));// 166
                this.add(new MonsterInfo("Looter", 2.0F));// 167
                this.add(new MonsterInfo("Large Slime", 2.0F));// 168
                this.add(new MonsterInfo("Lots of Slimes", 1.0F));// 169
                this.add(new MonsterInfo("Exordium Thugs", 1.5F));// 170
                this.add(new MonsterInfo("Exordium Wildlife", 1.5F));// 171
                this.add(new MonsterInfo("Red Slaver", 1.0F));// 172
                this.add(new MonsterInfo("3 Louse", 2.0F));// 173
                this.add(new MonsterInfo("2 Fungi Beasts", 2.0F));// 174

                this.add(new MonsterInfo("Chosen and Byrds", 2.0F));// 138
                this.add(new MonsterInfo("Sentry and Sphere", 2.0F));// 139
                this.add(new MonsterInfo("Snake Plant", 6.0F));// 140
                this.add(new MonsterInfo("Snecko", 4.0F));// 141
                this.add(new MonsterInfo("Centurion and Healer", 6.0F));// 142
                this.add(new MonsterInfo("Cultist and Chosen", 3.0F));// 143
                this.add(new MonsterInfo("3 Cultists", 3.0F));// 144
                this.add(new MonsterInfo("Shelled Parasite and Fungi", 3.0F));// 145

                this.add(new MonsterInfo("Spire Growth", 1.0F));// 127
                this.add(new MonsterInfo("Transient", 1.0F));// 128
                this.add(new MonsterInfo("4 Shapes", 1.0F));// 129
                this.add(new MonsterInfo("Maw", 1.0F));// 130
                this.add(new MonsterInfo("Sphere and 2 Shapes", 1.0F));// 131
                this.add(new MonsterInfo("Jaw Worm Horde", 1.0F));// 132
                this.add(new MonsterInfo("3 Darklings", 1.0F));// 133
                this.add(new MonsterInfo("Writhing Mass", 1.0F));// 134
            }
        };
        EliteMonsterStrings = new ArrayList<MonsterInfo>() {
            {
                this.add(new MonsterInfo("Snecko and Mystics", 2.0F));// 120

                this.add(new MonsterInfo("Gremlin Nob", 1.0F));// 182
                this.add(new MonsterInfo("Lagavulin", 1.0F));// 183
                this.add(new MonsterInfo("3 Sentries", 1.0F));// 184
                
                this.add(new MonsterInfo("Gremlin Leader", 1.0F));// 153
                this.add(new MonsterInfo("Slavers", 1.0F));// 154
                this.add(new MonsterInfo("Book of Stabbing", 1.0F));// 155
                
                this.add(new MonsterInfo("Giant Head", 2.0F));// 142
                this.add(new MonsterInfo("Nemesis", 2.0F));// 143
                this.add(new MonsterInfo("Reptomancer", 2.0F));// 144

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

    public static void addWeakEncounterID(String id) {
        if (!weakencounterIDs.contains(id)) {
            weakencounterIDs.add(id);
        }

    }
    public static void addStrongEncounterID(String id) {
        if (!strongencounterIDs.contains(id)) {
            strongencounterIDs.add(id);
        }
    }
    public static void addEliteEncounterID(String DungeonID,String id) {
        BulwarksAmbry.addEliteEncounterID(DungeonID,id);
        if (!eliteencounterIDs.contains(id)) {
            eliteencounterIDs.add(id);
        }
    }
    public static void addBossID(String id) {
        if (!BossStrings.contains(id)) {
            BossStrings.add(id);
            weakencounterIDs.remove(id);
        }
    }

    public static void generateMonsters(AbstractDungeon __instance) {


            generateWeakEnemies(1, __instance);
            generateStrongEnemies(14, __instance);
            generateElites(14, __instance);



    }

    // function to get a random encounter
    public static String getTrulyRandomEliteEncounter() {



        ArrayList<String> encounterIDs = new ArrayList<>();

        encounterIDs.addAll(eliteencounterIDs);
        EliteMonsterStrings.forEach((monsterInfo) -> {
            encounterIDs.add(monsterInfo.name);
        });


        return encounterIDs.get(AbstractDungeon.monsterRng.random(encounterIDs.size() - 1));
    }

    protected static void generateWeakEnemies(int count, AbstractDungeon __instance) {
        ArrayList<MonsterInfo> monsters = new ArrayList<>();
        for (String s : weakencounterIDs) {
            monsters.add(new MonsterInfo(s, 1.0F));
        }
        for(MonsterInfo s : WeakMonsterStrings) {
            monsters.add(new MonsterInfo(s.name, 1.0F));
        }

        MonsterInfo.normalizeWeights(monsters);
        __instance.populateMonsterList(monsters, count, false);
    }

    protected static void generateStrongEnemies(int count, AbstractDungeon __instance) {
        ArrayList<MonsterInfo> monsters = new ArrayList<>();
        for (String s : strongencounterIDs) {
            monsters.add(new MonsterInfo(s, 1.0F));
        }
        for(MonsterInfo s : NormalMonsterStrings) {
            monsters.add(new MonsterInfo(s.name, 1.0F));
        }


        MonsterInfo.normalizeWeights(monsters);
        __instance.populateFirstStrongEnemy(monsters, new ArrayList<>());
        __instance.populateMonsterList(monsters, count, false);
    }

    protected static void generateElites(int count, AbstractDungeon __instance) {
        ArrayList<MonsterInfo> monsters = new ArrayList();
        for (String s : eliteencounterIDs) {
            monsters.add(new MonsterInfo(s, 1.0F));
        }
        for(MonsterInfo s : EliteMonsterStrings) {
            monsters.add(new MonsterInfo(s.name, 1.0F));
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


            CtClass ctClass = pool.get(BaseMod.class.getName());
            CtMethod[] methods = ctClass.getDeclaredMethods();
                for(CtMethod m : methods) {


                    if (m.getName() .equals("addMonsterEncounter")) {
                        m.insertBefore("{riskOfRelics.patches.DissArtPatches.addWeakEncounterID(encounter.name);}");
                    }
                    if (m.getName().equals("addStrongMonsterEncounter")) {
                        m.insertBefore("{riskOfRelics.patches.DissArtPatches.addStrongEncounterID(encounter.name);}");
                    }
                    if (m.getName().equals("addEliteEncounter")) {
                        m.insertBefore("{riskOfRelics.patches.DissArtPatches.addEliteEncounterID(dungeonID, encounter.name);}");
                    }

                    if (m.getName().equals("addBoss")) {
                        m.insertBefore("{riskOfRelics.patches.DissArtPatches.addBossID(bossID);}");
                    }

            }

            for(ClassInfo classInfo : abstractDungeonFoundClasses) {

                ctClass = ctBehavior.getDeclaringClass().getClassPool().get(classInfo.getClassName());
                methods = ctClass.getDeclaredMethods();
                if (!classInfo.getClassName().equals(TheEnding.class.getName())) {
                    for (CtMethod m : methods) {
                        if (m.getName().equals("generateMonsters") && !m.isEmpty() && !Modifier.isNative(m.getModifiers())) {
                            m.insertBefore("{" +
                                    "if (riskOfRelics.RiskOfRelics.ActiveArtifacts.contains(riskOfRelics.RiskOfRelics.Artifacts.DISSONANCE)){" +
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
