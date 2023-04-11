package riskOfRelics.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class PolyluteAction extends AbstractGameAction {
    private final int hits;
    private final int damage;

    public PolyluteAction(AbstractCreature target, int damage, int hits) {
        this.target = target;
        this.hits = hits;
        this.damage = damage;


    }

    @Override
    public void update() {

        for (int i = 0; i < hits; i++) {
            this.addToBot(new VFXAction(new LightningEffect(this.target.drawX, this.target.drawY)));
            this.addToBot(new VFXAction(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect)));
            this.addToBot(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
            addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AttackEffect.LIGHTNING));
        }

        isDone = true;
    }
}
