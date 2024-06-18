// DepositPage.java
package com.atmapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DepositPage extends JFrame {
    private JTextField amountField;

    public DepositPage() {
        setTitle("Deposit");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField();
        JButton depositButton = new JButton("Deposit");

        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(new JLabel(""));
        panel.add(depositButton);

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void deposit() {
        double amount = Double.parseDouble(amountField.getText());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE users SET balance = balance + ? WHERE card_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setString(2, LoginPage.cardNumberField.getText());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Deposit successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
