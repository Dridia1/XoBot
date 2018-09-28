package com.dridia.Utils;

import xobot.script.methods.NPCs;
import xobot.script.util.Time;
import xobot.script.wrappers.interactive.NPC;

/**
 * Created by Dridia on 2018-01-16.
 */
public class Methods {

    private static NPC[] allMonsters;

    /*
	 * conditional sleep from Parabot
	 */
    public static boolean conditionalSleep(SleepCondition conn, int timeout) {
        long start = System.currentTimeMillis();
        while (!conn.isValid()) {
            if (start + timeout < System.currentTimeMillis()) {
                return false;
            }
            Time.sleep(50);
        }
        return true;
    }

    //Using Quicksort
    public static NPC[] getAllSortedByDistance(int monsterId){
        allMonsters = NPCs.getAll(monsterId);

        if(allMonsters.length > 1) {
            quickSort(0, allMonsters.length - 1);
        }

        return allMonsters;//CHANGE ME
    }

    private static void quickSort(int l, int h){
        int lowerIndex = l;
        int higherIndex = h;

        int pivot = allMonsters[l+(h-l)/2].getDistance(); // Get middle element distance.

        while (lowerIndex <= higherIndex) {

            while (allMonsters[lowerIndex].getDistance() < pivot) {
                lowerIndex++;
            }
            while (allMonsters[higherIndex].getDistance() > pivot) {
                higherIndex--;
            }
            if (lowerIndex <= higherIndex) {
                switchNumbers(lowerIndex, higherIndex);

                lowerIndex++;
                higherIndex--;
            }
        }

        if (l < higherIndex)
            quickSort(l, higherIndex);
        if (lowerIndex < h)
            quickSort(lowerIndex, h);

    }

    private static void switchNumbers(int i, int j) {
        NPC temp = allMonsters[i];
        allMonsters[i] = allMonsters[j];
        allMonsters[j] = temp;
    }


}
