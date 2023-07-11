package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.relics.ShapedGlass;
import riskOfRelics.vfx.ArtifactAboveCreatureAction;

import java.util.Objects;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectsQueue;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;



public class GlassPatches {// Don't worry about the "never used" warning - *You* usually don't use/call them anywhere. Mod The Spire does.

    @SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
            clz = AbstractPlayer.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
            method = "damage" // This is the name of the method we will be patching.
    )
    public static class ShapedGlassPlayerPatch {// Don't worry about the "never used" warning - *You* usually don't use/call them anywhere. Mod The Spire does.

        private static final Logger logger = LogManager.getLogger(DefaultInsertPatch.class.getName()); // This is our logger! It prints stuff out in the console.

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"damageAmount"}
        )
        //"A patch method must be a public static method."
        public static void thisIsOurActualPatchMethod(AbstractPlayer ___instance, DamageInfo info, @ByRef int[] damageAmount) {

            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.WEAKASSKNEES)
                    && AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
                damageAmount[0] *= 2;
                effectsQueue.add(new ArtifactAboveCreatureAction((float) Settings.WIDTH /2, (float) Settings.HEIGHT /2, RiskOfRelics.Artifacts.WEAKASSKNEES));
            }
            if (___instance.hasRelic(ShapedGlass.ID)) {
                int num = 0;



                for (AbstractRelic r: player.relics) {
                    if (Objects.equals(r.relicId, ShapedGlass.ID)){num++;}
                }

                damageAmount[0] *= Math.pow(ShapedGlass.AMOUNT,num);
            }

        }

        private static class Locator extends SpireInsertLocator { // Hey welcome to our SpireInsertLocator class!
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {// All the locator has and needs is an override of the Locate method


                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "decrementBlock");


                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);


            }
        }
    }
    @SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
            clz = AbstractMonster.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
            method = "damage" // This is the name of the method we will be patching.
    )
    public static class ShapedGlassMonsterPatch {// Don't worry about the "never used" warning - *You* usually don't use/call them anywhere. Mod The Spire does.

        private static final Logger logger = LogManager.getLogger(DefaultInsertPatch.class.getName()); // This is our logger! It prints stuff out in the console.

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"damageAmount"}
        )
        //"A patch method must be a public static method."
        public static void thisIsOurActualPatchMethod(AbstractMonster ___instance, DamageInfo info, @ByRef int[] damageAmount) {
            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.GLASS)) {
                damageAmount[0] *= 5;
                effectsQueue.add(new ArtifactAboveCreatureAction(___instance, RiskOfRelics.Artifacts.GLASS));
            }
            if (player.hasRelic(ShapedGlass.ID)) {
                int num = 0;

                for (AbstractRelic r: player.relics) {
                    if (Objects.equals(r.relicId, ShapedGlass.ID)){num++;}
                }
                damageAmount[0] *= Math.pow(ShapedGlass.AMOUNT,num);
            }

        }

        private static class Locator extends SpireInsertLocator { // Hey welcome to our SpireInsertLocator class!
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {// All the locator has and needs is an override of the Locate method


                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class,"isDying");


                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);


            }
        }
    }


}