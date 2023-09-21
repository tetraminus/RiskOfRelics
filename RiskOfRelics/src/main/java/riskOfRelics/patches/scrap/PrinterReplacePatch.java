package riskOfRelics.patches.scrap;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.chests.MediumChest;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;
import riskOfRelics.chests.Printer;

public class PrinterReplacePatch {
    @SpirePatch2(
            clz = AbstractDungeon.class,
            method = "getRandomChest"

    )
    public static class PrinterReplace {
        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {
                @Override
                public void edit(NewExpr m) throws CannotCompileException {
                    if (m.getClassName().equals(MediumChest.class.getName())) {

                        m.replace(
                                "{ " +
                                    "if (!"+Printer.class.getName()+".canSpawn()){" +
                                        "$_ = $proceed($$);" +
                                    "} else {" +
                                        "$_ = new " + Printer.class.getName() + "(); " +
                                    "}" +
                                "}");
                    }

                }
            };
        }



    }

}
