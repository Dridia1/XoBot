package com.dridia;


import com.dridia.Utils.Methods;
import xobot.script.methods.*;
import xobot.script.methods.tabs.Inventory;
import xobot.script.wrappers.Area;
import xobot.script.wrappers.Tile;
import xobot.script.wrappers.interactive.GameObject;

/**
 * Created by marcus on 2018-09-30.
 */
public class Chop {

    public static Area cutting_area = new Area(new Tile(2817, 3088), new Tile(2829, 3078));
    public static int treeID = 9034;

    public static boolean canChop(){
        GameObject trees = GameObjects.getNearest(9034);
        if(isAtChopArea() && trees.isReachable()){
            return true;
        }
        return false;
    }

    public static boolean shouldChop(){
        if(!Inventory.isFull() && Hatchet.gotAxe()) {
            return true;
        }
        return false;
    }

    public static boolean isAtChopArea(){
        if(cutting_area.contains(Players.getMyPlayer().getLocation())){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isOutsideChopArea(){
        if(cutting_area.getNearestTile(Players.getMyPlayer().getLocation()).getDistance() <= 20){
            return true;
        }else{
            return false;
        }
    }

    public static void enterChopArea(){
        GameObject door = GameObjects.getNearest(9038, 9039);
        if(door != null){
            door.interact("Open");
            Methods.conditionalSleep(() -> Widgets.getBackDialogId() == 4893, 4500);

            Packets.sendAction(679, 0, 252, 4899);
            Methods.conditionalSleep(() -> Widgets.getBackDialogId() == 2459, 2000);

            Packets.sendAction(315, 0, -1, 2461);
            Methods.conditionalSleep(Chop::isAtChopArea, 2000);
        }
    }

    public static void handleChop(){
        GameObject tree = GameObjects.getNearest(treeID);
        if(tree != null){
            tree.interact("Chop down");
            Methods.conditionalSleep(() -> Players.getMyPlayer().getAnimation() == 2846, 4000);
        }

    }

}
