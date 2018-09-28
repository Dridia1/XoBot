package com.dridia;

import com.dridia.Action.Combat;
import com.dridia.Action.TaiBwoWannaiTasks;
import xobot.script.methods.Players;
import xobot.script.methods.tabs.Equipment;
import xobot.script.methods.tabs.Inventory;
import xobot.script.methods.tabs.Skills;
import xobot.script.util.Time;

/**
 * Created by marcus on 2018-09-27.
 */
public class Status{
    public enum State{
        CHOP_VINES, KILL_MOB, REPAIR_FENCE, LOW_HEALTH, COLLECT_TS, EQUIP_MACHETE, WAIT, LOST
    }

    public static State updateState(){
        if(Skills.CONSTITUTION.getCurrentLevel() < Variables.teleportBelowHealth){
            return State.LOW_HEALTH;
        }else if(TaiBwoWannaiTasks.hasTSToCollect()) {
            return State.COLLECT_TS;
        }else if (Players.getMyPlayer().isInCombat() || Combat.MobInteractingInArea(Variables.JungleArea) != null) {
            return State.KILL_MOB;
        }
        if(Inventory.isFull() && Inventory.Contains(Variables.Vines) || Variables.prioritiseRepairFence){
            Variables.prioritiseRepairFence = true;
            if(!Inventory.Contains(Variables.Vines)){
                Variables.prioritiseRepairFence = false;
            }
            return State.REPAIR_FENCE;
        }else{
            if(Equipment.containsOneOf(Variables.macheteID)) {
                if (Players.getMyPlayer().getAnimation() == -1 && !Variables.prioritiseRepairFence) {
                    Time.sleep(200);
                    if(Players.getMyPlayer().getAnimation() == -1) {
                        return State.CHOP_VINES;
                    }
                }
            }else{
                if (Inventory.Contains(Variables.macheteID)) {
                    //Wear machete
                    return State.EQUIP_MACHETE;
                } else {
                    //No Machete found. Please go to your bank and get some.
                    return State.LOST;
                }

            }
        }
        return State.WAIT;
    }
}

