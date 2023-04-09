package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import riskOfRelics.DefaultMod;


public class FrostRelic extends BaseRelic {


    public static final int AMOUNT = 1;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("FrostRelic");
    private static final String IMAGENAME = "FrostRelic.png";

    public FrostRelic() {
        super(ID, IMAGENAME, AbstractCard.CardColor.BLUE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        counter=0;
        super.atBattleStart();
    }

    @Override
    public void onVictory() {
        counter=-1;
        super.onVictory();
    }

    @Override
    public void atTurnStart() {
        for (int i = 0; i < counter; i++) {
            flash();
            this.addToBot(new ChannelAction(new Frost()));
        }
        super.atTurnStart();
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        counter++;
        super.onMonsterDeath(m);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}