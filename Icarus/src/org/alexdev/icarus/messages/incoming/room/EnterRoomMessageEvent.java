package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.dao.mysql.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.settings.RoomState;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.RoomOwnerRightsComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericNoAnswerDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomEnterErrorMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class EnterRoomMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        Room room = RoomDao.getRoom(request.readInt(), true);

        if (room == null) {
            return;
        }
        
        room.loadRoom(player, request.readString());
    }

}
