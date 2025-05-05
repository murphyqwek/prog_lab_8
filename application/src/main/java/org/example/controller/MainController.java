package org.example.controller;

import org.example.base.model.MusicBand;
import org.example.base.response.ServerResponseType;
import org.example.command.*;
import org.example.exception.ExecuteAppCommandExcpetion;
import org.example.form.add.AddDialog;
import org.example.form.edit.EditDialog;
import org.example.form.removelower.RemoveLowerDialog;
import org.example.model.LocalStorage;
import org.example.network.NetworkClient;

import javax.swing.*;
import java.util.List;

/**
 * MainController - описание класса.
 *
 * @version 1.0
 */

public class MainController {
    private final NetworkClient networkClient;
    private final LocalStorage localStorage;
    private String filteringFieldName;

    public MainController(NetworkClient networkClient) {
        this.networkClient = networkClient;
        this.localStorage = new LocalStorage();
    }

    public void updateLocalStorage() {
        ShowUserCommand showUserCommand = new ShowUserCommand(networkClient);

        var response = showUserCommand.appExecute();

        if(response.getType() != ServerResponseType.SUCCESS) {
            throw new ExecuteAppCommandExcpetion(response.getMessage());
        }

        localStorage.setMusicBands(response.getMusicBandList());
    }

    public Object[][] getMusicBandsToDisplay() {
        List<MusicBand> musicBands = getMusicBands();

        return getMusicBandsToDisplay(musicBands);
    }

    public Object[][] getMusicBandsToDisplay(List<MusicBand> musicBandList) {
        Object[][] result = new Object[musicBandList.size()][];

        for(int i = 0; i < musicBandList.size(); i++) {
            MusicBand musicBand = musicBandList.get(i);
            result[i] = getMusicBandToDisplay(musicBand);
        }

        return result;
    }

    private Object[] getMusicBandToDisplay(MusicBand musicBand) {
        return new Object[] {
                musicBand.getId(),
                musicBand.getName(),
                musicBand.getCoordinates().getX(),
                musicBand.getCoordinates().getY(),
                musicBand.getCreationDate().getTime(),
                musicBand.getAlbumsCount(),
                musicBand.getNumberOfParticipants(),
                musicBand.getGenre().name(),
                musicBand.getLabel().getSales()
        };
    }

    public String getInfo() {
        InfoAppCommand infoAppCommand = new InfoAppCommand(networkClient);

        var response = infoAppCommand.appExecute();

        if(response.getType() != ServerResponseType.SUCCESS) {
            throw new ExecuteAppCommandExcpetion(response.getMessage());
        }

        return response.getMessage();
    }

    public String getUsername() {
        return networkClient.getUserLoginPasswordContainer().getLogin();
    }

    public String getSumOfAlbumsCount() {
        SumOfAlbumsAppCommand sumOfAlbumsAppCommand = new SumOfAlbumsAppCommand(networkClient);

        var response = sumOfAlbumsAppCommand.appExecute();

        if(response.getType() != ServerResponseType.SUCCESS) {
            throw new ExecuteAppCommandExcpetion(response.getMessage());
        }

        return response.getMessage();
    }

    public String clearCollection() {
        ClearCollectionAppCommand clearCollectionAppCommand = new ClearCollectionAppCommand(networkClient);

        var response = clearCollectionAppCommand.appExecute();

        if(response.getType() != ServerResponseType.SUCCESS) {
            throw new ExecuteAppCommandExcpetion(response.getMessage());
        }

        return response.getMessage();
    }

    public void uploadMusicBandWithFilterName(String name) {
        FilterContainsNameUserCommand filterContainsNameUserCommand = new FilterContainsNameUserCommand(networkClient);

        var response = filterContainsNameUserCommand.appExecute(name);

        localStorage.setMusicBands(response.getMusicBandList());
    }

    public void setFilteringFieldName(String filteringFieldName) {
        this.filteringFieldName = filteringFieldName;
    }

    public String getFilteringFieldName() {
        return this.filteringFieldName;
    }

    public List<MusicBand> getMusicBands() {
        return SortingByFieldName.sortByFieldName(localStorage.getStorage(), filteringFieldName);
    }

    public boolean openAddingDiaglog(JFrame mainForm) {
        AddController addController = new AddController(networkClient);
        AddDialog addDialog = new AddDialog(mainForm, addController);

        addDialog.gui();

        return addController.isCanceled();
    }

    public String deleteMusicBand(int id) {
        RemoveByIdCommand removeByIdCommand = new RemoveByIdCommand(networkClient);

        var response = removeByIdCommand.appExecute(id);

        return response.getMessage();
    }

    public boolean openRemoveLowerDiaglog(JFrame mainForm) {
        RemoveLowerController removeLowerController = new RemoveLowerController(networkClient);
        RemoveLowerDialog removeLowerDialog = new RemoveLowerDialog(mainForm, removeLowerController);

        removeLowerDialog.gui();

        return removeLowerController.isCanceled();
    }

    public boolean openUpdateDialog(JFrame mainForm, int id) {
        var mb = localStorage.getMusicBand(id);

        EditController editController = new EditController(networkClient, mb);
        EditDialog editDialog = new EditDialog(mainForm, editController);

        editDialog.gui();

        return editController.isCanceled();
    }

    public void executeScript(String filepath) {
        ExecuteScriptUserCommand executeScriptUserCommand = new ExecuteScriptUserCommand(networkClient);
        executeScriptUserCommand.appExecute(filepath);
    }
}
