package riskOfRelics.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import riskOfRelics.util.TextureLoader;

import static riskOfRelics.RiskOfRelics.*;

public abstract class BaseRelic extends CustomRelic {
    public AbstractCard.CardColor pool = null;
    public RelicType relicType = RelicType.SHARED;
    protected String imageName;

    //for character specific relics
    public BaseRelic(String id, String imageName, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx) {


        super(id, TextureLoader.getTexture(makeRelicPath(imageName)), TextureLoader.getTexture(makeRelicOutlinePath(imageName)), tier, sfx);
        logger.info(makeRelicPath(imageName));
        setPool(pool);


        img = TextureLoader.getTexture(makeRelicPath(imageName));
        outlineImg = TextureLoader.getTexture(makeRelicOutlinePath(imageName));
    }



    public BaseRelic(String id, String imageName, RelicTier tier, LandingSound sfx) {
        super(id, TextureLoader.getTexture(makeRelicPath(imageName)), tier, sfx);

        img = TextureLoader.getTexture(makeRelicPath(imageName));
        outlineImg = TextureLoader.getTexture(makeRelicOutlinePath(imageName));
    }

    @Override
    public void loadLargeImg() {
        if (largeImg == null) {
            this.largeImg = ImageMaster.loadImage(("large/" + imageName + ".png"));
        }
    }

    private void setPool(AbstractCard.CardColor pool) {
        switch (pool) { //Basegame pools are handled differently
            case RED:
                relicType = RelicType.RED;
                break;
            case GREEN:
                relicType = RelicType.GREEN;
                break;
            case BLUE:
                relicType = RelicType.BLUE;
                break;
            case PURPLE:
                relicType = RelicType.PURPLE;
                break;
            default:
                this.pool = pool;
                break;
        }
    }
}