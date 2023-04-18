package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import riskOfRelics.DefaultMod;
import riskOfRelics.rewards.RerollReward;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class RecyclerPatches {
        @SpirePatch(
                clz = AbstractRoom.class,
                method = "addRelicToRewards",
                paramtypez={
                        AbstractRelic.RelicTier.class
                }
        )
        public static class TieredPatch {


            @SpireInsertPatch(
                    locator = Locator.class

            )
            public static void RecyclerPatchMethod(AbstractRoom ___instance, AbstractRelic.RelicTier tier) {
                boolean shouldtrigger = true;
                for (RewardItem item: ___instance.rewards) {
                    if (item.type == RerollRewardPatch.RISKOFRELICS_REROLL) {
                        shouldtrigger = false;
                    }
                }

                if (player.hasRelic(DefaultMod.makeID("Recycler")) && shouldtrigger) {
                    ___instance.rewards.add(new RerollReward());
                }

            }

            private static class Locator extends SpireInsertLocator { // Hey welcome to our SpireInsertLocator class!
                @Override
                public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

                    Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "returnRandomRelic");

                    return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

                }
            }
        }
            @SpirePatch(
                    clz = AbstractRoom.class,
                    method = "addRelicToRewards",
                    paramtypez={
                            AbstractRelic.class
                    }
            )
            public static class UntieredPatch{


                @SpireInsertPatch(
                        locator = Locator.class

                )
                public static void RecyclerPatchMethod(AbstractRoom ___instance, AbstractRelic relic) {
                    boolean shouldtrigger = true;
                    for (RewardItem item: ___instance.rewards) {
                        if (item.type == RerollRewardPatch.RISKOFRELICS_REROLL) {
                            shouldtrigger = false;

                        }
                    }
                    if (player.hasRelic(DefaultMod.makeID("Recycler")) && shouldtrigger) {
                        ___instance.rewards.add(new RerollReward());
                    }


                }
                private static class Locator extends SpireInsertLocator { // Hey welcome to our SpireInsertLocator class!
                    @Override
                    public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

                        Matcher finalMatcher = new Matcher.NewExprMatcher(RewardItem.class);

                        return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

                    }
                }
            }
}



