package com.dridia.Action;

import com.dridia.Utils.Methods;
import com.dridia.Utils.SleepCondition;
import com.dridia.Variables;
import xobot.script.methods.*;
import xobot.script.methods.tabs.Inventory;
import xobot.script.util.Random;
import xobot.script.util.Time;
import xobot.script.wrappers.Tile;
import xobot.script.wrappers.interactive.GameObject;
import xobot.script.wrappers.interactive.NPC;

/**
 * Created by marcus on 2018-09-27.
 */
public class TaiBwoWannaiTasks {

    public static void CollectTradingSticks() {
        final NPC murcaily = NPCs.getNearest(2529);
        if(murcaily != null){
            murcaily.interact("Talk-to");
            Methods.conditionalSleep(() -> Widgets.getBackDialogId() == 2480, 4000);

            Packets.sendAction(315, 0, -1, 2484);
            Methods.conditionalSleep(() -> Widgets.getBackDialogId() == 968, 3000);

            Packets.sendAction(679, 0, 24, 972);
            Methods.conditionalSleep(() -> Widgets.getBackDialogId() == 4893, 3000);

            Variables.totalTSEarned = Variables.totalTSEarned + (Settings.get(353) * 2);

            Packets.sendAction(679, 0, 17, 4899);

        }else {
            if(Variables.murIsReachable.isReachable()){
                Variables.murIsReachable.walk();
                Methods.conditionalSleep(() -> Calculations.distanceTo(Variables.murIsReachable) < 2, 12000);
            }else{
                new Tile(2792, 3080).walk();
                Time.sleep(3000);
            }
        }


    }

    public static void repairFence() {
        GameObject unRepairedFence = GameObjects.getNearest(9025);
        final Tile repairingFenceTile = unRepairedFence.getLocation();
        if(unRepairedFence != null){
            unRepairedFence.interact("Repair");
            Methods.conditionalSleep(() -> GameObjects.getAllAt(repairingFenceTile)[0].getId() == 9029 || !Inventory.Contains(Variables.Vines), 120000);
            if(!Inventory.Contains(Variables.Vines)){
                Variables.prioritiseRepairFence = false;
            }
        }
    }


    /** TODO: Some solution to the problem where a players animation goes to -1 even when chopping occurs.*/
    public static void chopVines(int[] jungle) {
        final GameObject[] allAvailableJungle = GameObjects.getAll(jungle);

        if (allAvailableJungle.length > 0){
            for(int i = 0; i < allAvailableJungle.length; i++) {
                if (Variables.JungleArea.contains(allAvailableJungle[i].getLocation()) && allAvailableJungle[i].getDistance() < 35) {
                    allAvailableJungle[i].interact("Hack");

                    //When we've interacted with the Jungle, we wait until we start chopping.
                    Methods.conditionalSleep(() -> Players.getMyPlayer().getAnimation() != -1, Random.nextInt(4000, 6000));
                    i = allAvailableJungle.length;
                } else {
                    Walking.walkTo(new Tile(2795, 3078));
                    Time.sleep(400);
                }
            }
        }else{
            Teleport.TaiBwoWannai();
        }
    }

    public static boolean hasTSToCollect() {
        int tradingSticks = Settings.get(535);
        return tradingSticks >= 400;
    }

}
