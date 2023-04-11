package riskOfRelics.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import riskOfRelics.patches.RerollRewardPatch;

public class RerollReward extends CustomReward {
    private static final Texture ICON = new Texture(Gdx.files.internal("[pathtotexturefile]"));

    public RerollReward() {
        super(ICON, "Reroll the relic rewards.", RerollRewardPatch.RISKOFRELICS_REROLL);


    }

    @Override
    public boolean claimReward() {
        AbstractDungeon.getCurrRoom().rewards.remove(this);
        for (RewardItem reward : AbstractDungeon.getCurrRoom().rewards) {
            if (reward.type == RewardItem.RewardType.RELIC) {
                AbstractDungeon.getCurrRoom().rewards.remove(reward);
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(AbstractDungeon.returnRandomRelic(reward.relic.tier)));
            }
        }
        return true;
    }
}
