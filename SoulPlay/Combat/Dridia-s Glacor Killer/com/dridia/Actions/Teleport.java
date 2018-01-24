package com.dridia.Actions;

import com.dridia.Utils.Methods;
import com.dridia.Utils.SleepCondition;
import xobot.script.methods.Packets;
import xobot.script.methods.Players;
import xobot.script.methods.Widgets;
import xobot.script.util.Time;

/**
 * Created by Dridia on 2018-01-15.
 */
public class Teleport {
    public static void teleportHome() {
        Packets.sendAction(315, 449, 3, 1195, 0, 1);
        Methods.conditionalSleep(new SleepCondition() {
            @Override
            public boolean isValid() {
                return Players.getMyPlayer().getAnimation() != -1;
            }
        }, 3000);
        Methods.conditionalSleep(new SleepCondition() {
            @Override
            public boolean isValid() {
                return Players.getMyPlayer().getAnimation() == -1;
            }
        }, 5000);
    }
    public static void teleportGlacorsLair(){
        Packets.sendAction(315, 799, 504, 7455, 0, 1);
        Methods.conditionalSleep(new SleepCondition() {
            @Override
            public boolean isValid() {
                return Widgets.getBackDialogId() == 2492;
            }
        }, 2500);
        if(Widgets.getBackDialogId() == 2492) {
            Packets.sendAction(315, 840, 326, 2498, 0, 1);
            Time.sleep(650, 900);
        }
        if(Widgets.getBackDialogId() == 2492) {
            Packets.sendAction(315, 715, 321, 2495, 0, 1);
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Players.getMyPlayer().getAnimation() != -1;
                }
            }, 2500);
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Players.getMyPlayer().getAnimation() == -1;
                }
            }, 4500);
        }
    }
}
