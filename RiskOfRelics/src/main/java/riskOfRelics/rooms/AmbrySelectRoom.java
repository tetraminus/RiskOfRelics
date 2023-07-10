package riskOfRelics.rooms;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import riskOfRelics.screens.ArtifactSelectScreen;

import static basemod.BaseMod.openCustomScreen;

public class AmbrySelectRoom extends AbstractRoom {

    public AmbrySelectRoom() {
        phase = RoomPhase.EVENT;
        mapSymbol = "?";
        mapImg = ImageMaster.MAP_NODE_EVENT;
        mapImgOutline = ImageMaster.MAP_NODE_EVENT_OUTLINE;


    }


    @Override
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.endTurnButton.hide();
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        openCustomScreen(ArtifactSelectScreen.Enum.AMBRY_SCREEN, true);

    }
}
