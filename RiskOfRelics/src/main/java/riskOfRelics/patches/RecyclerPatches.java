package riskOfRelics.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
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
import de.robojumper.ststwitch.TwitchVoteOption;
import de.robojumper.ststwitch.TwitchVoter;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.rewards.RerollReward;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

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
                justHit = false;

            }
        }
        public static TwitchVoter voter;
        @SpirePatch2(
            clz = BossRelicSelectScreen.class,
            method = SpirePatch.CONSTRUCTOR
        )
        public static class setupvoter{
            public static void Postfix(BossRelicSelectScreen __instance) {
                Optional<TwitchVoter> twitchVoterOptional = ReflectionHacks.privateMethod(BossRelicSelectScreen.class, "getVoter").invoke(__instance);

                twitchVoterOptional.ifPresent(twitchVoter -> voter = twitchVoter);

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
                if (justHit) {
                    justHit = false;

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

//        if (voter != null) {
//            ReflectionHacks.setPrivate(__instance, BossRelicSelectScreen.class, "mayVote", false);
//            ReflectionHacks.setPrivate(__instance, BossRelicSelectScreen.class, "isVoting", false);
//            ReflectionHacks.setPrivate(voter, TwitchVoter.class, "triggered", false);
//            ReflectionHacks.setPrivate(voter, TwitchVoter.class, "isVoting", false);
//
//            voter.update();
//
//
//        }

        ((BossChest) ((TreasureRoomBoss) (AbstractDungeon.getCurrRoom())).chest).open(true);


    }
    @SpirePatch2(
            clz = BossRelicSelectScreen.class,
            method = "renderTwitchVotes"


    )
    public static class VoteRender{
        public static void Postfix(BossRelicSelectScreen __instance, SpriteBatch sb) {
            if ((boolean)(ReflectionHacks.getPrivate(__instance, BossRelicSelectScreen.class, "isVoting")) && !usedThisRoom) {
                TwitchVoteOption[] options = voter.getOptions();// 349
                int sum = (Integer) Arrays.stream(options).map((c) -> {// 350
                    return c.voteCount;
                }).reduce(0, Integer::sum);
                String s = "#4: " + options[4].voteCount;
                if (sum > 0) {// 391
                    s = s + " (" + options[4].voteCount * 100 / sum + "%)";// 392
                }

                FontHelper.renderFont(sb, FontHelper.panelNameFont, s, ((float) Settings.WIDTH /2)-32* Settings.scale, 400.0F * Settings.scale, Color.WHITE);// 395
            }
        }

    }

        public static boolean StartVote(BossRelicSelectScreen instance){
            if (voter != null) {
                if (usedThisRoom && player.hasRelic(RiskOfRelics.makeID("Recycler"))) {
                    return voter.initiateSimpleNumberVote((String[]) Stream.concat(Stream.of("skip"), instance.relics.stream().map(AbstractRelic::toString))
                            .toArray(String[]::new), instance::completeVoting);
                } else {
                    return voter.initiateSimpleNumberVote((String[]) Stream.concat(Stream.concat(Stream.of("skip"), instance.relics.stream().map(AbstractRelic::toString)), Stream.of("reroll"))
                            .toArray(String[]::new), instance::completeVoting);
                }

            }
            else {
                return false;
            }
        }
        public static boolean justHit;
        @SpirePatch2(
                clz = BossRelicSelectScreen.class,
                method = "completeVoting"
        )
        public static class BossScreenCompletePatch{
            public static void Postfix(BossRelicSelectScreen __instance, int option) {
                if (voter != null) {
                    if (option == __instance.relics.size()+1) {
                        justHit = true;

                    }

                }
            }

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
    @SpirePatch2(
            clz = BossRelicSelectScreen.class,
            method = "updateVote"
    )
    public static class voteCatch{
            @SpireInstrumentPatch
            public static ExprEditor Instrument() {
                return new ExprEditor() {
                    @Override
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getClassName().equals(TwitchVoter.class.getName()) && m.getMethodName().equals("initiateSimpleNumberVote")) {
                            m.replace("$_ = riskOfRelics.patches.RecyclerPatches.StartVote(this);");
                        }
                    }
                };
            }


    }
//    @SpirePatch2(
//            clz = TwitchVoter.class,
//            method = "endVoting"
//    )
//    public static class CompleteVoteFixerPt1{
//        @SpireInstrumentPatch
//        public static ExprEditor Instrument() {
//            return new ExprEditor() {
//                @Override
//                public void edit(MethodCall m) throws CannotCompileException {
//                    if (m.getClassName().equals(TwitchVoter.class.getName()) && m.getMethodName().equals("isCurrentlyVoting")) {
//                        m.replace("$_ = $proceed() && !riskOfRelics.patches.RecyclerPatches.justHit;");
//
//
//                    }
//
//
//                }
//            };
//        }
//    }
//    @SpirePatch2(
//            clz = TwitchVoter.class,
//            method = "endVoting"
//    )
//    public static class CompleteVoteFixerPt2{
//        public static void Postfix(TwitchVoter __instance) {
//            if (voter != null&& justHit) {
//                ReflectionHacks.setPrivate(voter, TwitchVoter.class, "triggered", false);
//            }
//            justHit = false;
//
//
//        }
//    }
}



