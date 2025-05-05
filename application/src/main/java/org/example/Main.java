package org.example;


import javax.swing.*;

import org.example.controller.RegisterContorller;
import org.example.form.register.*;
import org.example.localization.Localization;
import org.example.network.NetworkClient;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        String ip = "localhost";
        int port = 2720;

        if(args.length != 0) {
            ip = args[0];
        }

        NetworkClient networkClient = new NetworkClient(ip, port);

        RegisterContorller registerContorller = new RegisterContorller(networkClient);
        RegisterForm registerForm = new RegisterForm(registerContorller);

        Localization.setLocale(new Locale("ru"));

        SwingUtilities.invokeLater(registerForm::gui);
    }
}