package com.atmapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPage extends JFrame {
    public MainMenuPage() {
        setTitle("Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton bankingButton = new JButton("Banking");
        JButton transferButton = new JButton("Transfer");
        JButton pinChangeButton = new JButton("PIN Change");
        JButton balanceCheckButton = new JButton("Balance Check");

        bankingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BankingPage();
            }
        });

        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TransferPage();
            }
        });

        pinChangeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PinChangePage();
            }
        });

        balanceCheckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BalanceCheckPage();
            }
        });

        panel.add(bankingButton);
        panel.add(transferButton);
        panel.add(pinChangeButton);
        panel.add(balanceCheckButton);

        add(panel);
        setVisible(true);
    }
}
