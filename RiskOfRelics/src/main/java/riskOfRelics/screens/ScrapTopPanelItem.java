package riskOfRelics.screens;

import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import riskOfRelics.patches.RecyclerPatches;
import riskOfRelics.patches.scrap.ScrapField;
import riskOfRelics.util.ScrapInfo;

public class ScrapTopPanelItem extends TopPanelItem {
    public static final Texture IMG = new Texture("riskOfRelicsResources/images/ui/ambrySelect/Artifact3_off.png");
    public static final String ID = "riskOfRelics:ScrapTopPanelItem";

    private static final String[] uiStrings = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public ScrapTopPanelItem() {
        super(IMG, ID);
    }

    @Override
    protected void onClick() {

    }

    @Override
    protected void onHover() {
        super.onHover();
        TipHelper.renderGenericTip(this.x, this.y-100,  uiStrings[0], GenerateTip() );


    }

    private String GenerateTip(){
        ScrapInfo info = ScrapField.scrapFieldPatch.scrapInfo.get(AbstractDungeon.player);

        String tip = "";
        tip += uiStrings[1] + info.commonScrap + uiStrings[2];
        tip += uiStrings[1] + info.uncommonScrap + uiStrings[3];
        tip += uiStrings[1] + info.rareScrap + uiStrings[4];

        return tip;
    }

}
