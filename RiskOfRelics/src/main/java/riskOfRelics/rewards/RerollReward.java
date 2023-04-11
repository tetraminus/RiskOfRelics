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

import static riskOfRelics.DefaultMod.makeRelicPath;

public class RerollReward extends CustomReward {
    public static String iconpath = "riskOfRelicsResources/images/ui/reward/RerollReward.png";
    private static final Texture ICON = TextureLoader.getTexture(iconpath);

    public RerollReward() {
        super(ICON, Recycler.RerollText, RerollRewardPatch.RISKOFRELICS_REROLL);


    }

    @Override
    public boolean claimReward() {
        List<RewardItem> toremove = new ArrayList<>();
        AbstractDungeon.getCurrRoom().rewards.remove(this);
        for (RewardItem reward : AbstractDungeon.getCurrRoom().rewards) {
            if (reward.type == RewardItem.RewardType.RELIC) {
                toremove.add(reward);
            }
        }

        for (RewardItem reward : toremove) {
            AbstractDungeon.getCurrRoom().rewards.remove(reward);
            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(AbstractDungeon.returnRandomRelic(reward.relic.tier)));
        }
        AbstractDungeon.combatRewardScreen.open();


        return true;
    }
}
