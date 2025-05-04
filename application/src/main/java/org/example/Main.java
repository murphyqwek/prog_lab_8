package org.example;


import javax.swing.*;

import org.example.form.login.LoginForm;
import org.example.form.main.MainForm;
import org.example.form.register.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainForm::new);
    }
}