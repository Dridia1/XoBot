import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.dridia.methods.DecantingMethods;
import xobot.client.callback.listeners.MessageListener;
import xobot.client.callback.listeners.PaintListener;
import xobot.script.ActiveScript;
import xobot.script.Manifest;
import xobot.script.methods.NPCs;
import xobot.script.methods.Packets;
import xobot.script.methods.tabs.Skills;
import xobot.script.util.Time;
import xobot.script.util.Timer;
import com.dridia.methods.BankingMethods;
import com.dridia.methods.HerbloreMethods;
import com.dridia.data.Data;
import xobot.script.wrappers.interactive.NPC;


import javax.swing.*;

@Manifest(authors = { "Dridia" }, name = "Dridia's Potion Maker", version = 2.0, description = "Makes unfinished and finished potions and decants Combat potions.")
public class dridiaPotionMaker extends ActiveScript implements PaintListener, MessageListener{



    public static Timer runTime = null;

    public static int startExp = 0;
    public static int startLvl = 0;
    public static boolean decant = false;
    public static String itemToDecant = "";
    JComboBox<String> combo;

    @Override
    public boolean onStart() {
        JDialog frame = new JDialog();
        frame.setPreferredSize(new Dimension(300,150));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        FlowLayout layout = new FlowLayout();
        layout.setHgap(5);
        layout.setVgap(5);
        frame.setLayout(layout);

        combo = new JComboBox<String>();
        combo.setPreferredSize(new Dimension(200,30));
        combo.setFocusable(false);

        mixingAlternatives();

        JCheckBox decantPotion = new JCheckBox("Decant Potion");
        decantPotion.setPreferredSize(new Dimension(150,32));
        decantPotion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                decant = abstractButton.getModel().isSelected();
                if(decant){
                    decantingAlternatives();
                }else{
                    mixingAlternatives();
                }
            }
        });

        JButton button = new JButton("Start");
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(60,32));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                switch(combo.getSelectedItem().toString()) {
                    case "--Select a potion--":
                        onStop();
                        break;
                    case "Extreme Magic (3)":
                        Data.mainIng = 9594;
                        Data.secondaryIng = 3042;
                        break;
                    case "Extreme Defence (3)":
                        Data.mainIng = 2481;
                        Data.secondaryIng = 163;
                        break;
                    case "Extreme Strength (3)":
                        Data.mainIng = 267;
                        Data.secondaryIng = 157;
                        break;
                    case "Extreme Attack (3)":
                        Data.mainIng = 261;
                        Data.secondaryIng = 145;
                        break;
                    case "Dwarf Weed Potion (unf)":
                        Data.mainIng = 227;
                        Data.secondaryIng = 267;
                        break;
                    case "Ranging Potion (3)":
                        Data.mainIng = 245;
                        Data.secondaryIng = 109;
                        break;
                    case "Lantadyme Potion (unf)":
                        Data.mainIng = 227;
                        Data.secondaryIng = 2481;
                        break;
                    case "Magic Potion (3)":
                        Data.mainIng = 3138;
                        Data.secondaryIng = 2483;
                        break;
                    case "Cadantine Potion (unf)":
                        Data.mainIng = 227;
                        Data.secondaryIng = 265;
                        break;
                    case "Super Defence (3)":
                        Data.mainIng = 239;
                        Data.secondaryIng = 107;
                        break;
                    case "Kwuarm Potion (unf)":
                        Data.mainIng = 227;
                        Data.secondaryIng = 263;
                        break;
                    case "Super strength (3)":
                        Data.mainIng = 225;
                        Data.secondaryIng = 105;
                        break;
                    case "Irit Potion (unf)":
                        Data.mainIng = 227;
                        Data.secondaryIng = 259;
                        break;
                    case "Super Attack (3)":
                        Data.mainIng = 221;
                        Data.secondaryIng = 101;
                        break;
                    case "Guam potion (unf)":
                        Data.mainIng = 227;
                        Data.secondaryIng = 249;
                        break;
                    case "Attack potion (3)":
                        Data.mainIng = 221;
                        Data.secondaryIng = 91;
                        break;
                    case "Ranarr potion (unf)":
                        Data.mainIng = 227;
                        Data.secondaryIng = 257;
                        break;
                    case "Prayer potion (3)":
                        Data.mainIng = 231;
                        Data.secondaryIng = 99;
                        break;
                    case "Harralander potion (unf)":
                        Data.mainIng = 227;
                        Data.secondaryIng = 255;
                        break;
                    case "Combat potion (3)":
                        Data.mainIng = 9736;
                        Data.secondaryIng = 97;
                        Data.finishedId = 9741;
                        break;
                    case "Snapdragon potion (unf)":
                        Data.mainIng = 227;
                        Data.secondaryIng = 3000;
                        break;
                    case "Super restore (3)":
                        Data.mainIng = 3004;
                        Data.secondaryIng = 223;
                        break;
                    case "default":
                        onStop();
                        break;
                }
                itemToDecant = combo.getSelectedItem().toString();
                frame.dispose();
                runTime = new Timer(System.currentTimeMillis());
                startExp = Skills.getCurrentExp(Skills.HERBLORE);
                startLvl = Skills.getCurrentLevel(Skills.HERBLORE);

            }

        });

        frame.add(combo);
        frame.add(button);
        frame.add(decantPotion);
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
        if (!decant){
            if (HerbloreMethods.canMix()) {
                HerbloreMethods.doMix();
            }else if(Data.outOfSupplies){
                return -1;
            } else if(BankingMethods.needBank() && BankingMethods.canBank()) {
                BankingMethods.doBank();
            }
        }else{
            //Decanting should be simplified
            if(DecantingMethods.canDecant()) {
                DecantingMethods.doDecant();
            }else if(Data.outOfSupplies) {
                return -1;
            }else if(BankingMethods.canBank() && BankingMethods.needDecBank()){
                BankingMethods.doDecBank();
            }
        }
        return 100;
    }

    private final Color color = new Color(19, 197, 255);

    @Override
    public void repaint(Graphics g) {

        double xpGained = Skills.getCurrentExp(Skills.HERBLORE) - startExp;
        int perHour = (int) ((xpGained) * 3600000D / (runTime.getElapsed()));
        int currentLvl = Skills.getCurrentLevel(Skills.HERBLORE);

        Graphics2D graph = (Graphics2D)g;

        graph.setColor(color);
        graph.drawString("Dridia's Potion Maker v2.0", 10, 20);
        graph.drawString("Runtime: " + runTime.toElapsedString(), 10, 35);
        if(!decant) {
            graph.drawString("XP Gained: " + (int) xpGained, 10, 50);
            graph.drawString("XP/h: " + perHour, 10, 65);
            graph.drawString("Current lvl: " + currentLvl + "(" + "+" + (currentLvl-startLvl) + ")", 10, 80);
        }else{
            graph.drawString("Decanting " + itemToDecant + " -> (4)", 10, 50);
        }

    }

    @Override
    public void MessageRecieved(String arg0, int arg1, String arg2) {
        // TODO Auto-generated method stub

    }

    public void mixingAlternatives(){
        combo.removeAllItems();
        combo.addItem("--Mix potion--");

        combo.addItem("Guam potion (unf)");
        combo.addItem("Attack potion (3)");

        combo.addItem("Harralander potion (unf)");
        combo.addItem("Combat potion (3)");

        combo.addItem("Ranarr potion (unf)");
        combo.addItem("Prayer potion (3)");

        combo.addItem("Snapdragon potion (unf)");
        combo.addItem("Super restore (3)");

        combo.addItem("Irit Potion (unf)");
        combo.addItem("Super Attack (3)");

        combo.addItem("Kwuarm Potion (unf)");
        combo.addItem("Super strength (3)");

        combo.addItem("Cadantine Potion (unf)");
        combo.addItem("Super Defence (3)");

        combo.addItem("Lantadyme Potion (unf)");
        combo.addItem("Magic Potion (3)");

        combo.addItem("Dwarf Weed Potion (unf)");
        combo.addItem("Ranging Potion (3)");

        combo.addItem("Extreme Attack (3)");
        combo.addItem("Extreme Strength (3)");
        combo.addItem("Extreme Defence (3)");
        combo.addItem("Extreme Magic (3)");

    }

    public void decantingAlternatives(){
        combo.removeAllItems();
        combo.addItem("--Decant potion--");
        combo.addItem("Combat potion (3)")

    }
    
}

