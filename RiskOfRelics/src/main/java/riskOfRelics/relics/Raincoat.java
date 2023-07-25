package riskOfRelics.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.util.OnLoseArtifactRelic;


public class Raincoat extends BaseRelic implements OnLoseArtifactRelic {


    public static final int AMOUNT = 10;
    public static final int ARTIFACT = 1;

    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("Raincoat");
    private static final String IMAGENAME = "Raincoat.png";

    public Raincoat() {
        super(ID, IMAGENAME, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, ARTIFACT), ARTIFACT));
        super.atBattleStart();
    }



    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+ARTIFACT+DESCRIPTIONS[1]+AMOUNT+DESCRIPTIONS[2];
    }

    @Override
    public void onLoseArtifact() {
        this.addToBot(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, AMOUNT));
    }
}