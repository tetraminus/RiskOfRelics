package riskOfRelics.relics;

import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.DefaultMod;
import riskOfRelics.powers.CBHeal;
import riskOfRelics.util.TextureLoader;

import static riskOfRelics.DefaultMod.makeRelicOutlinePath;
import static riskOfRelics.DefaultMod.makeRelicPath;


public class CorpseBloom extends BaseRelic {

    boolean isCorpsebloomHeal;
    public static final float HEAL_AMOUNT = 50.0f;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("corpsebloom");


    private static final String IMAGENAME = "Corpsebloom.png";

    public CorpseBloom() {super(ID,IMAGENAME, RelicTier.BOSS, LandingSound.MAGICAL);}




    @Override
    public void atBattleStart() {
        super.atBattleStart();
    }

    @Override
    public void onPlayerEndTurn() {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && counter > 0) {
            isCorpsebloomHeal = true;
            float healed = counter/HEAL_AMOUNT;
            AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, (int)Math.ceil(healed)));
            counter -= healed;
            flash();
        }
        super.onPlayerEndTurn();
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        if (AbstractDungeon.getCurrRoom().phase.equals(AbstractRoom.RoomPhase.COMBAT)) {
            if (isCorpsebloomHeal) {
                isCorpsebloomHeal = false;
                return super.onPlayerHeal(healAmount);

            } else {
                counter += healAmount * 2;
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                return 0;

            }
        } else {
            return super.onPlayerHeal(healAmount);
        }
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }



}
