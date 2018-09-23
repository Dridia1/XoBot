package com.dridia.GUI.controller;

import com.dridia.GUI.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dridia on 2018-01-23.
 */
public class MainFrameController {
    private MainFrame mainFrame;

    public JSpinner withdrawSprRestSpinner;
    public JSpinner useWhenSprRestSpinner;
    public JSpinner withdrawSprAtkSpinner;
    public JSpinner useWhenSprAtkSpinner;
    private JPanel Supplies;
    public JTextField txtFoodID;
    public JSpinner eatAtSpinner;
    public JCheckBox checkBoxPiety;
    public JCheckBox checkBoxMagic;
    private JTextField txtAddItemID;
    private JButton btnAddItemToLootTable;
    private JButton btnRemoveItem;
    private JTextPane NOTEYouDoNOTTextPane;
    public JList lootTable;
    private JButton btnStartHunt;
    public JSpinner withdrawSprStrSpinner;
    public JSpinner withdrawSprDefSpinner;
    public JSpinner useWhenSprStrSpinner;
    public JSpinner useWhenSprDefSpinner;
    private JPanel mainPane;

    DefaultListModel<String> model = new DefaultListModel<>();

    public MainFrameController(int width, int height, boolean resizeable){
        initComponents(width, height, resizeable);
        initListeners();
    }

    private void initListeners() {
        btnAddItemToLootTable.addActionListener(new addItemListener());
        btnRemoveItem.addActionListener(new removeItemactionListener());
        btnStartHunt.addActionListener(new startBottingActionListener());
    }

    private void initComponents(int width, int height, boolean resizeable) {
        mainFrame = new MainFrame();
        mainFrame.setMinimumSize(new Dimension(width, height));

        mainFrame.getTxtFieldPrayer().setBackground(new Color(236, 237, 236));
        mainFrame.getTxtFieldAddLoot().setBackground(new Color(236, 237, 236));
        mainFrame.getLootTable().setBackground(new Color(236, 237, 236));

        mainFrame.setResizable(resizeable);

        /*Get the spinners*/
        withdrawSprRestSpinner = mainFrame.getWithdrawSprRestSpinner();
        withdrawSprAtkSpinner = mainFrame.getWithdrawSprAtkSpinner();
        withdrawSprStrSpinner = mainFrame.getWithdrawSprStrSpinner();
        withdrawSprDefSpinner = mainFrame.getWithdrawSprDefSpinner();

        useWhenSprRestSpinner = mainFrame.getUseWhenSprRestSpinner();
        useWhenSprAtkSpinner = mainFrame.getUseWhenSprAtkSpinner();
        useWhenSprStrSpinner = mainFrame.getUseWhenSprStrSpinner();
        useWhenSprDefSpinner = mainFrame.getUseWhenSprDefSpinner();

        eatAtSpinner = mainFrame.getEatAtSpinner();
        /*End the spinners*/

        checkBoxPiety = mainFrame.getCheckBoxPiety();


        txtFoodID = mainFrame.getTxtFoodID();

        btnAddItemToLootTable = mainFrame.getBtnAddItemToLootTable();
        btnRemoveItem = mainFrame.getBtnRemoveItem();

        btnStartHunt = mainFrame.getBtnStartHunt();
        lootTable = mainFrame.getLootTable();
        txtAddItemID = mainFrame.getTxtAddItemID();

        lootTable = mainFrame.getLootTable();
        lootTable.setModel(model);

    }

    public void show() {
        mainFrame.setVisible(true);
    }

    public boolean isVisible() {
        return mainFrame.isVisible();
    }

    private class addItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if (isInteger(txtAddItemID.getText())) {
                model.addElement(txtAddItemID.getText());
            } else {
                JOptionPane.showMessageDialog(null, "You can only enter integers.", "Only integers!", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    private class removeItemactionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            DefaultListModel model = (DefaultListModel) lootTable.getModel();
            int selectedIndex = lootTable.getSelectedIndex();
            if (selectedIndex != -1) {
                model.remove(selectedIndex);
            }else{
                JOptionPane.showMessageDialog(null, "Select an item in the lost in order to remove it..", "Select an item", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }

        return true;
    }

    private class startBottingActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            Integer sprRestCount = (Integer) withdrawSprRestSpinner.getValue();
            Integer sprAtkCount = (Integer)withdrawSprAtkSpinner.getValue();
            Integer sprStrCount = (Integer)withdrawSprStrSpinner.getValue();
            Integer sprDefCount = (Integer)withdrawSprDefSpinner.getValue();

            Integer total = sprRestCount + sprAtkCount + sprStrCount + sprDefCount;

            if(total > 27){
                JOptionPane.showMessageDialog(null, "This wont allow the bot to withdraw any food. Lower the values for supplies amount. Total should be lower than 28.", "Lower pot amount", JOptionPane.INFORMATION_MESSAGE);
            }else{
                if(txtFoodID.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Enter a food ID", "No food ID", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    if((Integer)eatAtSpinner.getValue() < 1){
                        JOptionPane.showMessageDialog(null, "Enter a value to where the bot should eat.", "No eatAt set", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        //Start the bot, set all the values then let the light shine.
                        mainFrame.setVisible(false);
                    }
                }
            }
        }
    }
}
