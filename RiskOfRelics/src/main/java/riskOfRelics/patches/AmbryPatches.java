package riskOfRelics.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.*;
import downfall.patches.EvilModeCharacterSelect;
import downfall.patches.ui.map.FlipMap;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import riskOfRelics.relics.YellowKey;
import riskOfRelics.rooms.AmbrySelectRoom;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.fadeIn;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static riskOfRelics.RiskOfRelics.makeBossPath;
import static riskOfRelics.RiskOfRelics.makeID;


public class AmbryPatches {// Don't worry about the "never used" warning - *You* usually don't use/call them anywhere. Mod The Spire does.
    
    // You can have as many inner classes with patches as you want inside this one - you don't have to separate each patch into it's own file.
    // So if you need to put 4 patches all for 1 purpose (for example they all make a specific relic effect happen) - you can keep them organized together.
    // Do keep in mind that "A patch class must be a public static class."
    
    private static final Logger logger = LogManager.getLogger(AmbryPatches.class.getName()); // This is our logger! It prints stuff out in the console.
    // It's like a very fancy System.out.println();

    @SpirePatch2(
            clz = DungeonMap.class,
            method = "calculateMapSize"
    )
    public static class MapSizePatch {
        @SpirePrefixPatch
        public static SpireReturn<Object> PatchMethod(DungeonMap __instance) {
            if (player.hasRelic(YellowKey.ID) && AbstractDungeon.id.equals(TheEnding.ID)) {

                return SpireReturn.Return(Settings.MAP_DST_Y * 5.0F - 1380.0F * Settings.scale);
            }


            return SpireReturn.Continue();
        }
    }
    @SpirePatch2(
            clz = DungeonMap.class,
            method = "update"
    )
    public static class MapButtonsPatch {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                int count = 0;
                @Override
                public void edit(FieldAccess m) throws CannotCompileException {
                    if (m.getClassName().equals(MapRoomNode.class.getName()) && m.getFieldName().equals("y")) {
                        count++;

                        if (count == 2) {

                            m.replace("{" +
                                    "if (com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.hasRelic(riskOfRelics.relics.YellowKey.ID)) {" +
                                    "$_ = $proceed()-1;" +
                                    "} else {" +
                                    "$_ = $proceed();" +
                                    "}" +
                                    "}");

                        }
                    }
                }
            };
        }


    }
    @SpirePatch2(
            clz = TheEnding.class,
            method = "initializeBoss"
    )
    public static class BossPatch{
        @SpirePrefixPatch
        //"A patch method must be a public static method."
        public static SpireReturn<Object> PatchMethod(TheEnding __instance) { // This is the name of the method that will be inserted.
            if (player.hasRelic(YellowKey.ID)) {
                AbstractDungeon.bossList.add(makeID("BulwarksAmbry"));
                AbstractDungeon.bossList.add(makeID("BulwarksAmbry"));
                AbstractDungeon.bossList.add(makeID("BulwarksAmbry"));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

    }

    @SpirePatch2(
            clz = AbstractDungeon.class,
            method = "setBoss"
    )
    public static class BossPatch2 {
        @SpirePostfixPatch
        //"A patch method must be a public static method."
        public static void PatchMethod(AbstractDungeon __instance, String key) { // This is the name of the method that will be inserted.
            if (player.hasRelic(YellowKey.ID) && AbstractDungeon.bossKey.equals(makeID("BulwarksAmbry"))) {
                DungeonMap.boss = ImageMaster.loadImage(makeBossPath("Ambry.png"));// 436
                DungeonMap.bossOutline = ImageMaster.loadImage(makeBossPath("AmbryOutline.png"));// 436

            }

        }
    }


     @SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
            clz = TheEnding.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
            method = "generateSpecialMap" // This is the name of the method we will be patching.

    )
    public static class MapGenPatch {
        public static boolean isEvil(){
            if (!Loader.isModLoaded("downfall") ){
                return false;
            }
            else {
                return EvilModeCharacterSelect.evilMode;
            }
        }
        @SpirePrefixPatch
        //"A patch method must be a public static method."
        public static SpireReturn<Object> PatchMethod(TheEnding __instance) { // This is the name of the method that will be inserted.
            if (player.hasRelic(YellowKey.ID) ) {
                ReflectionHacks.setPrivateStaticFinal(AbstractDungeon.class, "FINAL_ACT_MAP_HEIGHT", 4);
                long startTime = System.currentTimeMillis();// 74
                AbstractDungeon.map = new ArrayList();// 76
                ArrayList<MapRoomNode> row1 = new ArrayList();// 79
                MapRoomNode restNode = new MapRoomNode(3, 0);// 80
                restNode.room = new RestRoom();// 81
                MapRoomNode shopNode = new MapRoomNode(3, 1);// 82
                shopNode.room = new ShopRoom();// 83
                MapRoomNode ambryNode = new MapRoomNode(3, 2);// 84
                ambryNode.room = new AmbrySelectRoom();// 85
                MapRoomNode enemyNode = new MapRoomNode(3, 3);// 84
                enemyNode.room = new MonsterRoomElite();
                MapRoomNode bossNode = new MapRoomNode(3, 4);// 86
                bossNode.room = new MonsterRoomBoss();// 87
                MapRoomNode victoryNode = new MapRoomNode(3, 5);// 88
                victoryNode.room = new TrueVictoryRoom();// 89
                ReflectionHacks.privateMethod(TheEnding.class, "connectNode", MapRoomNode.class, MapRoomNode.class).invoke(__instance, restNode, shopNode);// 92
                ReflectionHacks.privateMethod(TheEnding.class, "connectNode", MapRoomNode.class, MapRoomNode.class).invoke(__instance, shopNode, ambryNode);
                ReflectionHacks.privateMethod(TheEnding.class, "connectNode", MapRoomNode.class, MapRoomNode.class).invoke(__instance, ambryNode, enemyNode);
                enemyNode.addEdge(new MapEdge(enemyNode.x, enemyNode.y, enemyNode.offsetX, enemyNode.offsetY, bossNode.x, bossNode.y, bossNode.offsetX, bossNode.offsetY, false));// 94
                row1.add(new MapRoomNode(0, 0));// 107
                row1.add(new MapRoomNode(1, 0));// 108
                row1.add(new MapRoomNode(2, 0));// 109
                row1.add(restNode);// 110
                row1.add(new MapRoomNode(4, 0));// 111
                row1.add(new MapRoomNode(5, 0));// 112
                row1.add(new MapRoomNode(6, 0));// 113
                ArrayList<MapRoomNode> row2 = new ArrayList();// 116
                row2.add(new MapRoomNode(0, 1));// 117
                row2.add(new MapRoomNode(1, 1));// 118
                row2.add(new MapRoomNode(2, 1));// 119
                row2.add(shopNode);// 120
                row2.add(new MapRoomNode(4, 1));// 121
                row2.add(new MapRoomNode(5, 1));// 122
                row2.add(new MapRoomNode(6, 1));// 123
                ArrayList<MapRoomNode> row3 = new ArrayList();// 126
                row3.add(new MapRoomNode(0, 2));// 127
                row3.add(new MapRoomNode(1, 2));// 128
                row3.add(new MapRoomNode(2, 2));// 129
                row3.add(ambryNode);// 130
                row3.add(new MapRoomNode(4, 2));// 131
                row3.add(new MapRoomNode(5, 2));// 132
                row3.add(new MapRoomNode(6, 2));// 133
                ArrayList<MapRoomNode> row4 = new ArrayList();// 126
                row4.add(new MapRoomNode(0, 3));// 127
                row4.add(new MapRoomNode(1, 3));// 128
                row4.add(new MapRoomNode(2, 3));// 129
                row4.add(enemyNode);// 130
                row4.add(new MapRoomNode(4, 3));// 131
                row4.add(new MapRoomNode(5, 3));// 132
                row4.add(new MapRoomNode(6, 3));// 133
                ArrayList<MapRoomNode> row5 = new ArrayList();// 136
                row5.add(new MapRoomNode(0, 4));// 147
                row5.add(new MapRoomNode(1, 4));// 148
                row5.add(new MapRoomNode(2, 4));// 149
                row5.add(bossNode);// 140
                row5.add(new MapRoomNode(4, 4));// 141
                row5.add(new MapRoomNode(5, 4));// 142
                row5.add(new MapRoomNode(6, 4));// 144
                ArrayList<MapRoomNode> row6 = new ArrayList();// 146
                row6.add(new MapRoomNode(0, 5));// 157
                row6.add(new MapRoomNode(1, 5));// 158
                row6.add(new MapRoomNode(2, 5));// 159
                row6.add(victoryNode);// 150
                row6.add(new MapRoomNode(4, 5));// 151
                row6.add(new MapRoomNode(5, 5));// 152
                row6.add(new MapRoomNode(6, 5));// 153
                AbstractDungeon.map.add(row1);// 156
                AbstractDungeon.map.add(row2);// 157
                AbstractDungeon.map.add(row3);// 158
                AbstractDungeon.map.add(row4);// 159
                AbstractDungeon.map.add(row5);// 160
                AbstractDungeon.map.add(row6);// 160
                logger.info("Generated the following dungeon map:");// 162
                logger.info(MapGenerator.toString(AbstractDungeon.map, true));// 163
                logger.info("Game Seed: " + Settings.seed);// 164
                logger.info("Map generation time: " + (System.currentTimeMillis() - startTime) + "ms");// 165
                AbstractDungeon.firstRoomChosen = false;// 166
                logger.info(ambryNode.room.getClass().getName());
                fadeIn();// 167
                if (isEvil()){
                    FlipMap.MapFlipper.flipflipflipflipflip();
                }


                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();

        }
    }

    
//    @SpirePatch2(
//            clz = FlipMap.MapFlipper.class,
//            method = "SWFAct4Override",
//            optional = true
//    )
//    public static class DownfallPatch1 {
//
//        public static SpireReturn<ArrayList<ArrayList<MapRoomNode>>> Prefix(){
//            if (player.hasRelic(YellowKey.ID)) {
//                ArrayList<ArrayList<MapRoomNode>> map = new ArrayList();// 112
//                MapRoomNode courierNode = new MapRoomNode(3, 0);// 114
//                courierNode.room = ModCrossoverHelperClass.returnCourierRoom();// 115
//                MapRoomNode restNode = new MapRoomNode(3, 1);// 116
//                restNode.room = new RestRoom();// 117
//                MapRoomNode shopNode = new MapRoomNode(3, 2);// 118
//                shopNode.room = new ShopRoom();// 119
//                MapRoomNode ambryNode = new MapRoomNode(3, 3);// 118
//                ambryNode.room = new AmbrySelectRoom();// 119
//                MapRoomNode enemyNode = new MapRoomNode(3, 4);// 120
//                enemyNode.room = new MonsterRoomElite();// 121
//                MapRoomNode bossNode = new MapRoomNode(3, 5);// 122
//                bossNode.room = new MonsterRoomBoss();// 123
//                MapRoomNode victoryNode = new MapRoomNode(3, 6);// 124
//                victoryNode.room = new TrueVictoryRoom();// 125
//                connectNode(courierNode, restNode);// 128
//                connectNode(restNode, shopNode);// 129
//                connectNode(shopNode, ambryNode);// 129
//                connectNode(ambryNode, enemyNode);// 130
//                enemyNode.addEdge(new MapEdge(enemyNode.x, enemyNode.y, enemyNode.offsetX, enemyNode.offsetY, bossNode.x, bossNode.y, bossNode.offsetX, bossNode.offsetY, false));// 132
//                ArrayList<MapRoomNode> row1 = new ArrayList();// 135
//                row1.add(new MapRoomNode(0, 0));// 136
//                row1.add(new MapRoomNode(1, 0));// 137
//                row1.add(new MapRoomNode(2, 0));// 138
//                row1.add(courierNode);// 139
//                row1.add(new MapRoomNode(4, 0));// 140
//                row1.add(new MapRoomNode(5, 0));// 141
//                row1.add(new MapRoomNode(6, 0));// 142
//                ArrayList<MapRoomNode> row2 = new ArrayList();// 144
//                row2.add(new MapRoomNode(0, 1));// 145
//                row2.add(new MapRoomNode(1, 1));// 146
//                row2.add(new MapRoomNode(2, 1));// 147
//                row2.add(restNode);// 148
//                row2.add(new MapRoomNode(4, 1));// 149
//                row2.add(new MapRoomNode(5, 1));// 150
//                row2.add(new MapRoomNode(6, 1));// 151
//                ArrayList<MapRoomNode> row3 = new ArrayList();// 153
//                row3.add(new MapRoomNode(0, 2));// 154
//                row3.add(new MapRoomNode(1, 2));// 155
//                row3.add(new MapRoomNode(2, 2));// 156
//                row3.add(shopNode);// 157
//                row3.add(new MapRoomNode(4, 2));// 158
//                row3.add(new MapRoomNode(5, 2));// 159
//                row3.add(new MapRoomNode(6, 2));// 160
//                ArrayList<MapRoomNode> rowINS = new ArrayList();// 153
//                rowINS.add(new MapRoomNode(0, 3));// 154
//                rowINS.add(new MapRoomNode(1, 3));// 155
//                rowINS.add(new MapRoomNode(2, 3));// 156
//                rowINS.add(ambryNode);// 157
//                rowINS.add(new MapRoomNode(4, 3));// 158
//                rowINS.add(new MapRoomNode(5, 3));// 159
//                rowINS.add(new MapRoomNode(6, 3));// 160
//                ArrayList<MapRoomNode> row4 = new ArrayList();// 162
//                row4.add(new MapRoomNode(0, 4));// 164
//                row4.add(new MapRoomNode(1, 4));// 164
//                row4.add(new MapRoomNode(2, 4));// 165
//                row4.add(enemyNode);// 166
//                row4.add(new MapRoomNode(4, 4));// 167
//                row4.add(new MapRoomNode(5, 4));// 168
//                row4.add(new MapRoomNode(6, 4));// 169
//                ArrayList<MapRoomNode> row5 = new ArrayList();// 171
//                row5.add(new MapRoomNode(0, 5));// 172
//                row5.add(new MapRoomNode(1, 5));// 173
//                row5.add(new MapRoomNode(2, 5));// 175
//                row5.add(bossNode);// 175
//                row5.add(new MapRoomNode(4, 5));// 176
//                row5.add(new MapRoomNode(5, 5));// 177
//                row5.add(new MapRoomNode(6, 5));// 178
//                ArrayList<MapRoomNode> row6 = new ArrayList();// 181
//                row6.add(new MapRoomNode(0, 6));// 182
//                row6.add(new MapRoomNode(1, 6));// 183
//                row6.add(new MapRoomNode(2, 6));// 184
//                row6.add(victoryNode);// 186
//                row6.add(new MapRoomNode(4, 6));// 186
//                row6.add(new MapRoomNode(5, 6));// 187
//                row6.add(new MapRoomNode(6, 6));// 188
//                map.add(row1);// 191
//                map.add(row2);// 192
//                map.add(row3);// 193
//                map.add(rowINS);
//                map.add(row4);// 194
//                map.add(row5);// 195
//                map.add(row6);// 196
//                AbstractDungeon.map.clear();// 198
//                AbstractDungeon.map = map;// 199
//                return SpireReturn.Return(map);
//
//            }
//            return SpireReturn.Continue();
//        }
//
//    }

}