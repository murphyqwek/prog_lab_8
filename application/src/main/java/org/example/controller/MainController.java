package org.example.controller;

import org.example.base.model.MusicBand;
import org.example.base.response.ServerErrorType;
import org.example.base.response.ServerResponseInfo;
import org.example.base.response.ServerResponseType;
import org.example.command.*;
import org.example.exception.ExecuteAppCommandExcpetion;
import org.example.form.add.AddDialog;
import org.example.form.edit.EditDialog;
import org.example.form.removelower.RemoveLowerDialog;
import org.example.form.visualise.VisualiseForm;
import org.example.localization.Localization;
import org.example.model.LocalStorage;
import org.example.network.NetworkClient;
import org.example.util.ErrorResponseHandler;

import javax.swing.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

        ErrorResponseHandler.checkForErrorResponse(response);

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

    private String foramtDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Localization.getLocale());

        return formatter.format(date);
    }

    private Object[] getMusicBandToDisplay(MusicBand musicBand) {

        return new Object[] {
                musicBand.getId(),
                musicBand.getName(),
                musicBand.getCoordinates().getX(),
                musicBand.getCoordinates().getY(),
                foramtDate(musicBand.getCreationDate()),
                musicBand.getAlbumsCount(),
                musicBand.getNumberOfParticipants(),
                musicBand.getGenre().name(),
                musicBand.getLabel().getSales()
        };
    }

    public String getInfo() {
        InfoAppCommand infoAppCommand = new InfoAppCommand(networkClient);

        var response = infoAppCommand.appExecute();

        ErrorResponseHandler.checkForErrorResponse(response);

        if(!(response instanceof ServerResponseInfo)) {
            ErrorResponseHandler.handleErrorReponse(ServerErrorType.UNEXPECTED_RESPONSE);
        }

        var responseInfo = (ServerResponseInfo) response;

        var text = Localization.get("info");
        String formatted = MessageFormat.format(text, responseInfo.getCollectionType(), foramtDate(responseInfo.getCreationDate()), responseInfo.getElementsCount());

        return formatted;
    }

    public String getUsername() {
        return networkClient.getUserLoginPasswordContainer().getLogin();
    }

    public String getSumOfAlbumsCount() {
        SumOfAlbumsAppCommand sumOfAlbumsAppCommand = new SumOfAlbumsAppCommand(networkClient);

        var response = sumOfAlbumsAppCommand.appExecute();

        ErrorResponseHandler.checkForErrorResponse(response);

        return response.getMessage();
    }

    public String clearCollection() {
        ClearCollectionAppCommand clearCollectionAppCommand = new ClearCollectionAppCommand(networkClient);

        var response = clearCollectionAppCommand.appExecute();

        ErrorResponseHandler.checkForErrorResponse(response);

        return Localization.get("collection_clear");
    }

    public void uploadMusicBandWithFilterName(String name) {
        FilterContainsNameUserCommand filterContainsNameUserCommand = new FilterContainsNameUserCommand(networkClient);

        var response = filterContainsNameUserCommand.appExecute(name);

        ErrorResponseHandler.checkForErrorResponse(response);

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

        ErrorResponseHandler.checkForErrorResponse(response);

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
        var status = executeScriptUserCommand.appExecute(filepath);

        ErrorResponseHandler.checkForScriptError(status);
    }

    public void openVisualizeFrame() {
        VisualiseController visualiseController = new VisualiseController(networkClient.getIp(), networkClient.getPort(), networkClient.getUserLoginPasswordContainer());

        VisualiseForm visualiseForm = new VisualiseForm(visualiseController);

        visualiseForm.gui();
    }
}
