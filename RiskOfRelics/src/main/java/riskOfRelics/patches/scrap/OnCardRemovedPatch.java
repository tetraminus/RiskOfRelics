package riskOfRelics.patches.scrap;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import riskOfRelics.util.ScrapInfo;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

@SpirePatch(
        clz = CardGroup.class,
        method = "removeCard",
        paramtypez = {AbstractCard.class}
)
public class OnCardRemovedPatch {


    public static void Postfix(CardGroup __instance, AbstractCard c) {
        if (__instance.type == CardGroup.CardGroupType.MASTER_DECK) {// 19
            ScrapInfo info = ScrapField.scrapFieldPatch.scrapInfo.get(player);
            switch (c.rarity) {
                case COMMON:
                    info.commonScrap++;
                    break;
                case UNCOMMON:
                    info.uncommonScrap++;
                    break;
                case RARE:
                    info.rareScrap++;
                    break;
            }


        }

    }// 26
}