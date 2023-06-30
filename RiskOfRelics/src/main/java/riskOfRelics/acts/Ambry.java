package riskOfRelics.acts;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.TheEndingScene;

import java.util.ArrayList;

public class Ambry extends AbstractDungeon {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public static final String NAME;
    public static final String ID = "Ambry";

    public Ambry(AbstractPlayer p, ArrayList<String> theList) {
        super(NAME, "Ambry", p, theList);// 36
        if (scene != null) {// 38
            scene.dispose();// 39
        }

        scene = new TheEndingScene();// 42
        fadeColor = Color.valueOf("140a1eff");// 43
        sourceFadeColor = Color.valueOf("140a1eff");// 44
        this.initializeLevelSpecificChances();// 46
        mapRng = new Random(Settings.seed + (long)(AbstractDungeon.actNum * 300));// 47
        this.generateSpecialMap();// 48
        CardCrawlGame.music.changeBGM(id);// 49
    }// 50

    public Ambry(AbstractPlayer p, SaveFile saveFile) {
        super(NAME, p, saveFile);// 53
        CardCrawlGame.dungeon = this;// 54
        if (scene != null) {// 56
            scene.dispose();// 57
        }

        scene = new TheEndingScene();// 60
        fadeColor = Color.valueOf("140a1eff");// 61
        sourceFadeColor = Color.valueOf("140a1eff");// 62
        this.initializeLevelSpecificChances();// 64
        miscRng = new Random(Settings.seed + (long)saveFile.floor_num);// 65
        CardCrawlGame.music.changeBGM(id);// 66
        mapRng = new Random(Settings.seed + (long)(saveFile.act_num * 300));// 67
        this.generateSpecialMap();// 68
        firstRoomChosen = true;// 69
        this.populatePathTaken(saveFile);// 70
    }// 71

    private void generateSpecialMap() {
        long startTime = System.currentTimeMillis();// 74
        map = new ArrayList();// 76
        ArrayList<MapRoomNode> row1 = new ArrayList();// 79
        MapRoomNode restNode = new MapRoomNode(3, 0);// 80
        restNode.room = new RestRoom();// 81
        MapRoomNode shopNode = new MapRoomNode(3, 1);// 82
        shopNode.room = new ShopRoom();// 83
        MapRoomNode enemyNode = new MapRoomNode(3, 2);// 84
        enemyNode.room = new MonsterRoomElite();// 85
        MapRoomNode bossNode = new MapRoomNode(3, 3);// 86
        bossNode.room = new MonsterRoomBoss();// 87
        MapRoomNode victoryNode = new MapRoomNode(3, 4);// 88
        victoryNode.room = new TrueVictoryRoom();// 89
        this.connectNode(restNode, shopNode);// 92
        this.connectNode(shopNode, enemyNode);// 93
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
        row3.add(enemyNode);// 130
        row3.add(new MapRoomNode(4, 2));// 131
        row3.add(new MapRoomNode(5, 2));// 132
        row3.add(new MapRoomNode(6, 2));// 133
        ArrayList<MapRoomNode> row4 = new ArrayList();// 136
        row4.add(new MapRoomNode(0, 3));// 137
        row4.add(new MapRoomNode(1, 3));// 138
        row4.add(new MapRoomNode(2, 3));// 139
        row4.add(bossNode);// 140
        row4.add(new MapRoomNode(4, 3));// 141
        row4.add(new MapRoomNode(5, 3));// 142
        row4.add(new MapRoomNode(6, 3));// 143
        ArrayList<MapRoomNode> row5 = new ArrayList();// 146
        row5.add(new MapRoomNode(0, 4));// 147
        row5.add(new MapRoomNode(1, 4));// 148
        row5.add(new MapRoomNode(2, 4));// 149
        row5.add(victoryNode);// 150
        row5.add(new MapRoomNode(4, 4));// 151
        row5.add(new MapRoomNode(5, 4));// 152
        row5.add(new MapRoomNode(6, 4));// 153
        map.add(row1);// 156
        map.add(row2);// 157
        map.add(row3);// 158
        map.add(row4);// 159
        map.add(row5);// 160
        logger.info("Generated the following dungeon map:");// 162
        logger.info(MapGenerator.toString(map, true));// 163
        logger.info("Game Seed: " + Settings.seed);// 164
        logger.info("Map generation time: " + (System.currentTimeMillis() - startTime) + "ms");// 165
        firstRoomChosen = false;// 166
        fadeIn();// 167
    }// 168

    private void connectNode(MapRoomNode src, MapRoomNode dst) {
        src.addEdge(new MapEdge(src.x, src.y, src.offsetX, src.offsetY, dst.x, dst.y, dst.offsetX, dst.offsetY, false));// 171
    }// 172

    protected void initializeLevelSpecificChances() {
        shopRoomChance = 0.05F;// 177
        restRoomChance = 0.12F;// 178
        treasureRoomChance = 0.0F;// 179
        eventRoomChance = 0.22F;// 180
        eliteRoomChance = 0.08F;// 181
        smallChestChance = 0;// 184
        mediumChestChance = 100;// 185
        largeChestChance = 0;// 186
        commonRelicChance = 0;// 189
        uncommonRelicChance = 100;// 190
        rareRelicChance = 0;// 191
        colorlessRareChance = 0.3F;// 194
        if (AbstractDungeon.ascensionLevel >= 12) {// 195
            cardUpgradedChance = 0.25F;// 196
        } else {
            cardUpgradedChance = 0.5F;// 198
        }

    }// 200

    protected void generateMonsters() {
        monsterList = new ArrayList();// 205
        monsterList.add("Shield and Spear");// 206
        monsterList.add("Shield and Spear");// 207
        monsterList.add("Shield and Spear");// 208
        eliteMonsterList = new ArrayList();// 210
        eliteMonsterList.add("Shield and Spear");// 211
        eliteMonsterList.add("Shield and Spear");// 212
        eliteMonsterList.add("Shield and Spear");// 213
    }// 214

    protected void generateWeakEnemies(int count) {
    }// 219

    protected void generateStrongEnemies(int count) {
    }// 223

    protected void generateElites(int count) {
    }// 227

    protected ArrayList<String> generateExclusions() {
        return new ArrayList();// 231
    }

    protected void initializeBoss() {
        bossList.add("The Heart");// 236
        bossList.add("The Heart");// 237
        bossList.add("The Heart");// 238
    }// 239

    protected void initializeEventList() {
    }// 244

    protected void initializeEventImg() {
        if (eventBackgroundImg != null) {// 248
            eventBackgroundImg.dispose();// 249
            eventBackgroundImg = null;// 250
        }

        eventBackgroundImg = ImageMaster.loadImage("images/ui/event/panel.png");// 252
    }// 253

    protected void initializeShrineList() {
    }// 258

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("TheEnding");// 29
        TEXT = uiStrings.TEXT;// 30
        NAME = TEXT[0];// 32
    }
}
