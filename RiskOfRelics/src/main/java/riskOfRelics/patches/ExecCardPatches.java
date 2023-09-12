package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import javassist.CtBehavior;
import riskOfRelics.relics.ExecutiveCard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ExecCardPatches {
    @SpirePatch2(clz = ShopScreen.class, method = "init")
    public static class ExecCardPatch {
        public static void Postfix(ShopScreen __instance) {
           if (player.hasRelic(ExecutiveCard.ID)){
               player.getRelic(ExecutiveCard.ID).flash();
               __instance.applyDiscount((100f-ExecutiveCard.AMOUNT)/100f, true);

           }
        }
    }
    @SpirePatch(
            clz= CardRewardScreen.class,
            method="cardSelectUpdate"
    )
    public static class ExecCardPatch2 {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"hoveredCard", "skipButton"}

        )
        public static SpireReturn<Void> Insert(CardRewardScreen __instance,  AbstractCard hoveredCard, SkipCardButton skipButton) {
            if(__instance.rItem == null || __instance.rItem.cards == null){
                return SpireReturn.Continue();
            }
            if(!player.hasRelic(ExecutiveCard.ID)|| ((ExecutiveCard)player.getRelic(ExecutiveCard.ID)).usedRewards.contains(__instance.rItem)){
                return SpireReturn.Continue();
            }
            __instance.rItem.cards.remove(hoveredCard);

            if(!__instance.rItem.cards.isEmpty()){
                skipButton.show();
                ((ExecutiveCard) player.getRelic(ExecutiveCard.ID)).usedRewards.add(__instance.rItem);

                player.getRelic(ExecutiveCard.ID).flash();
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(CardRewardScreen.class, "takeReward");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }


}
