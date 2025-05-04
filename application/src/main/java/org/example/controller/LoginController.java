package org.example.controller;

import org.example.UserLoginPasswordContainer;
import org.example.base.response.ServerResponseType;
import org.example.command.LoginAppCommand;
import org.example.command.RegisterUserCommand;
import org.example.exception.ServerErrorResponseExcpetion;
import org.example.exception.WrongLoginPasswordException;
import org.example.form.login.LoginForm;
import org.example.form.main.MainForm;
import org.example.form.register.RegisterForm;
import org.example.network.NetworkClient;

import javax.swing.*;

/**
 * LoginController - описание класса.
 *
 * @version 1.0
 */

public class LoginController {
    private NetworkClient networkClient;

    public LoginController(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    public void login(String login, String password) {
        UserLoginPasswordContainer userLoginPasswordContainer = new UserLoginPasswordContainer();

        userLoginPasswordContainer.setLogin(login);
        userLoginPasswordContainer.setPassword(password);

        networkClient.setUserLoginPasswordContainer(userLoginPasswordContainer);

        LoginAppCommand loginAppCommand = new LoginAppCommand(networkClient);

        var response = loginAppCommand.app_execute(login, password);

        if(response.getType() == ServerResponseType.FAILURE) {
            throw new ServerErrorResponseExcpetion(response.getMessage(), false);
        }

        if(response.getType() == ServerResponseType.UNAUTHORIZED) {
            throw new WrongLoginPasswordException();
        }
    }

    public void switchToMainFrame(JFrame loginFrame) {
        MainController mainController = new MainController(networkClient);
        MainForm mainForm = new MainForm(mainController);

        loginFrame.dispose();
        SwingUtilities.invokeLater(mainForm::gui);
    }

    public void switchToRegisterFrame(JFrame loginFrame) {
        RegisterContorller registerController = new RegisterContorller(networkClient);
        RegisterForm registerForm = new RegisterForm(registerController);

        loginFrame.dispose();
        SwingUtilities.invokeLater(registerForm::gui);
    }
}
