package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.inventory.InventoryNotification;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PickupItemMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasRights(player.getEntityId()) && !player.getDetails().hasPermission("room_all_rights")) {
            return;
        }
        
        reader.readInt();
        Item item = room.getItemManager().getItem(reader.readInt());

        if (item == null) {
            return;
        }

        if (item.getDefinition().getType() == ItemType.FLOOR || item.getDefinition().getType() == ItemType.WALL) {

            room.getMapping().removeItem(item);
            
            player.getInventory().addItem(item, InventoryNotification.NONE);
            player.getInventory().updateItems();
        }
    }
}
