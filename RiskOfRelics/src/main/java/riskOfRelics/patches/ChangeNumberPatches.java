package riskOfRelics.patches;

import basemod.helpers.dynamicvariables.DamageVariable;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DynamicTextBlocks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.artifacts.GlassArt;
import riskOfRelics.relics.ShapedGlass;

import java.util.Objects;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class ChangeNumberPatches {
    @SpirePatch2(    // "Use the @SpirePatch annotation on the patch class."
            clz = AbstractMonster.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
            method = "calculateDamage" // This is the name of the method we will be patching.

    )
    public static class DoubleIntentPatch {
        @SpireInsertPatch( // This annotation of our patch method specifies the type of patch we will be using. In our case - a Spire Insert Patch

                locator = Locator.class

        )
        public static void DoubleIntentPatchMethod(AbstractMonster __instance, @ByRef int[] dmg) {
            if (player.hasRelic(ShapedGlass.ID)) {
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


    @SpirePatch2(    // "Use the @SpirePatch annotation on the patch class."
            clz = DamageVariable.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
            method = "value" // This is the name of the method we will be patching.

    )
    public static class DoubleCardPatch {
        @SpirePrefixPatch

        public static SpireReturn<Integer> DoubleCardPatchMethod(AbstractCard card) {
            int AlteredVal = card.damage;
            if (AbstractDungeon.isPlayerInDungeon() && player != null && !DynamicTextBlocks.DisplayingUpgradesField.displayingUpgrades.get(card)) {

                if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.GLASS)) {
                    AlteredVal *= GlassArt.DamageMultiplier;
                }

                if (player.hasRelic(ShapedGlass.ID)) {
                    int num = 0;

                    for (AbstractRelic r : player.relics) {
                        if (Objects.equals(r.relicId, ShapedGlass.ID)) {
                            num++;
                        }
                    }
                    AlteredVal *= (int) Math.pow(ShapedGlass.AMOUNT, num);
                }
            }


            return SpireReturn.Return(AlteredVal);
        }
    }
//    @SpirePatch2(
//            clz = SmithPreview.class,
//            method = "Postfix"
//    )
//    public static class SmithFix {
//        static Logger logger = Logger.getLogger(ChangeNumberPatches.class.getName());
//        @SpireInstrumentPatch
//        public static ExprEditor patch() {
//            return new ExprEditor() {
//                @Override
//                public void edit(MethodCall m) throws CannotCompileException {
//                    logger.info(m.getClassName());
//                    logger.info(m.getMethodName());
//                    if (m.getClassName().equals(DynamicVariable.class.getName()) && m.getMethodName().equals("modifiedBaseValue")) {
//                        logger.info("Smithing a card");
//                        m.replace(
//                                "{"+
//                                            "if (riskOfRelics.RiskOfRelics.ActiveArtifacts.contains(riskOfRelics.RiskOfRelics.Artifacts.GLASS) && dv.key() == \"D\") {" +
//                                            "logger.info(\"Glass artifact detected\");" +
//                                            "    $_ = CardModifierManager.modifiedBaseValue($$, $$.baseDamage, dv.key());" +
//                                            "} else {" +
//                                                "$_ = $proceed($$);" +
//                                            "}"+
//                                        "}");
//
//                    }
//                }
//            };
//        }
//    }
    @SpirePatch2(    // "Use the @SpirePatch annotation on the patch class."
            clz = DamageVariable.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
            method = "baseValue" // This is the name of the method we will be patching.
    )
    public static class DoubleCardPatchModif {
        @SpirePrefixPatch
        //"A patch method must be a public static method."
        public static SpireReturn<Integer> DoubleCardPatchMethod(AbstractCard card) {
            int AlteredVal = card.baseDamage;
            if (AbstractDungeon.isPlayerInDungeon() && player != null ) {

                if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.GLASS)) {
                    AlteredVal *= GlassArt.DamageMultiplier;
                }

                if (player.hasRelic(ShapedGlass.ID)) {
                    int num = 0;

                    for (AbstractRelic r : player.relics) {
                        if (Objects.equals(r.relicId, ShapedGlass.ID)) {
                            num++;
                        }
                    }
                    AlteredVal *= (int) Math.pow(ShapedGlass.AMOUNT, num);
                }
            }
            return SpireReturn.Return(AlteredVal);
        }
    }
}