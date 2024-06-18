package com.atmapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BalanceCheckPage extends JFrame {
    private JLabel balanceLabel;

    public BalanceCheckPage() {
        setTitle("Balance Check");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        balanceLabel = new JLabel("Balance: ", SwingConstants.CENTER);
        JButton checkBalanceButton = new JButton("Check Balance");

        checkBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        panel.add(balanceLabel);
        panel.add(checkBalanceButton);

        add(panel);
        setVisible(true);
    }

    private void checkBalance() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT balance FROM users WHERE card_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, LoginPage.cardNumberField.getText());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                balanceLabel.setText("Balance: " + balance);
            } else {
                balanceLabel.setText("Balance: Error");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            balanceLabel.setText("Balance: Error");
        }
    }
}
