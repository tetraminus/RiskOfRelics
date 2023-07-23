package riskOfRelics.patches;


import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StoreRelic;
import riskOfRelics.relics.YellowKey;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class YellowKeyPatches {
    @SpirePatch2(
            clz = ShopScreen.class,
            method = "initRelics"
    )
    public static class YellowKeyShopPatch {
        @SpirePostfixPatch
        public static void Postfix(ShopScreen __instance) {
            if (!player.hasRelic(YellowKey.ID)){
                AbstractRelic r = new YellowKey();
                StoreRelic sr = new StoreRelic(r, -1, __instance);
                sr.price = 500;
                sr.relic.currentX = 950.0F*Settings.scale;
                sr.relic.currentY = 950.0F*Settings.scale;
                ReflectionHacks.<ArrayList<StoreRelic>>getPrivate(__instance, ShopScreen.class, "relics").add(sr);
            }
        }
    }

    @SpirePatch2(
            clz = StoreRelic.class,
            method = "update"
    )
    public static class YellowKeyPosPatch {
        @SpirePrefixPatch
        public static SpireReturn<Object> Prefix(StoreRelic __instance, float rugY) {
            if (__instance.relic instanceof YellowKey) {
                // 49
                if (!__instance.isPurchased) {// 50
                    __instance.relic.currentX = (((float) Settings.WIDTH /2) - 75 * Settings.xScale);// 52
                    __instance.relic.currentY = rugY + 500 * Settings.yScale ;// 53
                    __instance.relic.hb.move(__instance.relic.currentX, __instance.relic.currentY);// 54
                    __instance.relic.hb.update();// 57
                    if (__instance.relic.hb.hovered) {// 58
                        ReflectionHacks.<ShopScreen>getPrivate(__instance, StoreRelic.class, "shopScreen").moveHand(__instance.relic.currentX - 190.0F * Settings.xScale, __instance.relic.currentY - 70.0F * Settings.yScale);// 59
                        if (InputHelper.justClickedLeft) {// 60
                            __instance.relic.hb.clickStarted = true;// 61
                        }

                        __instance.relic.scale = Settings.scale * 1.25F;// 63
                    } else {
                        __instance.relic.scale = MathHelper.scaleLerpSnap(__instance.relic.scale, Settings.scale);// 65
                    }

                    if (__instance.relic.hb.hovered && InputHelper.justClickedRight) {// 68
                        CardCrawlGame.relicPopup.open(__instance.relic);// 70
                    }
                }

                if (__instance.relic.hb.clicked || __instance.relic.hb.hovered && CInputActionSet.select.isJustPressed()) {// 75
                    __instance.relic.hb.clicked = false;// 76
                    if (!Settings.isTouchScreen) {// 77
                        __instance.purchaseRelic();// 78
                    } else if (AbstractDungeon.shopScreen.touchRelic == null) {// 79
                        if (player.gold < __instance.price) {// 80
                            ReflectionHacks.<ShopScreen>getPrivate(__instance, StoreRelic.class, "shopScreen").playCantBuySfx();// 81
                            ReflectionHacks.<ShopScreen>getPrivate(__instance, StoreRelic.class, "shopScreen").createSpeech(ShopScreen.getCantBuyMsg());// 82
                        } else {
                            AbstractDungeon.shopScreen.confirmButton.hideInstantly();// 84
                            AbstractDungeon.shopScreen.confirmButton.show();// 85
                            AbstractDungeon.shopScreen.confirmButton.isDisabled = false;// 86
                            AbstractDungeon.shopScreen.confirmButton.hb.clickStarted = false;// 87
                            AbstractDungeon.shopScreen.touchRelic = __instance;// 88
                        }
                    }
                }
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}