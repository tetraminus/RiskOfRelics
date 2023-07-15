package riskOfRelics.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import riskOfRelics.events.ArtifactSelectEvent;

public class AmbrySelectRoom extends AbstractRoom {

    private EventRoom fakeRoom;

    public AmbrySelectRoom() {
        super();
        this.phase = RoomPhase.EVENT;
        this.mapSymbol = "?";

        this.mapImg = ImageMaster.MAP_NODE_EVENT;// 14
        this.mapImgOutline = ImageMaster.MAP_NODE_EVENT_OUTLINE;// 15

        fakeRoom = new EventRoom();
    }

    @Override
    public void onPlayerEntry()
    {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        event = fakeRoom.event = new ArtifactSelectEvent();
        fakeRoom.event.onEnterRoom();
    }

    @Override
    public AbstractCard.CardRarity getCardRarity(int roll)
    {
        return fakeRoom.getCardRarity(roll);
    }

    @Override
    public void update()
    {
        fakeRoom.update();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        fakeRoom.render(sb);
        fakeRoom.renderEventTexts(sb);
    }

    @Override
    public void renderAboveTopPanel(SpriteBatch sb)
    {
        fakeRoom.renderAboveTopPanel(sb);
    }
}
