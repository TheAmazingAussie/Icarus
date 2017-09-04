package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class WallOptionsMessageComposer extends MessageComposer {

    private boolean hideWall;
    private int wallThickness;
    private int floorThickness;

    public WallOptionsMessageComposer(boolean hideWall, int wallThickness, int floorThickness) {
        this.hideWall = hideWall;
        this.wallThickness = wallThickness;
        this.floorThickness = floorThickness;
    }

    @Override
    public void write() {
        response.init(Outgoing.WallOptionsMessageComposer);
        response.writeBool(this.hideWall);
        response.writeInt(this.wallThickness);
        response.writeInt(this.floorThickness);
    }
}
