import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import com.dridia.Actions.Combat;
import com.dridia.Actions.Teleport;
import com.dridia.GUI.controller.MainFrameController;
import xobot.client.callback.listeners.MessageListener;
import xobot.client.callback.listeners.PaintListener;
import xobot.client.events.MessageEvent;
import xobot.client.input.Mouse;
import xobot.script.ActiveScript;
import xobot.script.Manifest;
import xobot.script.methods.Packets;
import xobot.script.methods.Players;
import xobot.script.methods.Settings;
import xobot.script.methods.tabs.Skills;
import xobot.script.util.Time;
import xobot.script.util.Timer;

import com.dridia.Data.Variables;
import com.dridia.Actions.Supplies;
import xobot.script.wrappers.interactive.Character;
import xobot.script.wrappers.interactive.Player;

import javax.imageio.ImageIO;

/**
 * Created by Dridia on 2018-01-16..
 */

@Manifest(authors = { "Dridia" }, name = "Dridia's Glacor Killer - localversion", version = 1.1, description = "Kill Glacors at Glacors Lair")
public class dridiaGlacorHunter extends ActiveScript implements PaintListener, MessageListener, MouseListener{

    public Timer startTime;

    private final int paintToggleX = 493;
    private final int paintToggleY = 351;
    private final int paintToggleWidth = 40;
    private final int paintToggleHeight = 40;

    private boolean paintToggle = true;

    public final Image img1 = getImage("https://i.imgur.com/8AiQjha.png");
    public final Image img2 = getImage("https://vignette.wikia.nocookie.net/runescape2/images/f/f6/Options_button.png/revision/latest?cb=20150622123124");

    @Override
    public boolean onStart() {

        MainFrameController frame =  new MainFrameController(600, 500, true);

        /**/
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            frame.withdrawSprAtkSpinner.setValue(Integer.parseInt(prop.getProperty("atk_amount")));
            frame.useWhenSprAtkSpinner.setValue(Integer.parseInt(prop.getProperty("atk_usewhen")));

            frame.withdrawSprStrSpinner.setValue(Integer.parseInt(prop.getProperty("str_amount")));
            frame.useWhenSprStrSpinner.setValue(Integer.parseInt(prop.getProperty("str_usewhen")));

            frame.withdrawSprDefSpinner.setValue(Integer.parseInt(prop.getProperty("def_amount")));
            frame.useWhenSprDefSpinner.setValue(Integer.parseInt(prop.getProperty("def_usewhen")));

            frame.withdrawSprRestSpinner.setValue(Integer.parseInt(prop.getProperty("spr_amount")));
            frame.useWhenSprRestSpinner.setValue(Integer.parseInt(prop.getProperty("spr_usewhen")));

            frame.txtFoodID.setText(prop.getProperty("food_id"));
            frame.eatAtSpinner.setValue(Integer.parseInt(prop.getProperty("eat_at")));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**/

        frame.show();

        if(Settings.get(172) != 1){
            Packets.sendAction(169, 0, 0, 150); //Turn on Auto-Retaliate
        }

        while(frame.isVisible()) {
            Time.sleep(500);
        }

        /* Set all values that the user have chosen.*/

        //Get the prayer setting:
        Variables.USE_PIETY = frame.checkBoxPiety.isSelected();
        System.out.println("Piety: " + Variables.USE_PIETY);

        //Get the amount of potions to withdraw.
        int sprRestore = (Integer)frame.withdrawSprRestSpinner.getValue();
        int sprAttack = (Integer)frame.withdrawSprAtkSpinner.getValue();
        int sprStrength = (Integer)frame.withdrawSprStrSpinner.getValue();
        int sprDefence = (Integer)frame.withdrawSprDefSpinner.getValue();

        Variables.SUPER_RESTORE_AMOUNT = sprRestore; //6 is Recommended
        Variables.SUPER_ATTACK_AMOUNT = sprAttack;  //2 is Recommended
        Variables.SUPER_STRENGTH_AMOUNT = sprStrength;//2 is Recommended
        Variables.SUPER_DEFENCE_AMOUNT = sprDefence; //2 is Recommended

        //At what lvl to repot:
        Variables.SUPER_RESTORE_USEAT = (Integer)frame.useWhenSprRestSpinner.getValue();
        Variables.SUPER_ATTACK_USEAT = (Integer)frame.useWhenSprAtkSpinner.getValue();
        Variables.SUPER_STRENGTH_USEAT = (Integer)frame.useWhenSprStrSpinner.getValue();
        Variables.SUPER_DEFENCE_USEAT = (Integer)frame.useWhenSprDefSpinner.getValue();

        //MantaRay = 391
        //Get the foodID and at what hp to eat.
        Variables.FOOD_ID = Integer.parseInt(frame.txtFoodID.getText());
        Variables.EAT_AT_HP = (Integer)frame.eatAtSpinner.getValue();
        Variables.FOOD_AMOUNT = 28 - sprRestore - sprAttack - sprStrength - sprDefence; // The amount is 28 minus all the potions
        System.out.println("Food ID: " + Variables.FOOD_ID);
        System.out.println("Eat at hp: " + Variables.EAT_AT_HP);
        System.out.println("Food withdrawal: " + Variables.FOOD_AMOUNT);


        Variables.DROP_LIST = new ArrayList<>();
        //Add the drops to the DropList
        for(int i = 0; i < frame.lootTable.getModel().getSize();i++){
            int item = Integer.valueOf((String) frame.lootTable.getModel().getElementAt(i));
            Variables.DROP_LIST.add(item);
        }

        //Hardcoded loot:
        Variables.DROP_LIST.add(21790); //Glaiven boots
        Variables.DROP_LIST.add(21793); //Ragefire boots
        Variables.DROP_LIST.add(21787); //Steadfast boots
        Variables.DROP_LIST.add(21776); // Shards of armadyl

        startTime = new Timer(System.currentTimeMillis());

        /**/
        Properties propSave = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("config.properties");

            // set the properties value
            prop.setProperty("atk_amount", String.valueOf(sprAttack));
            prop.setProperty("atk_usewhen", String.valueOf(Variables.SUPER_ATTACK_USEAT));

            prop.setProperty("str_amount", String.valueOf(sprStrength));
            prop.setProperty("str_usewhen", String.valueOf(Variables.SUPER_STRENGTH_USEAT));

            prop.setProperty("def_amount", String.valueOf(sprDefence));
            prop.setProperty("def_usewhen", String.valueOf(Variables.SUPER_DEFENCE_USEAT));

            prop.setProperty("spr_amount", String.valueOf(sprRestore));
            prop.setProperty("spr_usewhen", String.valueOf(Variables.SUPER_RESTORE_USEAT));

            prop.setProperty("food_id", String.valueOf(Variables.FOOD_ID));
            prop.setProperty("eat_at", String.valueOf(Variables.EAT_AT_HP));

            prop.setProperty("piety_on", String.valueOf(Variables.USE_PIETY));


            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        /**/

        return true;
    }

