package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.GainGoldTextEffect;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.TouchPickupGold;
import riskOfRelics.DefaultMod;
import riskOfRelics.powers.BurningPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class Pennies extends BaseRelic{


    public static final float SCALAR = 0.2f;
    public static final int AMOUNT = 15;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Pennies");
    private static final String IMAGENAME = "Pennies.png";

    public Pennies() {super(ID,IMAGENAME, RelicTier.COMMON, LandingSound.CLINK);}

    @Override
    public void onLoseHp(int damageAmount) {
        player.gainGold(AMOUNT);
        this.addToBot(new VFXAction(new GainGoldTextEffect(AMOUNT)));
        this.addToBot(new VFXAction(new RainingGoldEffect(15, true)));
        super.onLoseHp(damageAmount);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+ AMOUNT + DESCRIPTIONS[1];
    }

}
