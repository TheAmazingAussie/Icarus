package org.alexdev.icarus.game.furniture.interactions;

import org.alexdev.icarus.game.furniture.interactions.types.AdjustableHeightInteractor;
import org.alexdev.icarus.game.furniture.interactions.types.BedInteractor;
import org.alexdev.icarus.game.furniture.interactions.types.DefaultInteractor;
import org.alexdev.icarus.game.furniture.interactions.types.DiceInteractor;
import org.alexdev.icarus.game.furniture.interactions.types.DimmerInteractor;
import org.alexdev.icarus.game.furniture.interactions.types.GateInteractor;
import org.alexdev.icarus.game.furniture.interactions.types.MannequinInteractor;
import org.alexdev.icarus.game.furniture.interactions.types.OneWayGateInteractor;
import org.alexdev.icarus.game.furniture.interactions.types.TeleportInteractor;
import org.alexdev.icarus.game.furniture.interactions.types.VendingInteractor;
import org.alexdev.icarus.log.Log;

public enum InteractionType {

    DEFAULT(new DefaultInteractor()),
    GATE(new GateInteractor()),
    POSTIT(null),
    ROOMEFFECT(null),
    DIMMER(new DimmerInteractor()),
    TROPHY(null),
    BED(new BedInteractor()),
    SCOREBOARD(null),
    VENDINGMACHINE(new VendingInteractor()),
    ADJUSTABLEHEIGHT(new AdjustableHeightInteractor()),
    ALERT(null),
    ONEWAYGATE(new OneWayGateInteractor()),
    LOVESHUFFLER(null),
    HABBOWHEEL(null),
    DICE(new DiceInteractor()),
    BOTTLE(null),
    TELEPORT(new TeleportInteractor()),
    RENTALS(null),
    PET(null),
    ROLLER(null),
    FOOTBALL(null),
    BB_PLATE(null),
    BB_GREEN_GATE(null),
    BB_BLUE_GATE(null),
    BB_YELLOW_GATE(null),
    BB_RED_GATE(null),
    BB_COUNTER(null),
    SOUNDMACHINE(null),
    PUZZLEBOX(null),
    FIREWORKS(null),
    BOX(null),
    CINE_TILE(null),
    FRZ_TILE(null),
    FRZ_COUNTER(null),
    FRZ_BOX(null),
    ES_GATE_R(null),
    ES_GATE_Y(null),
    ES_GATE_B(null),
    ES_GATE_G(null),
    FOOTBALLGOALGREEN(null),
    FOOTBALLGOALYELLOW(null),
    FOOTBALLGOALRED(null),
    FOOTBALLGOALBLUE(null),
    FOOTBALLCOUNTERGREEN(null),
    FOOTBALLCOUNTERYELLOW(null),
    FOOTBALLCOUNTERBLUE(null),
    FOOTBALLCOUNTERED(null),
    FBGATE(null),
    ICESKATES(null),
    NORMALSKATES(null),
    LOWPOOL(null),
    HALOWEENPOOL(null),
    POOL(null),
    SWIM(null),
    EASTER11_GRASSPATCH(null),
    BB_REDCOUNTER(null),
    BB_GREENCOUNTER(null),
    BB_YELLOWCOUNTER(null),
    BB_BLUECOUNTER(null),
    BB_TELEPORT(null),
    MUSICDISK(null),
    JUKEBOX(null),
    BANZAIPUCK(null),
    BANZAIPYRAMId(null),
    FREEZEGREENCOUNTER(null),
    FREEZEYELLOWCOUNTER(null),
    FREEZEBLUECOUNTER(null),
    FREEZEREDCOUNTER(null),
    FREEZEEXIT(null),
    TRIGGERTIMER(null),
    TRIGGERROOMENTER(null),
    TRIGGERGAMEEND(null),
    TRIGGERGAMESTART(null),
    TRIGGERREPEATER(null),
    TRIGGERONUSERSAY(null),
    TRIGGERSCOREACHIEVED(null),
    TRIGGERSTATECHANGED(null),
    TRIGGERWALKONFURNI(null),
    TRIGGERWALKOFFFURNI(null),
    ACTIONGIVESCORE(null),
    ACTIONPOSRESET(null),
    ACTIONMOVEROTATE(null),
    ACTIONRESETTIMER(null),
    ACTIONTELEPORTTO(null),
    ACTIONSHOWMESSAGE(null),
    ACTIONTOGGLESTATE(null),
    CONDITIONFURNISHAVEUSERS(null),
    CONDITIONSTATEPOS(null),
    CONDITIONTIMELESSTHAN(null),
    CONDITIONTIMEMORETHAN(null),
    CONDITIONTRIGGERONFURNI(null),
    ARROWPLATE(null),
    PREASSUREPLATE(null),
    RINGPLATE(null),
    COLORTILE(null),
    COLORWHEEL(null),
    FLOORSWITCH1(null),
    FLOORSWITCH2(null),
    FIREGATE(null),
    GLASSFOOR(null),
    SPECIALRANDOM(null),
    SPECIALUNSEEN(null),
    WIRE(null),
    WIRECENTER(null),
    WIRECORNER(null),
    WIRESPLITTER(null),
    WIRESTANDARD(null),
    GIFT(null),
    MANNEQUIN(new MannequinInteractor()),
    GLD_GATE(null),
    BG_COLORBACKGROUND(null),
    ADS_FURNITURE(null),
    GROUPITEM(null),
    BADGE_DISPLAY(null),
    YOUTUBETV(null),
    SNOWBOARD(null),
    DOG(null),
    CAT(null),
    CROCO(null),
    TERRIER(null),
    BEAR(null),
    PIG(null),
    LION(null),
    RHINO(null),
    SPIdER(null),
    TURTLE(null),
    CHICKEN(null),
    FROG(null),
    DRAGON(null),
    MONSTER(null),
    MONKEY(null),
    HORSE(null),
    MONSTERPLANT(null),
    BUNNYEASTER(null),
    BUNNYEVIL(null),
    BUNNYDEPRESSED(null),
    BUNNYLOVE(null),
    PIGEONGOOD(null),
    PIGEONEVIL(null),
    DEMONMONKEY(null),
    BEARBABY(null),
    TERRIERBABY(null),
    GNOME(null),
    CAMERA_PHOTO(null);

    private Interaction interaction;
    
    InteractionType(Interaction interaction) {
        this.interaction = interaction;
    }
    
    public Interaction getHandler() {
        return interaction;
    }
    
    public static InteractionType getType(String databaseType) {
        try {
            
            /*if (databaseType.contains("gate")) {
                Log.info("DB TYPE: " + databaseType + " value: " + InteractionType.valueOf(databaseType.toUpperCase().replace("_", "")));
            }*/
            
            return InteractionType.valueOf(databaseType.toUpperCase().replace("_", ""));
        } catch (Exception e) {
            return InteractionType.DEFAULT;
        }
    }
}