    @Override
    public void onStop() {
        System.out.println("Script stopped");
    }

    @Override
    public int loop() {
        /*
        * Bug 1: Sometimes get stuck looking at the Glacor.
        * Solution 1: Check if player has attacked once if Combat.Attack() is called. (to make sure it attacks)
        *
        * Bug 2: If you pick up Coins but has maxcash in bank, it enters a deadLock
        * Solution 2: When you get cash, add them to the pouch.
        * */

        if(!Supplies.shouldRefill()) {
            if(!Combat.isAtLair()){
                Teleport.teleportGlacorsLair();
            }
            if(Supplies.shouldEat()){
                Variables.STATUS = "Eat";
                Supplies.eatFood();
            }
            if (Supplies.prayerLow()) {
                Variables.STATUS = "Restore prayer";
                Supplies.restorePrayer();
            }
            if(!Supplies.shouldPotUp().equals("null")){
                Supplies.potUp(Supplies.shouldPotUp());
            }
            if(Combat.lootFound()) {
                Variables.STATUS = "Loot";
                Combat.pickUpLoot();
            }else {
                if (!Player.getMyPlayer().isInCombat()) {
                    if (Combat.canAttack()) {
                        Variables.STATUS = "Attack";
                        Combat.Attack();
                    }
                }
            }
        }else{
            Variables.STATUS = "Refill";
            Supplies.doRefill();
        }

        if(Players.getMyPlayer().getInteractingIndex() != -1 || Player.getMyPlayer().isInCombat()){
            Combat.turnOnPrayer();
        }else{
            Combat.turnOffPrayer();
        }

        return 80;
    }


    @Override
    public void repaint(Graphics g) {
        Character interactingCharacter = Players.getMyPlayer().getInteractingCharacter();
        if(interactingCharacter != null){
            Color c = new Color(0, 255, 0);
            interactingCharacter.getLocation().draw(g, c);
        }

        if(paintToggle) {
            Graphics2D g1 = (Graphics2D) g;
            g.drawImage(img1, 6, 332, null);
            g1.setColor(Color.WHITE);
            g1.setFont(new Font("Verdana", Font.BOLD, 14));
            g1.drawString("Time running: " + startTime.toElapsedString(), 10, 390);

            g1.setColor(Color.WHITE);
            g1.setFont(new Font("Verdana", Font.BOLD, 14));
            g1.drawString(Integer.toString(Variables.STEADS), 46, 462);
            g1.drawString(Integer.toString(Variables.RAGES), 106, 462);
            g1.drawString(Integer.toString(Variables.GLAIVEN), 166, 462);
            g1.drawString(Integer.toString(Variables.SHARDS), 220, 462);
        }else {
            Graphics2D g1 = (Graphics2D) g;
            g.drawImage(img2, paintToggleX, paintToggleY-10, null);
        }
    }

    public void MessageRecieved(String arg0, int arg1, String arg2) {
        // TODO Auto-generated method stub

    }



    public Image getImage(String url) { // Adds image.
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void MessageRecieved(MessageEvent messageEvent) {

    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        if((e.getX() > paintToggleX && e.getX() < (paintToggleX + paintToggleWidth)) && (e.getY() > paintToggleY) && e.getY() < (paintToggleY + paintToggleHeight)){
            paintToggle = !paintToggle;
            System.out.println("tgl: " + paintToggle);
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /* in mouse clicked
    *         if((mouseEvent.getX() > paintToggleX && mouseEvent.getX() < (paintToggleX + paintToggleWidth)) && (mouseEvent.getY() > paintToggleY) && mouseEvent.getY() < (paintToggleY + paintToggleHeight)){
            if(paintToggle) {
                paintToggle = false;
            }else{
                paintToggle = true;
            }
        }
    * */
}

