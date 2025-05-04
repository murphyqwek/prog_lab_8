package org.example.form.login;

/**
 * LoginForm - описание класса.
 *
 * @version 1.0
 */

import org.example.controller.LoginController;
import org.example.exception.WrongLoginPasswordException;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class LoginForm extends JFrame {
    private LoginController controller;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm(LoginController controller) {
        this.controller = controller;
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

        // Кнопка авторизоваться
        JButton loginButton = new JButton("Log in");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginClickHandler(evt);
            }
        });

        // Кликабельное Текстовое поля для входа
        JLabel signupLabel = new JLabel("Don’t have an account? Sign up");
        signupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        signupLabel.setForeground(Color.GRAY);

        signupLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                swithToRegisterFrameClickHandler(evt);
            }
        });

        // Добавляем элементы в container
        container.add(usernameBlock);
        container.add(Box.createVerticalStrut(10));
        container.add(passwordBlock);
        container.add(Box.createVerticalStrut(20));
        container.add(loginButton);
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

    private void swithToRegisterFrameClickHandler(MouseEvent evt) {
        controller.switchToRegisterFrame(this);
    }

    private void loginClickHandler(MouseEvent evt) {
        try {
            String login = usernameField.getText();
            String password = new String(passwordField.getPassword());
            controller.login(login, password);
            JOptionPane.showMessageDialog(this, "Вы успешно авторизовались!",
                    "Успешно!", JOptionPane.INFORMATION_MESSAGE);

            controller.switchToMainFrame(this);
        } catch (WrongLoginPasswordException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Ошибка", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка при регистрации: " + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}

