package com.atmapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterPage extends JFrame {
    private JTextField cardNumberField;
    private JPasswordField pinField;

    public RegisterPage() {
        setTitle("Register Card");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField();
        JLabel pinLabel = new JLabel("PIN:");
        pinField = new JPasswordField();
        JButton registerButton = new JButton("Register");

        panel.add(cardNumberLabel);
        panel.add(cardNumberField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(new JLabel(""));
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void register() {
        String cardNumber = cardNumberField.getText();
        String pin = new String(pinField.getPassword());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (card_number, pin) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cardNumber);
            stmt.setString(2, pin);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Card registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
