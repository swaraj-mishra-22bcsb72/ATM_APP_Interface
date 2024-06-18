package com.atmapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class WithdrawPage extends JFrame {
    private JTextField amountField;

    public WithdrawPage() {
        setTitle("Withdraw");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField();
        JButton withdrawButton = new JButton("Withdraw");

        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(new JLabel(""));
        panel.add(withdrawButton);

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void withdraw() {
        double amount = Double.parseDouble(amountField.getText());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE users SET balance = balance - ? WHERE card_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setString(2, LoginPage.cardNumberField.getText());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Withdrawal successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}