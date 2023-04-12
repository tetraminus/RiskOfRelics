package riskOfRelics.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import riskOfRelics.patches.RerollRewardPatch;
import riskOfRelics.relics.Recycler;
import riskOfRelics.util.TextureLoader;

public class RerollReward extends CustomReward {
    public static String iconpath = "riskOfRelicsResources/images/ui/reward/RerollReward.png";
    private static final Texture ICON = TextureLoader.getTexture(iconpath);

    public RerollReward() {
        super(ICON, Recycler.RerollText, RerollRewardPatch.RISKOFRELICS_REROLL);
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