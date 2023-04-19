package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import riskOfRelics.relics.ShapedGlass;

import java.util.Objects;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;



public class ShapedGlassPatch {// Don't worry about the "never used" warning - *You* usually don't use/call them anywhere. Mod The Spire does.

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
            if (___instance.hasRelic(ShapedGlass.ID)) {
                int num = 0;

                for (AbstractRelic r: player.relics) {
                    if (Objects.equals(r.relicId, ShapedGlass.ID)){num++;}
                }

                damageAmount[0] *= (ShapedGlass.AMOUNT*num);
            }

        }

        private static class Locator extends SpireInsertLocator { // Hey welcome to our SpireInsertLocator class!
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {// All the locator has and needs is an override of the Locate method


                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasPower");


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
            if (player.hasRelic(ShapedGlass.ID)) {
                int num = 0;

                for (AbstractRelic r: player.relics) {
                    if (Objects.equals(r.relicId, ShapedGlass.ID)){num++;}
                }
                damageAmount[0] *= ShapedGlass.AMOUNT*num;
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