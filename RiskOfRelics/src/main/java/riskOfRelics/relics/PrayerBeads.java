package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.cards.colorless.PrayerBead;


public class PrayerBeads extends BaseRelic {


    public static final int AMOUNT = 1;
    public static final int CARDS = 5;



    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("PrayerBeads");
    private static final String IMAGENAME = "PrayerBeads.png";

    public PrayerBeads() {
        super(ID, IMAGENAME, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        counter = 0;
    }

    @Override
    public void onEquip() {
        super.onEquip();

        for (int i = 0; i < CARDS; i++) {
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new PrayerBead(), (float) Settings.WIDTH / (CARDS+2) * (float)i+1, (float)Settings.HEIGHT / 2.0F));
        }

    }

    @Override
    public void atBattleStart() {
        this.flash();// 24
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, counter), counter));// 25
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 31
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARDS + DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];
    }

    public void PrayerBeadRemoved() {
        flash();

        counter += AMOUNT;

    }
}