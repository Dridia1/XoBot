

import com.dridia.Banking;
import com.dridia.Chop;
import com.dridia.Teleportation;
import xobot.client.callback.listeners.PaintListener;
import xobot.script.ActiveScript;
import xobot.script.Manifest;
import xobot.script.methods.Players;

import java.awt.*;

@Manifest(authors = { "Dridia" }, name = "Dridia's Mahogany", version = 1.0, description = "Cuts mahogany @ Tai Bwo Wannai W/O a Pack Yak")

public class dridiaMahogany extends ActiveScript implements PaintListener{

    private static String current_state = "setup";

    /*
     * Gets invoked before the script starts looping
     * If this returns false the script will stop
     */
    public boolean onStart() {

        return true;
    }

    /*
     * The main script's loop, the return value is the time it'll get invoked again
     */
    @Override
    public int loop() {
        switch (updateState()) {
            case CHOP_TREES:
                Chop.handleChop();
                break;
            case BANKING:
                Banking.handleBank();
                break;
            case TELEPORT:
                Teleportation.teleportTaiBwoWannai();
                break;
            case ENTERAREA:
                Chop.enterChopArea();
                break;
            case LOST:
                //I'm lost. Maybe teleport somewhere if this happens.
                break;
            case CUTTING:
                current_state = "Cutting";
                break;
        }

        return 100;
    }

    /*
     * Your graphics painting goes here
     */
    @Override
    public void repaint(Graphics arg0) {
        // TODO Auto-generated method stub

    }



    private STATE updateState(){
        if (Banking.shouldBank()) {
            return STATE.BANKING;
        }else {
            if (Chop.canChop()) {
                if(Players.getMyPlayer().getAnimation() == 2846){
                    return STATE.CUTTING;
                }
                return STATE.CHOP_TREES;
            }
            else if(Chop.shouldChop()) {
                if(Chop.isOutsideChopArea()){
                    return STATE.ENTERAREA;
                }else{
                    return STATE.TELEPORT;
                }
            }
            else {
                return STATE.LOST;
            }
        }
    }

    private enum STATE{
        CHOP_TREES, TELEPORT, LOST, BANKING, ENTERAREA, CUTTING
    }

}
