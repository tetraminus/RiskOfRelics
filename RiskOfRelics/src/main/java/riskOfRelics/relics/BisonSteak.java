package riskOfRelics.relics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import riskOfRelics.DefaultMod;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class BisonSteak extends BaseRelic {


    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("BisonSteak");
    private static final String IMAGENAME = "BisonSteak.png";

    public BisonSteak() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof TreasureRoom) {// 22
            this.flash();// 23
            player.increaseMaxHp(AMOUNT,true);
        }
        super.onEnterRoom(room);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}