package riskOfRelics.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomPotion;
import riskOfRelics.cards.curses.tonicAffliction;
import riskOfRelics.powers.EnergyDrinkPower;
import riskOfRelics.powers.TonicPower;

public class EnergyDrink extends CustomPotion {
    public static final Color POTION_LIQUID = CardHelper.getColor(136, 216, 132);//Blue
    public static final Color POTION_HYBRID = CardHelper.getColor(218, 224, 40); //Green
    public static final Color POTION_SPOTS = CardHelper.getColor(1, 21, 31); // Dark Blue
    public static final String POTION_ID = riskOfRelics.DefaultMod.makeID("energyDrink");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public EnergyDrink() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.BOLT, PotionColor.ATTACK);

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1] + DESCRIPTIONS[3] ;//+ potency + DESCRIPTIONS[1] + DESCRIPTIONS[3];

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;

        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));

    }
    // See that description? It has DESCRIPTIONS[1] instead of just hard-coding the "text " + potency + " more text" inside.
    // DO NOT HARDCODE YOUR STRINGS ANYWHERE, it's really bad practice to have "Strings" in your code:

    /*
     * 1. It's bad for if somebody likes your mod enough (or if you decide) to translate it.
     * Having only the JSON files for translation rather than 15 different instances of "Dexterity" in some random cards is A LOT easier.
     *
     * 2. You don't have a centralised file for all strings for easy proof-reading. If you ever want to change a string
     * you don't have to go through all your files individually/pray that a mass-replace doesn't screw something up.
     *
     * 3. Without hardcoded strings, editing a string doesn't require a compile, saving you time (unless you clean+package).
     *
     */

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new EnergyDrinkPower(target, target, potency), 1));

    }

    @Override
    public AbstractPotion makeCopy() {
        return new EnergyDrink();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 1;
    }
    public void upgradePotion()
    {
        potency += 1;
        tips.clear();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[2] + DESCRIPTIONS[3];

        tips.add(new PowerTip(name, description));
    }


}