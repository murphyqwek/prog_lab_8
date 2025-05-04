package org.example.form.login;

/**
 * LoginForm - описание класса.
 *
 * @version 1.0
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class LoginForm extends JFrame {

    public LoginForm() {
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
        container.setPreferredSize(new Dimension(350, 330));

        // Скруглённая граница
        container.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true),
                container.getBorder()
        ));

        // Поле Welcome back
        JLabel title = new JLabel("Welcome back!");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(title);
        container.add(Box.createVerticalStrut(10));

        // Поле Please, enter your details
        JLabel detailsLabel = new JLabel("Please, enter your details");
        detailsLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        detailsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsLabel.setForeground(Color.GRAY);
        container.add(detailsLabel);
        container.add(Box.createVerticalStrut(20));

        // Блок для Username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField usernameField = new JTextField();
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

        JTextField passwordField = new JPasswordField();
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
        JButton registerButton = new JButton("Log in");
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Кликабельное Текстовое поля для входа
        JLabel signupLabel = new JLabel("Don’t have an account? Sign up");
        signupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        signupLabel.setForeground(Color.GRAY);

        signupLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Показать MessageBox при клике
                JOptionPane.showMessageDialog(null, "Вы авторизовались!", "Успешно", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Добавляем элементы в container
        container.add(usernameBlock);
        container.add(Box.createVerticalStrut(10));
        container.add(passwordBlock);
        container.add(Box.createVerticalStrut(20));
        container.add(registerButton);
        container.add(Box.createVerticalStrut(15));
        container.add(signupLabel);

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.add(container);
        wrapperPanel.setBackground(Color.WHITE);

        add(wrapperPanel);
        setVisible(true);
    }

    private void stylizeField(JTextField field) {
        field.setPreferredSize(new Dimension(300, 30));
        field.setMaximumSize(new Dimension(300, 30));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
}

