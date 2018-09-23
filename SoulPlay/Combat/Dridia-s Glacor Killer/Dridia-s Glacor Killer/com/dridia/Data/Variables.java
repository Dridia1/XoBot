package com.dridia.Data;

import xobot.script.wrappers.Area;
import xobot.script.wrappers.Tile;
import java.util.List;

/**
 * Created by Dridia on 2018-01-15.
 */
public class Variables {

    /*
    * The meleeSwitch will be what the user enters. Weapon: Vesta longsword Armor: Vesta chainbody: Cape: FireCape
    *
    * The rangedSwitch will then know that instead of Firecape i should have my capeslota itemid entered and so on.
    * */

    public static boolean USE_PIETY;

    public static int SUPER_RESTORE_ID = 3024;
    public static int SUPER_RESTORE_AMOUNT;
    public static int SUPER_RESTORE_USEAT;

    public static int[] SUPER_ATTACK_ID = {149, 147, 145, 2436};
    public static int SUPER_ATTACK_AMOUNT;
    public static int SUPER_ATTACK_USEAT;

    public static int[] SUPER_STRENGTH_ID = {161, 159, 157, 2440};
    public static int SUPER_STRENGTH_AMOUNT;
    public static int SUPER_STRENGTH_USEAT;

    public static int[] SUPER_DEFENCE_ID = {167, 165, 163, 2442};
    public static int SUPER_DEFENCE_AMOUNT;
    public static int SUPER_DEFENCE_USEAT;

    public static int FOOD_ID = 391;
    public static int FOOD_AMOUNT;

    public static int EAT_AT_HP;

    public final static int BANK_ID = 26972;

    public static int STEADS = 0;
    public static int RAGES = 0;
    public static int GLAIVEN = 0;
    public static int SHARDS = 0;

    public static String STATUS = "";

    public static List<Integer> DROP_LIST;    //Items that should be picked up.

    public static int[] DO_NOT_DEPOSIT_ID = {SUPER_RESTORE_ID, SUPER_ATTACK_ID[3], SUPER_STRENGTH_ID[3], SUPER_DEFENCE_ID[3], FOOD_ID};

    public static Area GlacorsLair = new Area(new Tile(2626, 4015), new Tile(2680,3978));

}
