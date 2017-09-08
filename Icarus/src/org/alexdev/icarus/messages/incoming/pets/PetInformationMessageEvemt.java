package org.alexdev.icarus.messages.incoming.pets;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.pets.PetInformationComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PetInformationMessageEvemt implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
       
        Entity entity = player.getRoom().getEntityById(reader.readInt());
        
        if (entity == null) {
            return;
        }
        
        if (entity.getType() != EntityType.PET) {
            return;
        }
        
        Pet pet = (Pet) entity;
        
        player.send(new PetInformationComposer(pet));
    }

}
