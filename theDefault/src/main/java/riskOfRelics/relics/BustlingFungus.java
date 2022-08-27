package riskOfRelics.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import riskOfRelics.DefaultMod;
import riskOfRelics.util.TextureLoader;

import static riskOfRelics.DefaultMod.makeRelicOutlinePath;
import static riskOfRelics.DefaultMod.makeRelicPath;

public class BustlingFungus extends BaseRelic {

    public static boolean Canceled = false;
    public static final int HEAL_AMOUNT = 2;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("BustlingFungus");
    private static final String IMAGENAME = "bustlingFungus.png";

    public BustlingFungus() {super(ID,IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);}



    @Override
    public void atBattleStart() {

    }

    @Override
    public void atTurnStart() {
        beginLongPulse();
        super.atTurnStart();
    }

    @Override
    public void onPlayerEndTurn() {

        if (!Canceled){
            flash();
            AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEAL_AMOUNT));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }

        super.onPlayerEndTurn();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
    Canceled = true;
    stopPulse();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
