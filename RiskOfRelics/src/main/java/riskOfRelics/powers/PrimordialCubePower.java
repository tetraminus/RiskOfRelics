package riskOfRelics.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import riskOfRelics.util.TextureLoader;

import static riskOfRelics.RiskOfRelics.makeID;
import static riskOfRelics.RiskOfRelics.makePowerPath;

public class PrimordialCubePower extends AbstractPower{
    public static final String POWER_ID = makeID("PrimordialCubePower");
    private static final PowerType POWER_TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean IS_TURN_BASED = false;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public PrimordialCubePower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;


        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.NORMAL){
            for (AbstractCreature m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped() && m != this.owner) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(this.owner, damageAmount, DamageInfo.DamageType.THORNS)));
                }
            }
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);

        amount--;
        if(amount <= 0){
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }



    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
