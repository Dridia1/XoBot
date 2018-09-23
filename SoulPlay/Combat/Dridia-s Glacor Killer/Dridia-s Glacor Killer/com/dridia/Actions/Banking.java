package com.dridia.Actions;

import com.dridia.Data.Variables;
import com.dridia.Utils.Methods;
import com.dridia.Utils.SleepCondition;
import xobot.script.methods.Bank;
import xobot.script.methods.GameObjects;
import xobot.script.methods.tabs.Inventory;
import xobot.script.wrappers.interactive.GameObject;

/**
 * Created by Dridia on 2018-01-15.
 */
public class Banking {
    public static boolean canBank(){
        GameObject bank = GameObjects.getNearest(Variables.BANK_ID);
        return bank != null && bank.isReachable();
    }

    public static void doBank(){
        GameObject bank = GameObjects.getNearest(Variables.BANK_ID);
        if (bank != null && !Bank.isOpen()) {
            bank.interact("Bank");
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Bank.isOpen();
                }
            }, 5000);
        }
        System.out.println("Sleep has ended");
        if (Bank.isOpen()) {
            System.out.println("Bank is open!");
            Bank.depositAllExcept(Variables.DO_NOT_DEPOSIT_ID);

            Banking.withdrawNeededSupplies();
        }
    }

    private static void withdrawNeededSupplies() {
        int atkAmount = Inventory.getCount(Variables.SUPER_ATTACK_ID[3]);
        int strAmount = Inventory.getCount(Variables.SUPER_STRENGTH_ID[3]);
        int defAmount = Inventory.getCount(Variables.SUPER_DEFENCE_ID[3]);
        int resAmount = Inventory.getCount(Variables.SUPER_RESTORE_ID);
        int foodAmount = Inventory.getCount(Variables.FOOD_ID);

        if(atkAmount < Variables.SUPER_ATTACK_AMOUNT) {
            Bank.withdraw(Variables.SUPER_ATTACK_ID[3], Variables.SUPER_ATTACK_AMOUNT - atkAmount);
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Inventory.getCount(Variables.SUPER_ATTACK_ID[3]) == Variables.SUPER_ATTACK_AMOUNT;
                }
            }, 2000);
            System.out.println("sprAttack has ended");
        }
        if(strAmount < Variables.SUPER_STRENGTH_AMOUNT) {
            Bank.withdraw(Variables.SUPER_STRENGTH_ID[3], Variables.SUPER_STRENGTH_AMOUNT - strAmount);
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Inventory.getCount(Variables.SUPER_STRENGTH_ID[3]) == Variables.SUPER_STRENGTH_AMOUNT;
                }
            }, 2000);
        }

        if(defAmount < Variables.SUPER_DEFENCE_AMOUNT) {
            Bank.withdraw(Variables.SUPER_DEFENCE_ID[3], Variables.SUPER_DEFENCE_AMOUNT - defAmount);
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Inventory.getCount(Variables.SUPER_DEFENCE_ID[3]) == Variables.SUPER_DEFENCE_AMOUNT;
                }
            }, 2000);
        }

        if(resAmount < Variables.SUPER_RESTORE_AMOUNT) {
            Bank.withdraw(Variables.SUPER_RESTORE_ID, Variables.SUPER_RESTORE_AMOUNT - resAmount);
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Inventory.getCount(Variables.SUPER_RESTORE_ID) == Variables.SUPER_RESTORE_AMOUNT;
                }
            }, 2000);
        }

        if(foodAmount < Variables.FOOD_AMOUNT) {
            Bank.withdraw(Variables.FOOD_ID, Variables.FOOD_ID - foodAmount);
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Inventory.getCount(Variables.FOOD_ID) == Variables.FOOD_AMOUNT;
                }
            }, 2000);
        }
    }
}
