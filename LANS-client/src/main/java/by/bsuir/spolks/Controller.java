package by.bsuir.spolks;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import by.bsuir.spolks.client.CommandService;
import by.bsuir.spolks.client.exception.CommandSendingException;
import by.bsuir.spolks.client.exception.ConnectionFailedException;

import javafx.scene.input.MouseEvent;

import java.util.Optional;


public class Controller {

    private CommandService commandService = null;

    @FXML
    private TextField hostInput;

    @FXML
    private TextField portInput;

    @FXML
    private Button connectionSwitchBtn;

    @FXML
    private TextField commandParamsString;

    @FXML
    private TextArea serverOutput;

    @FXML
    private TextField filePath;

    @FXML
    private TextField preferredBufferSize;

    @FXML
    public void initialize() {
        connectionSwitchBtn.setText("Connect to server");
        connectionSwitchBtn.setMinWidth(200.);
        connectionSwitchBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (commandService == null || !commandService.isConnected()) {
                establishConnection();
                connectionSwitchBtn.setText("Disconnect from server");
            } else {
                closeConnection();
                connectionSwitchBtn.setText("Connect to server");
            }
        });
    }

    private void establishConnection() {
        try {
            serverOutput.appendText("Connection establishing...\n");
            String host = hostInput.getText();
            int port = Integer.valueOf(portInput.getText());
            hostInput.setDisable(true);
            portInput.setDisable(true);
            this.commandService = Optional.ofNullable(commandService).orElse(new CommandService(host, port));
            writeToConsole(commandService.echo("OK!"));
        } catch (ConnectionFailedException e) {
            // dialog window with error message
        } catch (CommandSendingException e) {
            // dialog window with error message
        }
    }

    private void closeConnection() {
        try {
            serverOutput.appendText("Closing Connection...\n");
            this.commandService.close();
            commandService = null;
            hostInput.setDisable(false);
            portInput.setDisable(false);
            writeToConsole("Connection closed.");
        } catch (CommandSendingException e) {

        }
    }

    @FXML
    private void echo() {
        try {
            writeToConsole(commandService.echo(commandParamsString.getText()));
        } catch (CommandSendingException e) {

        }
    }

    @FXML
    private void time() {
        try {
            writeToConsole("Time on server: " + commandService.time());
        } catch (CommandSendingException e) {

        }
    }

    public void writeToConsole(String text) {
        serverOutput.appendText(String.format("%s\n", text));
    }

    @FXML
    private void clearConsole() {
        serverOutput.clear();
    }

    @FXML
    private void uploadFile() {
        try {
            commandService.upload(filePath.getText());
        } catch (CommandSendingException e) {

        }
    }

    @FXML
    private void downloadFile() {
        try {
            commandService.download(filePath.getText(), Integer.valueOf(preferredBufferSize.getText()), this);
        } catch (CommandSendingException e) {

        }
    }
}
