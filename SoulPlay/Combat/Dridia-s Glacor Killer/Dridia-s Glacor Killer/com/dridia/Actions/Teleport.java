package com.dridia.Actions;

import com.dridia.Utils.Methods;
import com.dridia.Utils.SleepCondition;
import xobot.client.Interface;
import xobot.script.methods.Packets;
import xobot.script.methods.Players;
import xobot.script.methods.Widgets;
import xobot.script.util.Time;

/**
 * Created by Dridia on 2018-01-15.
 */
public class Teleport {
    public static void teleportHome() {
        Packets.sendAction(315, 449, 3, 1195);
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
        Packets.sendAction(315, 0, -1, 7455);
        Methods.conditionalSleep(new SleepCondition() {
            @Override
            public boolean isValid() {
                return Widgets.getBackDialogId() == 2492;
            }
        }, 2500);
        if(Widgets.getOpenInterface() == 25411) {
            Packets.sendAction(315, 840, 326, 26090);
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
        /*if(Widgets.getBackDialogId() == 2492) {
            Packets.sendAction(315, 715, 321, 2495);

        }*/
    }
}
