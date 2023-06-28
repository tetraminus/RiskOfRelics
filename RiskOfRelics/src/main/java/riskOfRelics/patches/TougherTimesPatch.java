

package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import riskOfRelics.DefaultMod;
import riskOfRelics.relics.tougherTimes;

@SpirePatch(
    clz = AbstractPlayer.class,
    method = "damage"
)
public class TougherTimesPatch {
    private static final String[] TEXT;

    public TougherTimesPatch() {
    }// 18

    public static SpireReturn Prefix(AbstractPlayer self, DamageInfo info) {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT && info.type == DamageType.NORMAL && info.output > 0 && self.hasRelic(DefaultMod.makeID("tougherTimes")) && AbstractDungeon.relicRng.random(99) < tougherTimes.AMOUNT) {// 22
            self.getRelic(DefaultMod.makeID("tougherTimes")).flash();// 23
            int damageAmount = 0;// 24
            AbstractDungeon.effectList.add(new BlockedWordEffect(self, self.hb.cX, self.hb.cY, TEXT[2]));// 41
            return SpireReturn.Return((Object)null);// 42
        } else {
            return SpireReturn.Continue();// 44
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID("tougherTimes")).DESCRIPTIONS;// 19
    }
}