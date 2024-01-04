package riskOfRelics.relics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import riskOfRelics.RiskOfRelics;


public class BisonSteak extends BaseRelic {


    public static final int AMOUNT = 2;
    // ID, images, text.
    public static final String ID = RiskOfRelics.makeID("BisonSteak");
    private static final String IMAGENAME = "BisonSteak.png";

    public BisonSteak() {
        super(ID, IMAGENAME, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+AMOUNT+DESCRIPTIONS[1];
    }
    
    @Override
    public void onEquip() {
        this.counter = AbstractDungeon.player.relics.size();
    }

    @Override
    public void renderCounter(SpriteBatch sb, boolean inTopPanel) {

    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.id != null){
            if (AbstractDungeon.player != null){
                if (AbstractDungeon.player.relics.size() > this.counter){
                    this.counter++;
                    AbstractDungeon.player.increaseMaxHp(AMOUNT, true);
                }else if (this.counter > AbstractDungeon.player.relics.size()){
                    this.counter = AbstractDungeon.player.relics.size();
                }
            }
        }
    }
}