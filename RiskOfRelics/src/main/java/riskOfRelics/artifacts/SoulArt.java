package riskOfRelics.artifacts;

import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.LouseNormal;
import com.megacrit.cardcrawl.powers.MinionPower;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.actions.FixMonsterAction;

import java.util.ArrayList;
import java.util.logging.Logger;

public class SoulArt {
    private static ArrayList<AbstractMonster> SpawnedLice = new ArrayList<>();
    private static Logger logger = Logger.getLogger(RiskOfRelics.class.getName());

    public static void ClearLice() {
        SpawnedLice.clear();
    }
    public static void onMonsterDeath(AbstractMonster monster) {
        if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.SOUL) && !SpawnedLice.contains(monster) && !monster.hasPower(MinionPower.POWER_ID)) {
            AbstractMonster louse = new LouseNormal(monster.drawX - Settings.WIDTH *0.75f, monster.drawY - AbstractDungeon.floorY * Settings.scale);
            louse.currentHealth = louse.maxHealth = monster.maxHealth / 2;

            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(louse, false,-99));// 122
            AbstractDungeon.actionManager.addToBottom(new FixMonsterAction(louse));

            SpawnedLice.add(louse);
            //logger.log(java.util.logging.Level.INFO, "Spawned Louse at " + louse.drawX + ", " + louse.drawY);


        }
    }
}
