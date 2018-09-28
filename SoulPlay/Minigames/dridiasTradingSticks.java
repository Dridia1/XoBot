import com.dridia.Action.Banking;
import com.dridia.Action.Combat;
import com.dridia.Action.TaiBwoWannaiTasks;
import com.dridia.Variables;
import xobot.client.callback.listeners.MessageListener;
import xobot.client.callback.listeners.PaintListener;
import xobot.client.events.MessageEvent;
import xobot.script.ActiveScript;
import xobot.script.Manifest;
import xobot.script.methods.*;
import xobot.script.methods.tabs.Equipment;
import xobot.script.methods.tabs.Inventory;
import xobot.script.methods.tabs.Skills;
import xobot.script.util.Random;
import xobot.script.util.Time;
import xobot.script.util.Timer;
import xobot.script.wrappers.Area;
import xobot.script.wrappers.Tile;
import xobot.script.wrappers.interactive.GameObject;
import xobot.script.wrappers.interactive.Item;
import xobot.script.wrappers.interactive.NPC;

import com.dridia.Utils.Methods;
import com.dridia.Utils.SleepCondition;
import com.dridia.GUI.GUI;
import com.dridia.Status;


import java.awt.*;

@Manifest(authors = { "Dridia" }, name = "Dridia's Tai Bwo Wannai", version = 1.0, description = "Will get you your trading sticks")
public class dridiasTradingSticks extends ActiveScript implements PaintListener, MessageListener {

    /**
     * Features:
     * Will do the Tai Bowanni Minigame to obtain trading sticks
     * Will Withdraw Trading Sticks from **Mur** when at 100% Favour
     * Will Bank when low health, eat food and drink super restore.
     *
     * Improvement:
     * Weapon Switch. Specify a weapon to switch when killing mobs.
     * */

    /*
    * TODO:
    * Increase the area in which the vines are located. (Done)
    * Move Combat check to be more prioritised. ( Done )
    * Increase combat check by also looking at mobs interacting index. (Done)
    * Do something when heath is too low. (Done)
    *
    * Add paint (Done)
    *  -Time Elapsed
    *  -Status
    *  -Total TS Obtained (Including the ones needed to be collected) (Not Done)
    *
    * Add Setup GUI (Done)
    *  -Food ID
    *  -Eat at HP
    *
    * Weapon flick
    *
    * Make the script more bulletproof.
    * */


    private Timer timeElapsed;
    private String state;
    private int CurrentFavourPercent;
    private int StartTradingSticksAmount; // Holds the amount of TradingSticks when starting the script.

    @Override
    public boolean onStart() {

        GUI.setupAndShowGUI();

        Variables.teleportBelowHealth = GUI.getEatAtHP();
        Variables.foodId = GUI.getFoodId();

        state = "setup";
        timeElapsed = new Timer(System.currentTimeMillis());

        StartTradingSticksAmount = Settings.get(535) * 2;
        CurrentFavourPercent = 0;

        return true;
    }

    @Override
    public void onStop() {

    }

    @Override
    public int loop() {

        if((Settings.get(535) * 2) != CurrentFavourPercent){
            CurrentFavourPercent = (Settings.get(535) * 2);
            //System.out.println("Currently " + CurrentFavourPercent + " Trading Sticks at Murr");
            //System.out.println("I've earned " + (CurrentFavourPercent - StartTradingSticksAmount));

        }

        switch (Status.updateState()) {

            case CHOP_VINES:
                state = "Cutting vines";
                TaiBwoWannaiTasks.chopVines(Variables.Jungle);
                break;
            case KILL_MOB:
                state = "Killing mobs";
                Combat.killMobInArea(Variables.JungleArea);
                break;
            case REPAIR_FENCE:
                state = "Repair fence";
                TaiBwoWannaiTasks.repairFence();
                break;
            case LOW_HEALTH:
                state = "Low health, banking";
                Banking.bankAndEat();
                break;
            case COLLECT_TS:
                state = "Collecting Trading Sticks";
                TaiBwoWannaiTasks.CollectTradingSticks();
                break;
            case WAIT:
                //state = "Wait?!";
                //Currently chopping jungle. Wait.
                break;
            case LOST:
                state = "Lost...";
                //Handle Lost
                break;
        }

        return 80;
    }

    @Override
    public void repaint(Graphics g) {
        Graphics2D g1 = (Graphics2D) g;

        g1.setColor(Color.CYAN);
        g1.setFont(new Font("Verdana", Font.BOLD, 12));
        g1.drawString("Dridia's Tai Bwo Wannai v1.0", 10, 30);
        g1.drawString("State: " + state, 10, 50);

        g1.drawString("Time run: " + timeElapsed.toElapsedString(), 10, 70);
        //g1.drawString("Trading Sticks Earned: " + (CurrentFavourPercent - StartTradingSticksAmount), 10, 90);

    }

    @Override
    public void MessageRecieved(MessageEvent messageEvent) {

    }



}
