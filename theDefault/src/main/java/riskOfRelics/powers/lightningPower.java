package riskOfRelics.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.DefaultMod;
import riskOfRelics.util.TextureLoader;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static riskOfRelics.DefaultMod.makePowerPath;

public class lightningPower extends AbstractPower {
    private final int damage;

    public static final String POWER_ID = DefaultMod.makeID("lightningPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int bombIdOffset;
    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("lightningPower_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("lightningPower_32.png"));

    public lightningPower(AbstractCreature owner, int damage) {
        name = NAME;
        ID = POWER_ID + bombIdOffset;
        bombIdOffset++;
        this.owner = owner;
        this.damage = damage;

        type = PowerType.DEBUFF;
        isTurnBased = false;


        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();// 28
    }// 30
    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {// 65 66
            this.flashWithoutSound();// 67
            this.addToBot(new RemoveSpecificPowerAction(owner,owner, this));
            this.addToBot(new DamageAllEnemiesAction(player, DamageInfo.createDamageMatrix(this.damage ,true), DamageType.THORNS, AttackEffect.LIGHTNING));
        }

    }// 70
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.damage + DESCRIPTIONS[1];

    }// 54


}
