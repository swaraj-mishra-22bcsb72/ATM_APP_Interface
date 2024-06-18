package com.atmapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingPage extends JFrame {
    public BankingPage() {
        setTitle("Banking");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WithdrawPage();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DepositPage();
            }
        });

        panel.add(withdrawButton);
        panel.add(depositButton);

        add(panel);
        setVisible(true);
    }
}
