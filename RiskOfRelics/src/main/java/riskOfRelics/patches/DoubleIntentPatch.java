package riskOfRelics.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import riskOfRelics.relics.ShapedGlass;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


@SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
        clz = AbstractMonster.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
        method = "calculateDamage" // This is the name of the method we will be patching.

)
public class DoubleIntentPatch {
    
    private static final Logger logger = LogManager.getLogger(DoubleIntentPatch.class.getName()); // This is our logger! It prints stuff out in the console.
    // It's like a very fancy System.out.println();
    
    @SpireInsertPatch( // This annotation of our patch method specifies the type of patch we will be using. In our case - a Spire Insert Patch
            
            locator = Locator.class,
            
            localvars = {"tmp"}
    )

    //"A patch method must be a public static method."
    public static void DoubleIntentPatchMethod(AbstractMonster ___instance, int dmg, @ByRef float[] tmp) {
            if (player.hasRelic(ShapedGlass.ID)){
                tmp[0] = tmp[0] * 2.0f;
            }
    }
    
    private static class Locator extends SpireInsertLocator { // Hey welcome to our SpireInsertLocator class!
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");

            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

        }
    }
}