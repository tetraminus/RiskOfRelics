package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import riskOfRelics.DefaultMod;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class perfAspect extends BaseRelic {


    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("perfAspect");
    private static final String IMAGENAME = "perfAspect.png";

    public perfAspect() {
        super(ID, IMAGENAME, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;

        super.atTurnStart();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        this.counter++;
        if (this.counter == AMOUNT) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player, new DexterityPower(target, -AMOUNT), -AMOUNT));
            //add one dex to player
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new DexterityPower(player, AMOUNT), AMOUNT));
        }

    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
        super.atBattleStart();
    }

    @Override
    public void onVictory() {
        this.counter = 0;
        super.onVictory();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}