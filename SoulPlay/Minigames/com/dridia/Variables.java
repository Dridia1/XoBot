package com.dridia;

import xobot.script.wrappers.Area;
import xobot.script.wrappers.Tile;

/**
 * Created by marcus on 2018-09-27.
 */
public class Variables {

    public static Tile murIsReachable = new Tile(2810, 3078); //A Tile near Murcaily
    public static int totalTSEarned = 0;

    public static int macheteID = 975;
    public static int[] Vines = {6285};
    public static int[] Jungle = {9020, 9021, 9022, 9023};

    public static boolean prioritiseRepairFence = false;
    public static int teleportBelowHealth;
    public static int foodId;

    public static Area JungleArea = new Area(new Tile(2769, 3074), new Tile(2781, 3092));

}
