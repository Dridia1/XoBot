package com.dridia.GUI;

import javax.swing.*;

/**
 * Created by Dridia on 2018-01-23.
 */
public class MainFrame extends JFrame{
    private JSpinner withdrawSprRestSpinner;
    private JSpinner useWhenSprRestSpinner;
    private JSpinner withdrawSprAtkSpinner;
    private JSpinner useWhenSprAtkSpinner;
    private JPanel panelSupplies;
    private JTextField txtFoodID;
    private JSpinner eatAtSpinner;
    private JCheckBox checkBoxPiety;
    private JCheckBox checkBoxMagic;
    private JTextField txtAddItemID;
    private JButton btnAddItemToLootTable;
    private JTextPane txtFieldAddLoot;
    private JList lootTable;

    public JButton getBtnRemoveItem() {
        return btnRemoveItem;
    }

    private JButton btnStartHunt;

    public JSpinner getUseWhenSprDefSpinner() {
        return useWhenSprDefSpinner;
    }

    public JSpinner getWithdrawSprStrSpinner() {
        return withdrawSprStrSpinner;
    }

    public JSpinner getWithdrawSprDefSpinner() {
        return withdrawSprDefSpinner;
    }

    public JSpinner getUseWhenSprStrSpinner() {
        return useWhenSprStrSpinner;
    }

    public JSpinner getUseWhenSprAtkSpinner() {
        return useWhenSprAtkSpinner;
    }

    public JSpinner getWithdrawSprRestSpinner() {
        return withdrawSprRestSpinner;
    }

    public JSpinner getUseWhenSprRestSpinner() {
        return useWhenSprRestSpinner;
    }

    public JSpinner getWithdrawSprAtkSpinner() {
        return withdrawSprAtkSpinner;
    }

    public JSpinner getEatAtSpinner() {
        return eatAtSpinner;
    }

    public JCheckBox getCheckBoxPiety() {
        return checkBoxPiety;
    }

    public JCheckBox getCheckBoxMagic() {
        return checkBoxMagic;
    }

    public JTextField getTxtFoodID() {
        return txtFoodID;
    }

    private JSpinner withdrawSprStrSpinner;
    private JSpinner withdrawSprDefSpinner;
    private JSpinner useWhenSprStrSpinner;
    private JSpinner useWhenSprDefSpinner;
    private JPanel mainPane;
    private JPanel panelLoot;

    public JTextPane getTxtFieldAddLoot() {
        return txtFieldAddLoot;
    }

    public JTextPane getTxtFieldPrayer() {
        return txtFieldPrayer;
    }

    public JPanel getPanelPrayer() {
        return panelPrayer;
    }

    public JPanel getPanelAddLoot() {
        return panelAddLoot;
    }

    public JPanel getPanelLoot() {
        return panelLoot;
    }

    public JPanel getPanelSupplies() {
        return panelSupplies;
    }

    private JPanel panelAddLoot;
    private JPanel panelPrayer;
    private JTextPane txtFieldPrayer;
    private JButton btnRemoveItem;

    public MainFrame(){
        setContentPane(mainPane);
        setLocationRelativeTo(null);

    }

    public JButton getBtnStartHunt() {
        return btnStartHunt;
    }

    public JList getLootTable() {
        return lootTable;
    }

    public JTextField getTxtAddItemID() {
        return txtAddItemID;
    }

    public JButton getBtnAddItemToLootTable() {
        return btnAddItemToLootTable;
    }
}
