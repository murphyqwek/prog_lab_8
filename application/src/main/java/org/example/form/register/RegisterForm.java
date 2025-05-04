package org.example.form.register;

import org.example.controller.RegisterContorller;
import org.example.network.NetworkClient;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class RegisterForm extends JFrame {
    private RegisterContorller registerContorller;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterForm(RegisterContorller controller) {
        this.registerContorller = controller;
    }

    private void stylizeField(JTextField field) {
        field.setPreferredSize(new Dimension(300, 30));
        field.setMaximumSize(new Dimension(300, 30));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    public void gui() {
        setTitle("Lab8 Starikov Arseny");
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Конейнер регистрации
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(new EmptyBorder(30, 40, 30, 40)); // внутренние отступы
        container.setBackground(Color.WHITE);
        container.setMaximumSize(new Dimension(400, 400));
        container.setPreferredSize(new Dimension(350, 310));

        // Скруглённая граница
        container.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true),
                container.getBorder()
        ));

        // Поле Register
        JLabel title = new JLabel("Create an account");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(title);
        container.add(Box.createVerticalStrut(20));

        // Блок для Username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        usernameField = new JTextField();
        stylizeField(usernameField);
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel usernameBlock = new JPanel();
        usernameBlock.setLayout(new BoxLayout(usernameBlock, BoxLayout.Y_AXIS));
        usernameBlock.setOpaque(false);
        usernameBlock.setBorder(new EmptyBorder(0, 0, 10, 0));
        usernameBlock.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameBlock.add(usernameLabel);
        usernameBlock.add(Box.createVerticalStrut(5));
        usernameBlock.add(usernameField);

        // Блок для Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordField = new JPasswordField();
        stylizeField(passwordField);
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel passwordBlock = new JPanel();
        passwordBlock.setLayout(new BoxLayout(passwordBlock, BoxLayout.Y_AXIS));
        passwordBlock.setOpaque(false);
        passwordBlock.setBorder(new EmptyBorder(0, 0, 10, 0));
        passwordBlock.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordBlock.add(passwordLabel);
        passwordBlock.add(Box.createVerticalStrut(5));
        passwordBlock.add(passwordField);

        // Кнопка регистрации
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerClickHandler(evt);
            }
        });

        // Кликабельное Текстовое поля для входа
        JLabel loginLabel = new JLabel("Have an account? Log in");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        loginLabel.setForeground(Color.GRAY);

        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                swithToLogingHandler(evt);
            }
        });

        // Добавляем элементы в container
        container.add(usernameBlock);
        container.add(Box.createVerticalStrut(10));
        container.add(passwordBlock);
        container.add(Box.createVerticalStrut(20));
        container.add(registerButton);
        container.add(Box.createVerticalStrut(15));
        container.add(loginLabel);

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.add(container);
        wrapperPanel.setBackground(Color.WHITE);

        add(wrapperPanel);
        setVisible(true);
    }

    private void swithToLogingHandler(MouseEvent evt) {
        registerContorller.switchToLoginFrame(this);
    }

    private void registerClickHandler(MouseEvent evt) {
        try {
            String login = usernameField.getText();
            String password = new String(passwordField.getPassword());
            registerContorller.register(login, password);
            JOptionPane.showMessageDialog(this, "Вы успешно зарегистрировались!",
                    "Успешно!", JOptionPane.INFORMATION_MESSAGE);

            registerContorller.switchToMainFrame(this);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка при регистрации: " + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
