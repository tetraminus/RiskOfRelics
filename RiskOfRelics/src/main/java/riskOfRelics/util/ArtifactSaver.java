package riskOfRelics.util;

import basemod.abstracts.CustomSavable;
import riskOfRelics.RiskOfRelics;

public class ArtifactSaver implements CustomSavable<Integer[]> {
    public Integer[] onSave() {
        Integer[] temp = new Integer[RiskOfRelics.ActiveArtifacts.size()];
        for (RiskOfRelics.Artifacts a : RiskOfRelics.ActiveArtifacts) {
            //Add it to temp
            temp[RiskOfRelics.ActiveArtifacts.indexOf(a)] = a.ordinal();
        }

        return temp;
    }



    @Override
    public void onLoad(Integer[] artifacts) {
        for ( Integer i : artifacts){
            if (i != null) {
                RiskOfRelics.ActiveArtifacts.add(RiskOfRelics.getArtifact(i));
            }

        }


    }
}
