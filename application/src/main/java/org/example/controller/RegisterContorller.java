package org.example.controller;

import org.example.UserLoginPasswordContainer;
import org.example.base.iomanager.EmptyIOManager;
import org.example.base.response.ServerResponseType;
import org.example.command.RegisterUserCommand;
import org.example.exception.ServerErrorResponseExcpetion;
import org.example.form.login.LoginForm;
import org.example.form.main.MainForm;
import org.example.network.NetworkClient;
import org.example.util.ErrorResponseHandler;

import javax.swing.*;

/**
 * RegisterContorller - описание класса.
 *
 * @version 1.0
 */

public class RegisterContorller {
    private NetworkClient networkClient;

    public RegisterContorller(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    public void register(String login, String password) {
        UserLoginPasswordContainer userLoginPasswordContainer = new UserLoginPasswordContainer();

        userLoginPasswordContainer.setLogin(login);
        userLoginPasswordContainer.setPassword(password);

        networkClient.setUserLoginPasswordContainer(userLoginPasswordContainer);

        RegisterUserCommand registerUserCommand = new RegisterUserCommand(networkClient, userLoginPasswordContainer);

        var response = registerUserCommand.app_execute(login, password);

        ErrorResponseHandler.checkForErrorResponse(response);
    }

    public void switchToMainFrame(JFrame registerFrame) {
        MainController mainController = new MainController(networkClient);
        MainForm mainForm = new MainForm(mainController);

        registerFrame.dispose();
        SwingUtilities.invokeLater(mainForm::gui);
    }

    public void switchToLoginFrame(JFrame registerFrame) {
        LoginController loginController = new LoginController(networkClient);
        LoginForm loginForm = new LoginForm(loginController);

        registerFrame.dispose();
        SwingUtilities.invokeLater(loginForm::gui);
    }
}
