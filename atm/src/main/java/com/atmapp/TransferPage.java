package com.atmapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TransferPage extends JFrame {
    private JTextField recipientField;
    private JTextField amountField;

    public TransferPage() {
        setTitle("Transfer");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel recipientLabel = new JLabel("Recipient:");
        recipientField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField();
        JButton transferButton = new JButton("Transfer");

        panel.add(recipientLabel);
        panel.add(recipientField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(new JLabel(""));
        panel.add(transferButton);

        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                transfer();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void transfer() {
        String recipient = recipientField.getText();
        double amount = Double.parseDouble(amountField.getText());

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            String debitSql = "UPDATE users SET balance = balance - ? WHERE card_number = ?";
            PreparedStatement debitStmt = conn.prepareStatement(debitSql);
            debitStmt.setDouble(1, amount);
            debitStmt.setString(2, LoginPage.cardNumberField.getText());
            debitStmt.executeUpdate();

            String creditSql = "UPDATE users SET balance = balance + ? WHERE card_number = ?";
            PreparedStatement creditStmt = conn.prepareStatement(creditSql);
            creditStmt.setDouble(1, amount);
            creditStmt.setString(2, recipient);
            creditStmt.executeUpdate();

            conn.commit();

            JOptionPane.showMessageDialog(this, "Transfer successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Transfer failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
