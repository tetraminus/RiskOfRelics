package riskOfRelics.patches;


import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import javassist.CtBehavior;

import java.util.ArrayList;


public class FCPatches {

    @SpirePatch2(clz = BossRelicSelectScreen.class, method = "open")
    public static class BossRelicSelectScreenPatch {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"r","r2", "r3"}
        )
        public static void insert(AbstractRelic ___r,AbstractRelic ___r2, AbstractRelic ___r3,BossRelicSelectScreen __instance) {
            if (AbstractDungeon.player.hasRelic("riskOfRelics:FocusedConvergence")) {
                __instance.relics.remove(___r2);
                ___r2.grayscale = true;
                __instance.relics.remove(___r3);
                ___r3.grayscale = true;
                ___r.currentX = ReflectionHacks.getPrivateStatic(BossRelicSelectScreen.class, "SLOT_1_X");
                ___r.currentY = AbstractDungeon.floorY + 292.0F * Settings.scale;
                ___r.hb.move(___r.currentX, ___r.currentY);


                AbstractDungeon.bossRelicPool.add(___r2.relicId);
                AbstractDungeon.bossRelicPool.add(___r3.relicId);
            }
        }
        private static class Locator extends SpireInsertLocator { // Hey welcome to our SpireInsertLocator class!
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "iterator");

                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

            }
        }
    }


    @SpirePatch2(clz = MonsterRoomBoss.class, method = "getCardRarity")
    public static class BossCardDropPatch{

        @SpirePrefixPatch
        public static SpireReturn<AbstractCard.CardRarity> Prefix(MonsterRoomBoss __instance, int roll) {
            if (AbstractDungeon.player.hasRelic("riskOfRelics:FocusedConvergence")) {
                return SpireReturn.Return(AbstractCard.CardRarity.COMMON);
            }
            return SpireReturn.Continue();
        }


    }



}
