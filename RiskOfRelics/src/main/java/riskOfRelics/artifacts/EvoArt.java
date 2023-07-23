package riskOfRelics.artifacts;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

public class EvoArt {


    public static int PowersPerAct = 2;
    //make a list of possible power ids: Strength, Dexterity
    public static ArrayList<Class<? extends AbstractPower>> PowerList = new ArrayList<>();
    static {
        PowerList.add(StrengthPower.class);
        PowerList.add(DexterityPower.class);
        PowerList.add(BufferPower.class);

    }





    public static void ApplyRandomPower(AbstractMonster m) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Collections.shuffle(PowerList);
        if (PowerList.get(0).equals(BufferPower.class)){
            Collections.shuffle(PowerList);
        }
        //im going to hell for this. but it works.
        AbstractPower power = PowerList.get(0).getDeclaredConstructor(AbstractCreature.class, int.class).newInstance(m, 1);

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, power, power.amount));
    }



}
