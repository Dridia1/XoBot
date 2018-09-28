package com.dridia.Action;

import com.dridia.Utils.Methods;
import com.dridia.Variables;
import xobot.script.methods.Bank;
import xobot.script.methods.GameObjects;
import xobot.script.methods.Packets;
import xobot.script.methods.Players;
import xobot.script.methods.tabs.Inventory;
import xobot.script.methods.tabs.Skills;
import xobot.script.util.Time;
import xobot.script.wrappers.interactive.GameObject;
import xobot.script.wrappers.interactive.Item;

/**
 * Created by marcus on 2018-09-27.
 */
public class Banking {

    public static void bankAndEat() {

        GameObject bank = GameObjects.getNearest(26972);

        if(bank != null){

            int withdrawVineAmount;
            bank.interact("Use");

            Methods.conditionalSleep(Bank::isOpen, 8000);

            //Deposit vines to make room for food + super restore.
            if(Inventory.getFreeSlots() < 5){
                withdrawVineAmount = (5 - Inventory.getFreeSlots());
                Bank.deposit(6285, withdrawVineAmount);
                Time.sleep(1000);
            }

            // Withdraw lobster and leave blank space for a restore.
            Bank.withdraw(Variables.foodId, Inventory.getFreeSlots() - 1);
            Methods.conditionalSleep(() -> Inventory.Contains(Variables.foodId), 2000);

            //Withdraw restore
            Bank.withdraw(3024, 1);
            Methods.conditionalSleep(() -> Inventory.Contains(3024), 2000);

            // Close the bank
            Packets.sendAction(200, 0, 249, 5384);
            Methods.conditionalSleep(Bank::isOpen, 1000);



            int iteration = 0;
            //Pot up until stats are above 5% of the real lvl
            while(Skills.ATTACK.getCurrentLevel() / Skills.ATTACK.getRealLevel() < 0.95){
                Item i = Inventory.getItem(3024 + iteration);
                i.interact("Drink");
                Methods.conditionalSleep(() -> Players.getMyPlayer().getAnimation() != -1, 2000);
                Methods.conditionalSleep(() -> Players.getMyPlayer().getAnimation() == -1, 2000);
                iteration += 2;
            }

            //Eat desired food until hp is greater than 94
            while(Skills.CONSTITUTION.getCurrentLevel() < 95){
                Item is = Inventory.getItem(Variables.foodId);
                is.interact("Eat");
                Methods.conditionalSleep(() -> Players.getMyPlayer().getAnimation() != -1, 2000);
                Methods.conditionalSleep(() -> Players.getMyPlayer().getAnimation() == -1, 2000);
            }

            bank.interact("Use");
            Methods.conditionalSleep(Bank::isOpen, 2000);

            Bank.depositAllExcept(6306, 6285); //Deposit all items except tradingsticks and vines.
            Time.sleep(1000);

            Item vineAtBank = Bank.getItem(6285);
            if(vineAtBank != null) {
                Bank.withdraw(6285, vineAtBank.getStack());
                Time.sleep(1250);
            }

            //Close bank
            Packets.sendAction(200, 0, 249, 5384);
            Methods.conditionalSleep(() -> !Bank.isOpen(), 1750);
        }else{
            Teleport.home();
        }

    }

}
