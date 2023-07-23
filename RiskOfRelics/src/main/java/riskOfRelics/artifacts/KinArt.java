package riskOfRelics.artifacts;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import riskOfRelics.RiskOfRelics;

public class KinArt {


    public static void onMonsterDeath() {
        if (RiskOfRelics.ActiveArtifacts.contains(RiskOfRelics.Artifacts.KIN)) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m != null && !m.isDeadOrEscaped()) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new StrengthPower(m, 10), 10));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new LoseStrengthPower(m, 10), 10));
                }

            }
        }
    }
}
