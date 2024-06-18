package com.atmapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPage extends JFrame {
    static JTextField cardNumberField;
    private JPasswordField pinField;

    public LoginPage() {
        setTitle("ATM Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField();
        JLabel pinLabel = new JLabel("PIN:");
        pinField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(cardNumberLabel);
        panel.add(cardNumberField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(loginButton);
        panel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterPage();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void login() {
        String cardNumber = cardNumberField.getText();
        String pin = new String(pinField.getPassword());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE card_number = ? AND pin = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cardNumber);
            stmt.setString(2, pin);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                new MainMenuPage();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid card number or PIN", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
