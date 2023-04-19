package riskOfRelics.patches;

import basemod.helpers.dynamicvariables.DamageVariable;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import riskOfRelics.relics.ShapedGlass;

import java.util.Objects;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class DoubleNumberPatches {
    @SpirePatch2(    // "Use the @SpirePatch annotation on the patch class."
            clz = AbstractMonster.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
            method = "calculateDamage" // This is the name of the method we will be patching.

    )
    public static class DoubleIntentPatch {
        @SpireInsertPatch( // This annotation of our patch method specifies the type of patch we will be using. In our case - a Spire Insert Patch

                locator = Locator.class

        )
        public static void DoubleIntentPatchMethod(AbstractMonster __instance, @ByRef int[] dmg) {
            if (player.hasRelic(ShapedGlass.ID) &&
                    !(player.hasPower(IntangiblePower.POWER_ID )||
                            (player.hasPower(IntangiblePlayerPower.POWER_ID )))) {
                int num = 0;

                for (AbstractRelic r: player.relics) {
                    if (Objects.equals(r.relicId, ShapedGlass.ID)){num++;}
                }
                dmg[0] = dmg[0] * (int) Math.pow(ShapedGlass.AMOUNT,num);
            }
        }

        private static class Locator extends SpireInsertLocator { // Hey welcome to our SpireInsertLocator class!
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "intentDmg");

                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

            }
        }
    }

//    @SpirePatch2(    // "Use the @SpirePatch annotation on the patch class."
//            clz = AbstractCard.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
//            method = "renderDynamicVariable" // This is the name of the method we will be patching.
//
//    )
//    public static class DoubleCardPatchModif {
//
//
//        @SpireInsertPatch( // This annotation of our patch method specifies the type of patch we will be using. In our case - a Spire Insert Patch
//
//                locator = Locator.class,
//
//                localvars = {"num"}
//
//        )
//
//        //"A patch method must be a public static method."
//        public static void DoubleCardPatchMethod( @ByRef int[] num) {
//            if (true) {
//                num[0] *= 2;
//            }
//        }
//
//        private static class Locator extends SpireInsertLocator { // Hey welcome to our SpireInsertLocator class!
//            @Override
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "baseDamage");
//
//                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
//
//            }
//        }
//    }
//    @SpirePatch2(
//            clz = AbstractCard.class,
//            method = "renderDynamicVariable"
//    )
//    public static class DoubleCardPatchUnmodif {
//        static Logger logger = LogManager.getLogger(DoubleCardPatchUnmodif.class.getName());
//        @SpireInsertPatch(locator = Locator.class, localvars = {"num"})
//        public static void DoubleCardPatchMethod(@ByRef int[] num) {
//            num[0] *= 2;
//
//        }
//        private static class Locator extends SpireInsertLocator {
//            @Override
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "baseDamage");
//
//                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1] + 1};
//
//            }
//        }
//    }
    @SpirePatch2(    // "Use the @SpirePatch annotation on the patch class."
            clz = DamageVariable.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
            method = "value" // This is the name of the method we will be patching.

    )
    public static class DoubleCardPatch {
        @SpirePrefixPatch
        //"A patch method must be a public static method."
        public static SpireReturn<Integer> DoubleCardPatchMethod(AbstractCard card) {
            if (player.hasRelic(ShapedGlass.ID)) {
                int num = 0;

                for (AbstractRelic r: player.relics) {
                    if (Objects.equals(r.relicId, ShapedGlass.ID)){num++;}
                }
                    return SpireReturn.Return(card.damage * (int) Math.pow(ShapedGlass.AMOUNT,num));
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch2(    // "Use the @SpirePatch annotation on the patch class."
            clz = DamageVariable.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
            method = "baseValue" // This is the name of the method we will be patching.
    )
    public static class DoubleCardPatchModif {
        @SpirePrefixPatch
        //"A patch method must be a public static method."
        public static SpireReturn<Integer> DoubleCardPatchMethod(AbstractCard card) {
            if (AbstractDungeon.isPlayerInDungeon() && player.hasRelic(ShapedGlass.ID)) {
                int num = 0;

                for (AbstractRelic r: player.relics) {
                    if (Objects.equals(r.relicId, ShapedGlass.ID)){num++;}
                }
                return SpireReturn.Return(card.baseDamage * (int) Math.pow(ShapedGlass.AMOUNT,num));
            }
            return SpireReturn.Continue();
        }
    }
}