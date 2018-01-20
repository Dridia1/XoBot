import java.awt.*;
import xobot.client.callback.listeners.MessageListener;
import xobot.client.callback.listeners.PaintListener;
import xobot.script.ActiveScript;
import xobot.script.Manifest;
import xobot.script.methods.*;
import xobot.script.methods.tabs.Inventory;
import xobot.script.methods.tabs.Skills;
import xobot.script.util.Timer;
import xobot.script.wrappers.Tile;


@Manifest(authors = { "Dridia" }, name = "Dridia's Auto Hunter", version = 0.1, description = "This will catch implings at the hunter area.")
public class dridiaAutoHunter extends ActiveScript implements PaintListener, MessageListener{

    public static Timer runTime = null;
    public static int startExp = 0;
    public static int startLvl = 0;

    public static int ImpJar = 11260;
    public static Tile startTile = new Tile(2556, 2845);
    public static Imp selectedImp = Imp.GOURMET;

    public static Tile nextSpotTile;

    //Baby Impling: lvl 1, id: 6055 A: 3299
    //Young Impling: lvl 22, id: 6056
    //Gourmet Impling: lvl 28, id: 6957
    //Earth Impling: lvl 36, id: 6058
    //Essence Impling: lvl 42, id: 6059
    //Nature Impling: lvl 58, id: 6061
    //Magpie Impling: lvl 65, id: 6062
    //Ninja Impling: lvl 74, id: 6063
    //Dragon Impling: lvl 83, id: 6064

    @Override
    public boolean onStart() {
        runTime = new Timer(System.currentTimeMillis());
        startExp = Skills.getCurrentExp(22);
        startLvl = Skills.getCurrentLevel(22);
        return true;
    }

    @Override
    public void onStop() {
        System.out.println("Script stopped");
    }

    @Override
    public int loop() {
        if(ImpMethods.shouldWalkToStartLocation()){
            walkingMethods.walkToStartLocation();
        }else if (ImpMethods.impNear() && Inventory.Contains(11260)) {
            ImpMethods.catchImp();
        }else if (Inventory.Contains(11260) && !selectedImp.getTiles()[0].isReachable()){
            Tile closerTile = walkingMethods.getCloserTile(selectedImp.getTiles()[0]);
            Walking.walkTo(closerTile);
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Calculations.distanceBetween(closerTile, Players.getMyPlayer().getLocation()) <= 3;
                }
            },25000);
        }else if(Inventory.Contains(11260) && selectedImp.getTiles()[0].isReachable()){
            ImpMethods.switchSpot();
        }else if(BankingMethods.needBank()){
            if(!BankingMethods.canBank()) {
                ImpMethods.TeleportToBank();
            }else{
                BankingMethods.doBank();
            }
        }else{
            ImpMethods.TeleportToShop();
            ImpMethods.BuySupplies();
        }
        return 80;
    }

    private final Color color = new Color(19, 197, 255);

    @Override
    public void repaint(Graphics g) {

        double xpGained = Skills.getCurrentExp(22) - startExp;
        int perHour = (int) ((xpGained) * 3600000D / (runTime.getElapsed()));
        int currentLvl = Skills.getCurrentLevel(22);

        Graphics2D graph = (Graphics2D)g;

        graph.setColor(color);
        graph.drawString("Dridia's Auto Hunter", 10, 20);
        graph.drawString("Runtime: " + runTime.toElapsedString(), 10, 35);
        graph.drawString("XP Gained: " + (int)xpGained, 10, 50);
        graph.drawString("XP/h: " + perHour, 10, 65);
        graph.drawString("Current lvl: " + currentLvl + "(" + "+" + (currentLvl-startLvl) + ")", 10, 80);
    }

    @Override
    public void MessageRecieved(String arg0, int arg1, String arg2) {
        // TODO Auto-generated method stub

    }
}



