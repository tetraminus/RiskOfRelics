package riskOfRelics.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import javassist.CtBehavior;
import riskOfRelics.RiskOfRelics;

import static com.megacrit.cardcrawl.core.CardCrawlGame.dungeon;

public class VengPatches {


    @SpirePatch2(
            clz = AbstractDungeon.class,
            method = "getMonsterForRoomCreation"
    )
    public static class VengPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<MonsterGroup> Insert() {




            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.VENGEANCE) && EnigmaAndMetaPatches.vengCounter % 10 == 0) {
                if (AbstractDungeon.bossList.size() == 0) {
                    ReflectionHacks.privateMethod(AbstractDungeon.class, "initializeBoss").invoke(dungeon);
                }
                MonsterGroup m = MonsterHelper.getEncounter(AbstractDungeon.bossList.get(0));
                AbstractDungeon.bossList.remove(0);
                EnigmaAndMetaPatches.vengCounter++;
                return SpireReturn.Return(m);

            }
            EnigmaAndMetaPatches.vengCounter++;
            if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.SWARMS)) {

                MonsterGroup FinalFight = dungeon.getEliteMonsterForRoomCreation();

                AbstractDungeon.eliteMonsterList.remove(0);
                FinalFight.monsters.forEach(m -> {
                    m.currentHealth = m.maxHealth/2;
                    m.healthBarUpdatedEvent();});
                return SpireReturn.Return(FinalFight);

            }





            return SpireReturn.Continue();

        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior C) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher( MonsterHelper.class, "getEncounter");
                return LineFinder.findInOrder(C, finalMatcher);
            }
        }

    }

    @SpirePatch2(
            clz = AbstractDungeon.class,
            method = "setBoss"
    )
    public static class RemoveFirstBossPatch{
        @SpirePostfixPatch
        public static void Postfix(){
            if (AbstractDungeon.bossList.size() > 2){
                AbstractDungeon.bossList.remove(AbstractDungeon.bossKey);
            }


        }
    }

}

