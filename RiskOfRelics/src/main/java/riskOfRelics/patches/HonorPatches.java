package riskOfRelics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.vfx.ArtifactAboveCreatureAction;

import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectsQueue;

public class HonorPatches {
    public static void ApplyBurningToRoom(){
        Iterator var1;
        AbstractMonster m;
        switch (AbstractDungeon.mapRng.random(0, 3)) {// 37
            case 0:
                var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();// 39

                while(var1.hasNext()) {
                    m = (AbstractMonster)var1.next();
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new StrengthPower(m, AbstractDungeon.actNum + 1), AbstractDungeon.actNum + 1));// 40
                }

                return;// 78
            case 1:
                var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();// 50

                while(var1.hasNext()) {
                    m = (AbstractMonster)var1.next();
                    AbstractDungeon.actionManager.addToBottom(new IncreaseMaxHpAction(m, 0.25F, true));// 51
                }

                return;
            case 2:
                var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();// 55

                while(var1.hasNext()) {
                    m = (AbstractMonster)var1.next();
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new MetallicizePower(m, AbstractDungeon.actNum * 2 + 2), AbstractDungeon.actNum * 2 + 2));// 56
                }

                return;
            case 3:
                var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();// 65

                while(var1.hasNext()) {
                    m = (AbstractMonster)var1.next();
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new RegenerateMonsterPower(m, 1 + AbstractDungeon.actNum * 2), 1 + AbstractDungeon.actNum * 2));// 66
                }
        }
    }

    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "preBattlePrep"
    )
    public static class HonorPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance) {
            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.HONOR) ){

                    ApplyBurningToRoom();
                    effectsQueue.add(new ArtifactAboveCreatureAction(Settings.WIDTH/2, Settings.HEIGHT/2, RiskOfRelics.Artifacts.HONOR));


            }
        }
    }


}
