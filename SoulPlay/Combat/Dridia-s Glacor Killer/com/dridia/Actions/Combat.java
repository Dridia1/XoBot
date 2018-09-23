package com.dridia.Actions;

import com.dridia.Data.Variables;
import com.dridia.Utils.Methods;
import com.dridia.Utils.SleepCondition;
import xobot.script.methods.*;
import xobot.script.methods.tabs.Inventory;
import xobot.script.util.Random;
import xobot.script.util.Time;
import xobot.script.wrappers.Tile;
import xobot.script.wrappers.interactive.*;
import xobot.script.wrappers.interactive.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dridia on 2018-01-15.
 */
public class Combat {

    static List<GroundItem> itemsFound = new ArrayList<>();

    public static boolean prayerOn(){
        if(Settings.get(95) == 1 && !Variables.USE_PIETY){
            return true;
        }else if(Variables.USE_PIETY && Settings.get(95) == 1 && Settings.get(608) == 1){
            return true;
        }
        return false;
    }

    public static void turnOnPrayer(){
        if(Variables.USE_PIETY) {
            if (Settings.get(608) == 0) {//If != 0 it's already on
                Packets.sendAction(169, 0, 0, 18014, 411);
            }
        }
        if(Settings.get(95) == 0){
            Packets.sendAction(169, 0, 0, 5621, 0);
        }
    }

    public static void turnOffPrayer(){
        if(Variables.USE_PIETY) {
            if (Settings.get(608) == 1) {//If != 0 it's already on
                Packets.sendAction(169, 0, 0, 18014, 411);
            }
        }
        if(Settings.get(95) == 1){
            Packets.sendAction(169, 0, 0, 5621, 0);
        }
    }

    public static boolean canAttack(){
        if(Players.getMyPlayer().isInCombat()){
            Character fighting = Players.getMyPlayer().getInteractingCharacter();
            return fighting != null && fighting.isDead();
        }
        return Players.getMyPlayer().getInteractingIndex() == -1;
    }

    public static void Attack() {
        final NPC[] allGlacors = Methods.getAllSortedByDistance(1382);

        if(allGlacors.length > 0){
            for (int i = 0; i < allGlacors.length; i++) {
                if(!allGlacors[i].isInCombat()) {
                    //This glacor has a target, is it me? if yes, attack back otherwise, find another Glacor
                    //System.out.println("Already under attack...");
                    allGlacors[i].interact("Attack");
                    final int finalI = i;
                    Methods.conditionalSleep(new SleepCondition() {
                        @Override
                        public boolean isValid() {
                            return Players.getMyPlayer().isInCombat() || allGlacors[finalI].isInCombat(); // Should be when player is in combat? Currently, thid makes the bot click multiple times before player is at the mob
                        }
                    }, 2000);
                    i = allGlacors.length;
                }
            }
        }else{
            //No Glacors found, walk to the center tile +- 3 tiles X'n'Y. Check if tile is reachable. if not, pick a tile closer.
            int ABCval1 = new Random().nextInt(-3, 3);
            int ABCval2 = new Random().nextInt(-3, 3);

            final Tile t = new Tile(2659 + ABCval1, 3997 + ABCval2);
            if(t.isReachable()) {
                Walking.walkTo(t);
                Methods.conditionalSleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return Calculations.getdistanceBetween(Players.getMyPlayer().getLocation(), t) < 3 || Players.getMyPlayer().isInCombat();
                    }
                }, 5600);
            }else{
                Walking.walkTo(new Tile(2650, 4007));
            }
        }
    }

    public static boolean gotAggro() {
        NPC[] allDemons = NPCs.getAll(8349, 8351);
        for(NPC n : allDemons) {
            if(n != null && n.getInteractingCharacter() != null) {
                if(Players.getMyPlayer().getName().equals(n.getInteractingCharacter().def.getName())) {
                    //System.out.println("Got Aggro");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isAtLair(){
        Tile[] arr = Variables.GlacorsLair.getTileArray();
        for(Tile t : arr) {
            if(t.getX() == Players.getMyPlayer().getLocation().getX() && t.getY() == Players.getMyPlayer().getLocation().getY()){
                return true;
            }
        }
        return false;
    }

    public static boolean lootFound() {
        itemsFound.clear();
        GroundItem[] allItems = GroundItems.getAll();
        for(GroundItem i : allItems){
            if(i != null && Variables.DROP_LIST.contains(i.getItem().getID())){
                itemsFound.add(i);
            }
        }
        return itemsFound.size() > 0;
    }

    public static void pickUpLoot() {
        if (Inventory.isFull())
        {
            Item vial = Inventory.getItem(229);
            if (vial != null)
            {
                vial.interact("Drop");
                Time.sleep(500,666);
            }
            else
            {
                if(Supplies.gotFood()){
                    Supplies.eatFood();
                    Time.sleep(480,666);
                }
            }
        }
        if (!Inventory.isFull())
        {
            final int count = countInventory();
            Packets.sendAction(234, itemsFound.get(0).getItem().getID(), itemsFound.get(0).getX(), itemsFound.get(0).getY());
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return countInventory() > count;
                }
            }, 8000);
            if(itemsFound.get(0).getItem().getID() == 21790) {
                Variables.GLAIVEN++;
            }else if(itemsFound.get(0).getItem().getID() == 21793){
                Variables.RAGES++;
            }else if(itemsFound.get(0).getItem().getID() == 21787){
                Variables.STEADS++;
            }else if(itemsFound.get(0).getItem().getID() == 21776){
                Variables.SHARDS++;
            }
        }
    }
    public static int countInventory() {
        int count = 0;
        Item[] items = Inventory.getItems();

        for (Item item : items)
        {
            if (item != null)
            {
                count += item.getStack();
            }
        }

        return count;
    }
}
