package com.dridia;

import com.dridia.Utils.Methods;
import xobot.script.methods.Packets;
import xobot.script.methods.Players;
import xobot.script.methods.Widgets;

/**
 * Created by marcus on 2018-09-30.
 */
public class Teleportation {

    public static void teleportHome(){
        Packets.sendAction(315, 0, -1, 30000);
        Methods.conditionalSleep(() -> Players.getMyPlayer().getAnimation() != -1, 2000);
        Methods.conditionalSleep(() -> Players.getMyPlayer().getAnimation() == -1, 2000);
    }

    public static void teleportTaiBwoWannai(){
        Packets.sendAction(315, 0, -1, 30008);
        Methods.conditionalSleep(() -> Widgets.getOpenInterface() == 25411, 2000);

        Packets.sendAction(315, 0, -1, 26506);

        Methods.conditionalSleep(() -> Players.getMyPlayer().getAnimation() != -1, 2000);
        Methods.conditionalSleep(() -> Players.getMyPlayer().getAnimation() == 1, 2000);
    }
}

