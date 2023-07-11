package riskOfRelics.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import riskOfRelics.relics.YellowKey;
import riskOfRelics.rooms.AmbrySelectRoom;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.fadeIn;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

/*
 * Using SpirePatch, also known as patching, allows you to insert your own code into the basegame code.
 * It is an extremely powerful and useful tool that can appear complicated at first. If you have no experience with modding StS, and especially
 * with Java, I recommend you skip this for a while until you have a semi-decent grip on things/until you feel like you need to use it.
 * That being said, at the end of the day, it is not very complex once you understand how it works.
 *
 * Keep in mind that every patch is very unique so making a "tutorial class" that goes beyond the basics is a bit difficult,
 * since there are too many unique cases. I'll leave it up to you to experiment and learn what you need for *your own* patch.
 *
 * You will ***NEED*** to follow the official SpirePatch documentation here as you read through this patch.
 * https://github.com/kiooeht/ModTheSpire/wiki/SpirePatch
 * https://github.com/kiooeht/ModTheSpire/wiki/Matcher
 * Comments with quotations are taken from the documentation.
 *
 * This is a good time to Ctrl+Click on AbstractDungeon down there and Ctrl+f for returnRandomRelicKey() - that is the method that we will be patching.
 * Have a read through it's code. returnRandomRelicKey() is a method that is passed a rarity (relic tier) and returns the first relic
 * from the appropriate pool of that rarity (which are pre-shuffled), as well as removing it from the relic pool so that you never get it again.
 *
 * This is used whenever any combat gives you a relic reward - it is how the game grabs a random relic.
 * (On a sidenote returnEndRandomRelicKey() on the other hand returns the *last* relic form the same list - this is used for shops.)
 * That way visiting a shop doesn't change what relics you would get/see otherwise.
 *
 * Now that we understand how that method works - This patch will do the following:
 * We will insert our piece of code above the line "return !RelicLibrary.getRelic(retVal).canSpawn() ? returnEndRandomRelicKey(tier) : retVal;"
 * which is at the very end of the method. (If you read through the official documentation, you will also know that you can simply use a postfix patch to do that.)
 * Have a read through the documentation as to their differences - they all have their pros and cons.
 * For example postfix patches can't use @ByRef and doesn't have localvars. On the other hand, instead of needing to use SpireReturn they can just
 * put a return value in their patched method, and they can also be passed the original return value of the patched method.
 *
 * *NEVER USE REPLACE PATCHES. DON'T REPLACE GAME FILES EITHER (by putting a file with the same name in the same location as a basegame one).*
 * *NEVER USE REPLACE PATCHES. DON'T REPLACE GAME FILES EITHER (by putting a file with the same name in the same location as a basegame one).*
 * *NEVER USE REPLACE PATCHES. DON'T REPLACE GAME FILES EITHER (by putting a file with the same name in the same location as a basegame one).*
 * *NEVER USE REPLACE PATCHES. DON'T REPLACE GAME FILES EITHER (by putting a file with the same name in the same location as a basegame one).*
 * *NEVER USE REPLACE PATCHES. DON'T REPLACE GAME FILES EITHER (by putting a file with the same name in the same location as a basegame one).*
 * *NEVER USE REPLACE PATCHES. DON'T REPLACE GAME FILES EITHER (by putting a file with the same name in the same location as a basegame one).*
 *
 * So:
 * We will insert our piece of code above the line "return !RelicLibrary.getRelic(retVal).canSpawn() ? returnEndRandomRelicKey(tier) : retVal;"
 * We will the put in a logger inside that prints out the the value of the local variable "retVal" of that method.
 * That's about it.
 *
 * Let's get to it!
 */

@SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
        clz = TheEnding.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
        method = "generateSpecialMap" // This is the name of the method we will be patching.

)
public class AmbryPatches {// Don't worry about the "never used" warning - *You* usually don't use/call them anywhere. Mod The Spire does.
    
    // You can have as many inner classes with patches as you want inside this one - you don't have to separate each patch into it's own file.
    // So if you need to put 4 patches all for 1 purpose (for example they all make a specific relic effect happen) - you can keep them organized together.
    // Do keep in mind that "A patch class must be a public static class."
    
    private static final Logger logger = LogManager.getLogger(AmbryPatches.class.getName()); // This is our logger! It prints stuff out in the console.
    // It's like a very fancy System.out.println();
    
    @SpirePrefixPatch
    //"A patch method must be a public static method."
    public static SpireReturn<Object> PatchMethod(TheEnding __instance) { // This is the name of the method that will be inserted.
        if (player.hasRelic(YellowKey.ID)){
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
            ReflectionHacks.privateMethod(TheEnding.class,"connectNode", MapRoomNode.class, MapRoomNode.class).invoke(__instance , restNode, shopNode);// 92
            ReflectionHacks.privateMethod(TheEnding.class,"connectNode", MapRoomNode.class, MapRoomNode.class).invoke(__instance , shopNode, ambryNode);
            ReflectionHacks.privateMethod(TheEnding.class,"connectNode", MapRoomNode.class, MapRoomNode.class).invoke(__instance , ambryNode, enemyNode);
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
            row4.add(new MapRoomNode(0, 2));// 127
            row4.add(new MapRoomNode(1, 2));// 128
            row4.add(new MapRoomNode(2, 2));// 129
            row4.add(enemyNode);// 130
            row4.add(new MapRoomNode(4, 2));// 131
            row4.add(new MapRoomNode(5, 2));// 132
            row4.add(new MapRoomNode(6, 2));// 133
            ArrayList<MapRoomNode> row5 = new ArrayList();// 136
            row5.add(new MapRoomNode(0, 3));// 137
            row5.add(new MapRoomNode(1, 3));// 138
            row5.add(new MapRoomNode(2, 3));// 139
            row5.add(bossNode);// 140
            row5.add(new MapRoomNode(4, 3));// 141
            row5.add(new MapRoomNode(5, 3));// 142
            row5.add(new MapRoomNode(6, 3));// 143
            ArrayList<MapRoomNode> row6 = new ArrayList();// 146
            row6.add(new MapRoomNode(0, 4));// 147
            row6.add(new MapRoomNode(1, 4));// 148
            row6.add(new MapRoomNode(2, 4));// 149
            row6.add(victoryNode);// 150
            row6.add(new MapRoomNode(4, 4));// 151
            row6.add(new MapRoomNode(5, 4));// 152
            row6.add(new MapRoomNode(6, 4));// 153
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
            fadeIn();// 167
            return SpireReturn.Return(null);
        }

        return SpireReturn.Continue();

    }
    

}