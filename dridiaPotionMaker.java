
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import xobot.client.callback.listeners.MessageListener;
import xobot.client.callback.listeners.PaintListener;
import xobot.script.ActiveScript;
import xobot.script.Manifest;
import xobot.script.methods.*;
import xobot.script.methods.tabs.Inventory;
import xobot.script.methods.tabs.Skills;
import xobot.script.util.Time;
import xobot.script.util.Timer;
import xobot.script.wrappers.interactive.GameObject;
import xobot.script.wrappers.interactive.Item;
import xobot.script.wrappers.interactive.Player;

import javax.swing.*;

@Manifest(authors = { "Dridia" }, name = "Dridia's Potion Maker", version = 1.0, description = "Makes unfinished and finished potions")
public class dridiaPotionMaker extends ActiveScript implements PaintListener, MessageListener{

    private static int secondaryIng = 0;
    private static int mainIng = 0;

    public static Timer runTime = null;

    public static int startExp = 0;
    public static int startLvl = 0;

    public static boolean outOfSupplies = false;

    @Override
    public boolean onStart() {
        JDialog frame = new JDialog();
        frame.setPreferredSize(new Dimension(300,90));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        FlowLayout layout = new FlowLayout();
        layout.setHgap(5);
        layout.setVgap(5);
        frame.setLayout(layout);

        JComboBox<String> combo = new JComboBox<String>();
        combo.setPreferredSize(new Dimension(200,30));
        combo.setFocusable(false);
        combo.addItem("Guam potion (unf)");
        combo.addItem("Attack potion (3)");
        combo.addItem("Harralander potion (unf)");
        combo.addItem("Combat potion (3)");
        combo.addItem("Ranarr potion (unf)");
        combo.addItem("Prayer potion (3)");
        combo.addItem("Snapdragon potion (unf)");
        combo.addItem("Super restore (3)");


        JButton button = new JButton("Start");
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(60,32));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                switch(combo.getSelectedItem().toString()) {
                    case "Guam potion (unf)":
                        mainIng = 227;
                        secondaryIng = 249;
                        break;
                    case "Attack potion (3)":
                        mainIng = 221;
                        secondaryIng = 91;
                        break;
                    case "Ranarr potion (unf)":
                        mainIng = 227;
                        secondaryIng = 257;
                        break;
                    case "Prayer potion (3)":
                        mainIng = 231;
                        secondaryIng = 99;
                        break;
                    case "Harralander potion (unf)":
                        mainIng = 227;
                        secondaryIng = 255;
                        break;
                    case "Combat potion (3)":
                        mainIng = 9736;
                        secondaryIng = 97;
                        break;
                    case "Snapdragon potion (unf)":
                        mainIng = 227;
                        secondaryIng = 3000;
                        break;
                    case "Super restore (3)":
                        mainIng = 3004;
                        secondaryIng = 223;
                        break;
                    case "default":
                        onStop();
                        break;
                }
                frame.dispose();
                runTime = new Timer(System.currentTimeMillis());
                startExp = Skills.getCurrentExp(Skills.HERBLORE);
                startLvl = Skills.getCurrentLevel(Skills.HERBLORE);

            }

        });

        frame.add(combo);
        frame.add(button);
        frame.setTitle("Dridia's Potion Maker");


        frame.pack();
        frame.setVisible(true);
        while(frame.isVisible()) {
            Time.sleep(500);
        }
        return true;
    }

    @Override
    public void onStop() {
        System.out.println("Script stopped");
    }

    @Override
    public int loop() {
        if (HerbloreMethods.canMix()) {
            HerbloreMethods.doMix();
        }else if(outOfSupplies){
            return -1;
        } else if(BankingMethods.needBank() && BankingMethods.canBank()) {
            BankingMethods.doBank();
        }
        return 100;
    }

    public static int getMainIng(){
        return mainIng;
    }

    public static int getSecondaryIng(){
        return secondaryIng;
    }

    private final Color color = new Color(19, 197, 255);

    @Override
    public void repaint(Graphics g) {

        double xpGained = Skills.getCurrentExp(Skills.HERBLORE) - startExp;
        int perHour = (int) ((xpGained) * 3600000D / (runTime.getElapsed()));
        int currentLvl = Skills.getCurrentLevel(Skills.HERBLORE);

        Graphics2D graph = (Graphics2D)g;

        graph.setColor(color);
        graph.drawString("Dridia's Potion Maker", 10, 20);
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

class BankingMethods {

    public static boolean canBank() {
        final GameObject bank = GameObjects.getNearest(26972);
        return bank != null && bank.getDistance() <= 3;
    }

    public static boolean needBank() {
        return (!Inventory.Contains(dridiaPotionMaker.getMainIng()) || !Inventory.Contains(dridiaPotionMaker.getSecondaryIng()));
    }

    public static void doBank() {
        final GameObject bank = GameObjects.getNearest(26972);

        if (bank != null && bank.getDistance() <= 3 && !Bank.isOpen()) {
            bank.interact("Bank");
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Bank.isOpen();
                }
            }, 5000);
        }

        if (Bank.isOpen()) {
            if (Inventory.getCount() > 0) {
                Bank.depositAll();
                Time.sleep(490, 590);
            }
            if ((!Inventory.Contains(dridiaPotionMaker.getMainIng()) && !Inventory.Contains(dridiaPotionMaker.getSecondaryIng()))) {
                Item herb = Bank.getItem(dridiaPotionMaker.getSecondaryIng());
                Item vial = Bank.getItem(dridiaPotionMaker.getMainIng());
                if (herb != null && vial != null) {
                    Bank.withdraw(dridiaPotionMaker.getMainIng(), 14);
                    Methods.conditionalSleep(new SleepCondition() {
                        @Override
                        public boolean isValid() {
                            return Inventory.Contains(dridiaPotionMaker.getMainIng());
                        }
                    }, 3000);
                    Bank.withdraw(dridiaPotionMaker.getSecondaryIng(), 14);
                    Methods.conditionalSleep(new SleepCondition() {
                        @Override
                        public boolean isValid() {
                            return Inventory.Contains(dridiaPotionMaker.getSecondaryIng());
                        }
                    }, 3000);
                }else{
                    dridiaPotionMaker.outOfSupplies = true;
                }
            }
        }
    }
}

