package riskOfRelics.cards.colorless;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import riskOfRelics.DefaultMod;
import riskOfRelics.cards.AbstractEquipmentCard;
import riskOfRelics.powers.CrowdfunderPower;

import static riskOfRelics.DefaultMod.makeCardPath;

// public class missileLancher extends AbstractDynamicCard

// Remove this line when you make a template. Refer to https://github.com/daviscook477/BaseMod/wiki/AutoAdd if you want to know what it does.
public class Crowdfunder extends AbstractEquipmentCard {

    /*
     * "Hey, I wanna make a bunch of cards now." - You, probably.
     * ok cool my dude no problem here's the most convenient way to do it:
     *
     * Copy all of the code here (Ctrl+A > Ctrl+C)
     * Ctrl+Shift+A and search up "file and code template"
     * Press the + button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
     * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with missileLancher
     * And then you can do custom ones like 4 and ALL if you want.
     * I'll leave some comments on things you might consider replacing with what.
     *
     * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template you'll
     * likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe you want a
     * universal template where you delete everything unnecessary - up to you)
     *
     * You can create templates for anything you ever want to. Cards, relics, powers, orbs, etc. etc. etc.
     */

    // TEXT DECLARATION

    // public static final String ID = DefaultMod.makeID(missileLauncher.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String ID = DefaultMod.makeID("Crowdfunder"); // DELETE THIS ONE.
    public static final String IMG = makeCardPath("Crowdfunder.png");// "public static final String IMG = makeCardPath("missileLancher.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //


    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 3;    // DAMAGE = 4

    private static final int AMOUNT = 3;
    private static final int UPGRADE_PLUS_AMOUNT = 2;  // UPGRADE_PLUS_DMG = 2

    // /STAT DECLARATION/


    public Crowdfunder() { // public missileLancher() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = AMOUNT;

        setCharges(1);
        CURRENT_CHARGES = MAX_CHARGES;
        // 59
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new CrowdfunderPower(p, p, this.magicNumber), this.magicNumber));

        super.use(p, m);
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            this.upgradeMagicNumber(UPGRADE_PLUS_AMOUNT);

            initializeDescription();
        }
    }


}
