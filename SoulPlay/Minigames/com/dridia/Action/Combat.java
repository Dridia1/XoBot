package com.dridia.Action;

import com.dridia.Utils.Methods;
import xobot.script.methods.NPCs;
import xobot.script.methods.Players;
import xobot.script.wrappers.Area;
import xobot.script.wrappers.interactive.NPC;

import java.util.LinkedList;

/**
 * Created by marcus on 2018-09-27.
 */
public class Combat {

    public static void killMobInArea(Area JungleArea) {
        if(Players.getMyPlayer().getInteractingIndex() == -1) {
            NPC n[] = NPCs.getAll();
            if (n.length > 0) {
                for (int i = 0; i < n.length; i++) {
                    if(n[i].getInteractingIndex() != -1) {
                        if (JungleArea.contains(n[i].getLocation()) && (Players.getMyPlayer().getName().equals(n[i].getInteractingCharacter().def.getName()))) {
                            n[i].interact("Attack");
                            Methods.conditionalSleep(() -> Players.getMyPlayer().getInteractingCharacter() != null, 2000);
                            i = n.length;
                        }
                    }
                }
            }
        }
    }

    public static NPC MobInteractingInArea(Area jungleArea) {
        NPC closeNPCs[] = NPCs.getAll(); //Find all close NPCs

        //Loop through all NPCs inside of the area
        for(NPC n : closeNPCs){
            if(jungleArea.contains(n.getLocation())){
                if(n.getInteractingIndex() != -1) {
                    if (Players.getMyPlayer().getName().equals(n.getInteractingCharacter().def.getName())) {
                        return n;
                    }
                }
            }
        }
        return null;
    }

}
