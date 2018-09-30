package com.dridia;

import com.dridia.Utils.Methods;
import xobot.script.methods.Bank;
import xobot.script.methods.GameObjects;
import xobot.script.methods.Widgets;
import xobot.script.methods.tabs.Inventory;
import xobot.script.wrappers.interactive.GameObject;
import xobot.script.wrappers.interactive.Item;

/**
 * Created by marcus on 2018-09-30.
 */
public class Banking {

    public static int bankID = 26972;
    public static int tsID = 6306;
    public static int logID = 6332;

    public static boolean canBank(){
        GameObject bank = GameObjects.getNearest(bankID);
        return bank != null;
    }

    public static boolean shouldBank(){
        if(Inventory.isFull() || !Inventory.Contains(tsID) && !Chop.isAtChopArea()){
            return true;
        }
        return false;
    }

    public static void handleBank(){
        if(canBank()){
            GameObject bank = GameObjects.getNearest(bankID);
            bank.interact("Use");
            Methods.conditionalSleep(() -> Widgets.getOpenInterface() == 5292, 7000);

            if(Inventory.Contains(logID)){
                Bank.deposit(logID, 28);
            }

            if(Inventory.getItem(tsID) == null){
                //Withdraw 100 tradingSticks
                Item tSticks = Bank.getItem(tsID);
                if(tSticks != null) {
                    Bank.withdraw(tsID, 100);
                    Methods.conditionalSleep(() -> Inventory.Contains(tsID), 2000);
                }else{
                    //System.out.println("Can't find any trading sticks inside your bank.. Consider switching tabs");
                }
            }
        }else{
            Teleportation.teleportHome();
        }
    }
}
