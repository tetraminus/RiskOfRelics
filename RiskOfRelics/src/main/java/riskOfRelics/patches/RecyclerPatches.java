package riskOfRelics.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.chests.BossChest;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import javassist.CtBehavior;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.rewards.RerollReward;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class RecyclerPatches {
        public static Hitbox RecyclerHitbox = new Hitbox(Settings.WIDTH/2f-(100*Settings.scale)/2,Settings.HEIGHT/2f -150*Settings.scale , 100*Settings.scale, 100*Settings.scale);
        public static Texture RecyclerTexture = new Texture(RiskOfRelics.makeRelicPath("Recycler.png"));
        public static String[] TEXT = CardCrawlGame.languagePack.getRelicStrings(RiskOfRelics.makeID("Recycler")).DESCRIPTIONS;
        public static String NAME = CardCrawlGame.languagePack.getRelicStrings(RiskOfRelics.makeID("Recycler")).NAME;
        public static boolean usedThisRoom = false;

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

                if (player.hasRelic(RiskOfRelics.makeID("Recycler")) && shouldtrigger) {
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
                    if (player.hasRelic(RiskOfRelics.makeID("Recycler")) && shouldtrigger) {
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


        @SpirePatch2(
                clz = BossChest.class,
                method = SpirePatch.CONSTRUCTOR
        )
        public static class BossChestPatch{
            public static void Postfix(BossChest __instance) {
                usedThisRoom = false;


            }
        }


        @SpirePatch2(
                clz = BossRelicSelectScreen.class,
                method = "update"


        )
        public static class BossScreenUpdatePatch{
            public static void Postfix(BossRelicSelectScreen __instance) {
                RecyclerHitbox.update();
                if (player.hasRelic(RiskOfRelics.makeID("Recycler")) && RecyclerHitbox.hovered&& !usedThisRoom) {
                    TipHelper.renderGenericTip(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY, NAME, TEXT[1]);
                }

                if (player.hasRelic(RiskOfRelics.makeID("Recycler"))&&RecyclerHitbox.hovered && InputHelper.justClickedLeft && !usedThisRoom) {
                    RerollBossRelics(__instance);

                }

            }

        }

    private static void RerollBossRelics(BossRelicSelectScreen __instance) {
        RecyclerHitbox.clicked = false;
        usedThisRoom = true;
        ArrayList<AbstractRelic> relics = new ArrayList<>();
        for (int i = 0; i < __instance.relics.size(); i++) {
            if (__instance.relics.get(i) != null) {
                relics.add(i, AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS));
            }
        }
        if (relics.size() < 3) {
            for (int i = relics.size(); i < 3; i++) {
                relics.add(i, new Circlet());
            }
        }
        if (AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss) {
            ((BossChest) ((TreasureRoomBoss) (AbstractDungeon.getCurrRoom())).chest).relics = relics;
        }


        ((BossChest) ((TreasureRoomBoss) (AbstractDungeon.getCurrRoom())).chest).open(true);
    }

        @SpirePatch2(
                clz = BossRelicSelectScreen.class,
                method = "render"
        )
        public static class BossScreenRenderPatch{
            public static void Postfix(SpriteBatch sb) {
                if (player.hasRelic(RiskOfRelics.makeID("Recycler")) && !usedThisRoom) {
                    RecyclerHitbox.render(sb);
                    sb.setColor(Color.WHITE.cpy());
                    sb.draw(RecyclerTexture,
                            RecyclerHitbox.x - RecyclerHitbox.height / 2f, RecyclerHitbox.y - RecyclerHitbox.height / 2f,
                            RecyclerHitbox.width*2, RecyclerHitbox.height*2);

                }
            }

        }

}