class HerbloreMethods {

    public static boolean canMix() {
        return Inventory.Contains(dridiaPotionMaker.getMainIng()) && Inventory.Contains(dridiaPotionMaker.getSecondaryIng()) && !areMixing();
    }

    public static void doMix() {
        Item herb = Inventory.getItem(dridiaPotionMaker.getSecondaryIng());
        Item vial = Inventory.getItem(dridiaPotionMaker.getMainIng());

        if (herb != null && vial != null) {
            Packets.sendAction(447, herb.getID(), herb.getSlot(), 3214);
            Time.sleep(190, 250);
            Packets.sendAction(870, vial.getID(), vial.getSlot(), 3214);

            Methods.conditionalSleep( new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Widgets.getBackDialogId() == 4429;
                }
            }, 5000);

            if(Widgets.getBackDialogId() == 4429) {
                Packets.sendAction(315, -1, -1, 1747);
                Time.sleep(700, 900);
            }
        }
    }

    private static boolean areMixing(){
        Timer t = new Timer();
        int startCount = Inventory.getAll().length;
        while(t.getElapsed() < 2120){
            if(Player.getMyPlayer().getAnimation() == 363 || Inventory.getAll().length < startCount){
                return true;
            }
            Time.sleep(40, 100);
        }
        return false;
    }
}

class Methods {
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
}

interface SleepCondition {
    /**
     * Determine if condition is valid
     * @return <b>true</b> if valid, otherwise <b>false</b>.
     */
    boolean isValid();

}