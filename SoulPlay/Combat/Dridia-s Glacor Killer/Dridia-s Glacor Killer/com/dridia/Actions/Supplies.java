package com.dridia.Actions;

import com.dridia.Data.Variables;
import com.dridia.Utils.Methods;
import com.dridia.Utils.SleepCondition;
import xobot.script.methods.tabs.Inventory;
import xobot.script.methods.tabs.Prayer;
import xobot.script.methods.tabs.Skills;
import xobot.script.wrappers.interactive.Item;

/**
 * Created by Dridia on 2018-01-15.
 */
public class Supplies {

    public static String shouldPotUp(){
        if(Skills.ATTACK.getCurrentLevel() <= Variables.SUPER_ATTACK_USEAT && Variables.SUPER_ATTACK_AMOUNT > 0) {
            return "attack";
        }else if(Skills.STRENGTH.getCurrentLevel()  <= Variables.SUPER_STRENGTH_USEAT && Variables.SUPER_STRENGTH_AMOUNT > 0){
            return "strength";
        }else if(Skills.DEFENCE.getCurrentLevel()  <= Variables.SUPER_DEFENCE_USEAT && Variables.SUPER_DEFENCE_AMOUNT > 0){
            return "defence";
        }
        return "null";
    }

    public static void potUp(String s){
        if(s.equals("attack")) {
            for (int i = 0; i < 4; i++){
                Item attackPot = Inventory.getItem(Variables.SUPER_ATTACK_ID[i]);
                if(attackPot != null){
                    attackPot.interact("Drink");
                    break;
                }
            }

        }else if(s.equals("strength")){
            for (int i = 0; i < 4; i++){
                Item strengthPot = Inventory.getItem(Variables.SUPER_STRENGTH_ID[i]);
                if(strengthPot != null){
                    strengthPot.interact("Drink");
                    break;
                }
            }

        }else if(s.equals("defence")){
            for (int i = 0; i < 4; i++){
                Item defencePot = Inventory.getItem(Variables.SUPER_DEFENCE_ID[i]);
                if(defencePot != null){
                    defencePot.interact("Drink");
                    break;
                }
            }
        }
    }

    public static void usePot(){
        //Find the most used prayerPot
        int i;
        for (i = 6; i >= 0; i-=2){
            Item prayerPot = Inventory.getItem(Variables.SUPER_RESTORE_ID + i);
            if(prayerPot != null){
                prayerPot.interact("Drink");
                break;
            }
        }
    }

    public static boolean prayerLow(){
        int prayerPoint = Prayer.getRemainingPoints();
        if(prayerPoint < 30){
            return true;
        }
        return false;
    }


    public static boolean shouldEat(){
        if(Skills.CONSTITUTION.getCurrentLevel()  > 0) {
            if (Skills.CONSTITUTION.getCurrentLevel()  <= Variables.EAT_AT_HP) {
                return true;
            }
        }
        return false;
    }

    public static boolean gotFood(){
        int foodCount = Inventory.getCount(Variables.FOOD_ID);
        if(foodCount < 1){
            return false;
        }
        return true;
    }

    public static void eatFood() {
        Item food = Inventory.getItem(Variables.FOOD_ID);
        if (food != null) {
            final int hp = Skills.CONSTITUTION.getCurrentLevel() ;
            food.interact("Eat");
            //829 animation for eating food
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Skills.CONSTITUTION.getCurrentLevel()  > hp;
                }
            }, 1700);

        }
    }
    public static void restorePrayer() {
        //Find the most used prayerPot
        int i;
        for (i = 6; i >= 0; i-=2){
            Item prayerPot = Inventory.getItem(Variables.SUPER_RESTORE_ID + i);
            if(prayerPot != null){
                prayerPot.interact("Drink");
                break;
            }
        }

    }

    public static boolean shouldRefill() {
        if(!Inventory.Contains(Variables.SUPER_RESTORE_ID) ||
           !Inventory.Contains(Variables.SUPER_ATTACK_ID)  ||
           !Inventory.Contains(Variables.SUPER_STRENGTH_ID)||
           !Inventory.Contains(Variables.SUPER_DEFENCE_ID) ||
           !Inventory.Contains(Variables.FOOD_ID)){

            return true;
        }
        return false;
    }

    public static void doRefill(){
        if(Banking.canBank()){
            Banking.doBank();
        }else{
            Teleport.teleportHome();
        }
    }
}
