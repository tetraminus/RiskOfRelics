package riskOfRelics.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.util.TextureLoader;

import static riskOfRelics.RiskOfRelics.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class CBHeal extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final int HEALAMOUNT = 2;
    public static final String POWER_ID = RiskOfRelics.makeID("CBHeal");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public CBHeal(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;


        this.owner = owner;
        this.amount = amount;

        this.source = source;

        type = PowerType.BUFF;

        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    // On use card, apply (amount) of Dexterity. (Go to the actual power card for the amount.)


    // Note: If you want to apply an effect when a power is being applied you have 3 options:
    //onInitialApplication is "When THIS power is first applied for the very first time only."
    //onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
    //onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."


    // At the end of the turn, remove gained Dexterity.
    @Override
    public void atEndOfTurn(final boolean isPlayer) {

        AbstractDungeon.player.heal(HEALAMOUNT, true);
        if (amount>=2) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, POWER_ID, 2));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, POWER_ID, amount));
        }


    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {

            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];

    }

    @Override
    public AbstractPower makeCopy() {
        return new TonicPower(owner, source, amount);
    }
}
