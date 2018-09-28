package com.dridia.GUI;

import xobot.script.util.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by marcus on 2018-09-27.
 */
public class GUI {

    private static JTextField foodID;
    private static JTextField eatAt;

    public static void setupAndShowGUI() {
        JDialog frame = new JDialog();
        frame.setPreferredSize(new Dimension(204,170));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        FlowLayout layout = new FlowLayout();
        layout.setHgap(5);
        layout.setVgap(5);
        frame.setLayout(layout);

        JLabel lblFoodID = new JLabel("Food ID:");
        JLabel lblEatAt = new JLabel("Bank at HP:");

        foodID = new JTextField();
        foodID.setPreferredSize(new Dimension(150,32));
        eatAt = new JTextField();
        eatAt.setPreferredSize(new Dimension(150,32));

        JButton button = new JButton("Start");
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(60,32));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
            }
        });

        frame.add(lblFoodID);
        frame.add(foodID);
        frame.add(lblEatAt);
        frame.add(eatAt);
        frame.add(button);
        frame.setTitle("Dridia's Tai Bwo Wannai");
        frame.pack();
        frame.setVisible(true);
        while(frame.isVisible()) {
            Time.sleep(500);
        }
    }

    public static int getFoodId(){
        return Integer.parseInt(foodID.getText());
    }

    public static int getEatAtHP(){
        return Integer.parseInt(eatAt.getText());
    }

}
