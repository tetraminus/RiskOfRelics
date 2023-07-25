package riskOfRelics.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.util.TextureLoader;

import static riskOfRelics.RiskOfRelics.makePowerPath;

public class RuinPower extends AbstractPower {


    public static final String POWER_ID = RiskOfRelics.makeID("RuinPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("RuinPower_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("RuinPower_32.png"));
    public RuinPower(AbstractCreature target, int amount) {

        name = NAME;
        ID = POWER_ID;


        this.owner = target;
        this.amount = amount;

        type = PowerType.DEBUFF;

        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onSpecificTrigger() {
        this.addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS)));
        this.addToBot(new VFXAction(new com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect(this.owner.hb.cX, this.owner.hb.cY), 0.1F));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));


        super.onSpecificTrigger();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        super.updateDescription();
    }
}
