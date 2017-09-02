package org.alexdev.icarus.game.navigator.populator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.alexdev.icarus.game.navigator.NavigatorRoomPopulator;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.settings.RoomType;

public class PromotedPopulator extends NavigatorRoomPopulator {

    @Override
    public List<Room> generateListing(boolean limit, Player player) {

        Map<Integer, Room> loadedRooms = RoomManager.getPromotedRooms();
        
        List<Room> activeRooms = loadedRooms.values().stream().filter(r -> r.getData().getRoomType() == RoomType.PRIVATE && r.getPromotion() != null).collect(Collectors.toList());
        
        activeRooms.sort((room1, room2)
        ->room1.getPromotion().getPromotionMinutesLeft().get()
        - room2.getPromotion().getPromotionMinutesLeft().get());

        return activeRooms;
    }

}