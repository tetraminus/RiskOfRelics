package riskOfRelics.util;

import basemod.abstracts.CustomSavableRaw;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.patches.scrap.ScrapField;

public class ScrapInfoSaver implements CustomSavableRaw {


    @Override
    public JsonElement onSaveRaw() {
        JsonObject json = new JsonObject();
        ScrapInfo sc = ScrapField.scrapFieldPatch.scrapInfo.get(AbstractDungeon.player);

        json.addProperty("commonScrap", sc.commonScrap);
        json.addProperty("uncommonScrap", sc.uncommonScrap);
        json.addProperty("rareScrap", sc.rareScrap);

        return json;
    }

    @Override
    public void onLoadRaw(JsonElement jsonElement) {

        if (jsonElement == null) {
            return;
        }

        JsonObject json = jsonElement.getAsJsonObject();
        ScrapInfo sc = ScrapField.scrapFieldPatch.scrapInfo.get(AbstractDungeon.player);

        sc.commonScrap = json.get("commonScrap").getAsInt();
        sc.uncommonScrap = json.get("uncommonScrap").getAsInt();
        sc.rareScrap = json.get("rareScrap").getAsInt();
    }
}
