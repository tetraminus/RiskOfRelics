package riskOfRelics.util;

import basemod.abstracts.CustomSavable;
import riskOfRelics.patches.EnigmaAndMetaPatches;

public class CounterSavers {
    public static class EnigmaCounterSaver implements CustomSavable<Integer> {
        @Override
        public Integer onSave() {
            return EnigmaAndMetaPatches.enigmaCounter;
        }

        @Override
        public void onLoad(Integer s) {
            EnigmaAndMetaPatches.enigmaCounter = s;
        }
    }
    public static class MetamorphCounterSaver implements CustomSavable<Integer> {
        @Override
        public Integer onSave() {
            return EnigmaAndMetaPatches.metamorphCounter;
        }

        @Override
        public void onLoad(Integer s) {
            EnigmaAndMetaPatches.metamorphCounter = s;
        }
    }

}
