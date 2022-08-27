package riskOfRelics.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class UkuleleAction extends AbstractGameAction {
    public int damage;
    private boolean firstFrame;

    public UkuleleAction(AbstractCreature source, AbstractCreature target, int amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect, boolean isFast) {
        this.firstFrame = true;
        this.setValues(target, source, amount);
        this.damage = amount;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        if (isFast) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FAST;
        }

    }

    public UkuleleAction(AbstractCreature source, AbstractCreature target, int amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect) {
        this(source, target, amount, type, effect, false);
    }

    @Override
    public void update() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            if (m == target){

                continue;
            }
            this.addToTop(new VFXAction(new LightningEffect(this.target.drawX, this.target.drawY)));
            this.addToTop(new VFXAction(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect)));
            this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
            AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.damage, this.damageType)));
        }

        this.isDone = true;

    }
}

