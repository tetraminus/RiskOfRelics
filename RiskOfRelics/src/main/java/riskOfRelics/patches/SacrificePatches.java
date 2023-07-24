package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.map.RoomTypeAssigner;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import javassist.CtBehavior;
import riskOfRelics.RiskOfRelics;


public class SacrificePatches {

    @SpirePatch2(clz = EventHelper.class, method = "roll", paramtypez = {Random.class})
    public static class SacrificeEventPatch {
        @SpireInsertPatch(locator = Locator.class,localvars = {"choice"})
        public static void insert(Random eventRng, @ByRef EventHelper.RoomResult[] choice) {
            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.SACRIFICE) && choice[0] == EventHelper.RoomResult.TREASURE ) {
                choice[0] = EventHelper.RoomResult.ELITE;
            }
        }

        private static class Locator extends SpireInsertLocator {


            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(Settings.class, "isEndless");

                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }

    }
    @SpirePatch2(
            clz = MonsterRoomElite.class,
            method = "dropReward"
    )
    public static class SacrificeElitePatch {
        @SpireInsertPatch(locator = Locator.class,localvars = {"choice"})
        public static void insert(MonsterRoomElite __instance) {
            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.SACRIFICE) && AbstractDungeon.treasureRng.randomBoolean()) {
                __instance.addRelicToRewards(AbstractDungeon.returnRandomRelicTier());


            }
            if (Settings.isFinalActAvailable && !Settings.hasSapphireKey && !__instance.rewards.isEmpty()) {
                __instance.rewards.add(new RewardItem((RewardItem)__instance.rewards.get(__instance.rewards.size() - 1),RewardItem.RewardType.SAPPHIRE_KEY));
            }
        }

        private static class Locator extends SpireInsertLocator {


            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MonsterRoomElite.class, "addRelicToRewards");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }


    @SpirePatch2(clz = RoomTypeAssigner.class, method = "assignRowAsRoomType")
    public static class SacrificeRoomPatch {
        @SpirePrefixPatch
        public static void Prefix(@ByRef Class<? extends AbstractRoom>[] c) {
            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.SACRIFICE) && c[0] == TreasureRoom.class) {
                c[0] = MonsterRoomElite.class;
            }
        }
    }
}
