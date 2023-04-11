package riskOfRelics.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import riskOfRelics.patches.RerollRewardPatch;
import riskOfRelics.relics.Recycler;
import riskOfRelics.util.TextureLoader;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.combatRewardScreen;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static riskOfRelics.DefaultMod.makeRelicPath;

public class RerollReward extends CustomReward {
    public static String iconpath = "riskOfRelicsResources/images/ui/reward/RerollReward.png";
    private static final Texture ICON = TextureLoader.getTexture(iconpath);

    public RerollReward() {
        super(ICON, Recycler.RerollText, RerollRewardPatch.RISKOFRELICS_REROLL);
//        for (RewardItem r: AbstractDungeon.getCurrRoom().rewards) {
//            relicLink = r;
//
//        }


    }



    @Override
    public boolean claimReward() {



        for (RewardItem reward : AbstractDungeon.getCurrRoom().rewards) {
            if (reward.type == RewardItem.RewardType.RELIC && !reward.isDone && !reward.ignoreReward) {
                reward.relic = AbstractDungeon.returnRandomRelic(AbstractDungeon.returnRandomRelicTier());
                if(reward.text != null && reward.relic != null) {
                    reward.text = reward.relic.name;
                }
            }
        }
        isDone = true;
        return true;
    }
}
