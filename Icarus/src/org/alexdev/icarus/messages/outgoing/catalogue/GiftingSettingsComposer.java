package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class GiftingSettingsComposer extends MessageComposer {

    @Override
    public void write() {

        response.init(Outgoing.GiftingSettingsComposer);
        response.writeBool(true);
        response.writeInt(1);
        response.writeInt(10);
        
        for (int i = 3372; i < 3382;) {
            response.writeInt(i);
            i++;
        }
        
        response.writeInt(7);
        response.writeInt(0);
        response.writeInt(1);
        response.writeInt(2);
        response.writeInt(3);
        response.writeInt(4);
        response.writeInt(5);
        response.writeInt(6);
        response.writeInt(11);
        response.writeInt(0);
        response.writeInt(1);
        response.writeInt(2);
        response.writeInt(3);
        response.writeInt(4);
        response.writeInt(5);
        response.writeInt(6);
        response.writeInt(7);
        response.writeInt(8);
        response.writeInt(9);
        response.writeInt(10);
        response.writeInt(7);
        
        for (int i = 187; i < 194;) {
            response.writeInt(i);
            i++;
        }
    }
}
