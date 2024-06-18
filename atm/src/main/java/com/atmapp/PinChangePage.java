package com.atmapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class PinChangePage extends JFrame {
    private JPasswordField oldPinField;
    private JPasswordField newPinField;

    public PinChangePage() {
        setTitle("Change PIN");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel oldPinLabel = new JLabel("Old PIN:");
        oldPinField = new JPasswordField();
        JLabel newPinLabel = new JLabel("New PIN:");
        newPinField = new JPasswordField();
        JButton changePinButton = new JButton("Change PIN");

        panel.add(oldPinLabel);
        panel.add(oldPinField);
        panel.add(newPinLabel);
        panel.add(newPinField);
        panel.add(new JLabel(""));
        panel.add(changePinButton);

        changePinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changePin();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void changePin() {
        String oldPin = new String(oldPinField.getPassword());
        String newPin = new String(newPinField.getPassword());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE users SET pin = ? WHERE card_number = ? AND pin = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPin);
            stmt.setString(2, LoginPage.cardNumberField.getText());
            stmt.setString(3, oldPin);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "PIN changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Old PIN is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
