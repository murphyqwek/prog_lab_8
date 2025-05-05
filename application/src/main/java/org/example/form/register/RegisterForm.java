package org.example.form.register;

import org.example.controller.RegisterContorller;
import org.example.localization.Localization;
import org.example.network.NetworkClient;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Locale;

public class RegisterForm extends JFrame {
    private RegisterContorller registerContorller;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private JLabel usernameLabel;
    private JLabel title;
    private JLabel passwordLabel;
    private JButton registerButton;
    private JLabel loginLabel;
    private JComboBox<String> languageBox;


    Locale[] locales = {
            new Locale("en"),
            new Locale("ru"),
            new Locale("el"),
            new Locale("sr"),
            new Locale("es", "HN")
    };

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
        container.setMaximumSize(new Dimension(400, 350));
        container.setPreferredSize(new Dimension(350, 350));
        container.setMinimumSize(new Dimension(350, 350));


        // Скруглённая граница
        container.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true),
                container.getBorder()
        ));

        // Поле Register
        title = new JLabel(Localization.get("register_title"));
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(title);
        container.add(Box.createVerticalStrut(20));

        // Блок для Username
        usernameLabel = new JLabel(Localization.get("username_title"));
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
        passwordLabel = new JLabel(Localization.get("password_title"));
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
        registerButton = new JButton(Localization.get("register_button_text"));
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
        loginLabel = new JLabel(Localization.get("go_to_log_in_title"));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        loginLabel.setForeground(Color.GRAY);

        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                swithToLogingHandler(evt);
            }
        });

        String[] languageNames = {"English", "Русский", "Ελληνικά", "Српски", "Español (HN)"};

        languageBox = new JComboBox<>(languageNames);
        languageBox.setPreferredSize(new Dimension(150, 25));
        languageBox.setMinimumSize(new Dimension(150, 25));
        languageBox.setMaximumSize(new Dimension(150, 25));
        languageBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        languageBox.setSelectedIndex(1);

        languageBox.addActionListener(e -> {
            selectLangChangedHandler();
        });

        // Добавляем элементы в container
        container.add(usernameBlock);
        container.add(Box.createVerticalStrut(10));
        container.add(passwordBlock);
        container.add(Box.createVerticalStrut(20));
        container.add(registerButton);
        container.add(Box.createVerticalStrut(15));
        container.add(loginLabel);
        container.add(Box.createVerticalStrut(5));
        container.add(languageBox);


        JPanel wrapperPanel = new JPanel(new GridBagLayout());

        wrapperPanel.add(container);
        wrapperPanel.setBackground(Color.WHITE);


        add(wrapperPanel);
        setVisible(true);
    }

    private void swithToLogingHandler(MouseEvent evt) {
        registerContorller.switchToLoginFrame(this);
    }

    private void updateText() {
        usernameLabel.setText(Localization.get("username_title"));
        title.setText(Localization.get("register_title"));
        passwordLabel.setText(Localization.get("password_title"));
        registerButton.setText(Localization.get("register_button_text"));
        loginLabel.setText(Localization.get("go_to_log_in_title"));
    }

    private void selectLangChangedHandler() {
        Locale selected = locales[languageBox.getSelectedIndex()];
        Localization.setLocale(selected);
        updateText();
    }

    private void registerClickHandler(MouseEvent evt) {
        try {
            String login = usernameField.getText();
            String password = new String(passwordField.getPassword());
            registerContorller.register(login, password);
            JOptionPane.showMessageDialog(this, Localization.get("success_register"),
                    Localization.get("success_title"), JOptionPane.INFORMATION_MESSAGE);

            registerContorller.switchToMainFrame(this);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, Localization.get("error_with_register") + " "  + e.getMessage(),
                    Localization.get("error_title"), JOptionPane.ERROR_MESSAGE);
        }
    }
}
