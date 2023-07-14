package riskOfRelics.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.util.TextureLoader;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static riskOfRelics.RiskOfRelics.makePowerPath;

public class SpitePower extends TwoAmountPower {
    public AbstractCreature source;

    public static final String POWER_ID = RiskOfRelics.makeID("SpitePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int SpiteIdOffset;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("SpitePower_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("SpitePower_32.png"));
    public SpitePower(AbstractCreature owner, int amount) {
        name = NAME;
        this.ID = POWER_ID + SpiteIdOffset;
        SpiteIdOffset++;

        this.owner = owner;
        this.amount = 3;
        this.amount2 = amount;


        type = PowerType.DEBUFF;


        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();



    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));// 35
            if (this.amount == 1) {// 36
                this.addToBot(new DamageAction(player, new DamageInfo(null, amount2, DamageInfo.DamageType.THORNS)));// 37 40
            }
        }
        super.atEndOfTurn(isPlayer);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]  + amount2 + DESCRIPTIONS[2];
    }

}
