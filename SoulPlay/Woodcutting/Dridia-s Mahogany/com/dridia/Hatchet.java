package com.dridia;

import xobot.script.methods.tabs.Equipment;
import xobot.script.methods.tabs.Inventory;

/**
 * Created by marcus on 2018-09-30.
 */
public class Hatchet {

    public static int[] allHatchetIDs = {1351, 1349, 1353, 1355, 1357, 1359, 6739};

    public static boolean gotAxe(){
        for(int hatchet : allHatchetIDs){
            if(Inventory.Contains(hatchet)){
                return true;
            }else if(Equipment.containsOneOf(hatchet)){
                return true;
            }
        }
        System.out.println("GOT NO AXE!!!!!");
        return false;
    }
}

