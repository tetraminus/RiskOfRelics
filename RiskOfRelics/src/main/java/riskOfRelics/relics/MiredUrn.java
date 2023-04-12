package riskOfRelics.relics;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.DefaultMod;
import riskOfRelics.vfx.UrnSuckEffect;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class MiredUrn extends BaseRelic {


    public static final int AMOUNT = 1;
    public AbstractMonster target;

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("MiredUrn");
    private static final String IMAGENAME = "MiredUrn.png";
    private int targetIndex = 0 ;
    public MiredUrn() {
        super(ID, IMAGENAME, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        target = AbstractDungeon.getMonsters().monsters.get(targetIndex);
        super.atBattleStart();
    }

    @Override
    public void atTurnStart() {
        if (target.isDeadOrEscaped()) {
            targetIndex++;
            target = AbstractDungeon.getMonsters().monsters.get(targetIndex);
        }
        if (target != null){
            this.flash();
            this.addToBot(new DamageAction(target, new DamageInfo(player, AMOUNT, DamageInfo.DamageType.THORNS)));
            this.addToBot(new HealAction(player, player, AMOUNT));
            this.addToBot(new VFXAction(new UrnSuckEffect(target.hb.cX, target.hb.cY, player.hb.cX, player.hb.cY)));
        }

        super.atTurnStart();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];
    }

}