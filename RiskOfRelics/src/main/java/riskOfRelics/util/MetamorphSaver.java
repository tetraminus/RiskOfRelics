package riskOfRelics.util;

import basemod.abstracts.CustomSavable;
import riskOfRelics.RiskOfRelics;

public class MetamorphSaver implements CustomSavable<String> {


    @Override
    public String onSave() {
        return RiskOfRelics.MetamorphCharacter;
    }

    @Override
    public void onLoad(String s) {
        RiskOfRelics.MetamorphCharacter = s;
    }



}
