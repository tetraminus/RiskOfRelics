package riskOfRelics.rewards;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import riskOfRelics.RiskOfRelics;
import riskOfRelics.patches.RerollRewardPatch;
import riskOfRelics.relics.Recycler;
import riskOfRelics.util.TextureLoader;

import java.util.ArrayList;
import java.util.List;

public class RerollReward extends CustomReward {
    public static String iconpath = "riskOfRelicsResources/images/ui/reward/RerollReward.png";
    private static final Texture ICON = TextureLoader.getTexture(iconpath);

    public RerollReward() {
        super(ICON, Recycler.RerollText, RerollRewardPatch.RISKOFRELICS_REROLL);
    }
    @Override
    public boolean claimReward() {

        ArrayList<RewardItem> rewards = AbstractDungeon.getCurrRoom().rewards;
        if (Loader.isModLoaded("BossyRelics") ) {
            rewards.clear();

            try {
                Class brclass = Class.forName("theDefault.patches.RoomAddRelicRewardPatch");
                for (List<RewardItem> brrewards : (List<List<RewardItem>>) ReflectionHacks.getPrivateStatic(brclass,"linkedRewardItems") ) {
                    rewards.addAll(brrewards);
                }
            } catch (ClassNotFoundException e) {
                RiskOfRelics.logger.error("Failed to get BossyRelics rewards");
            }


        }

        for (RewardItem reward : rewards) {
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