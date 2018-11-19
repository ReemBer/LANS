package by.bsuir.spolks;

import by.bsuir.spolks.client.exception.FileSendingInterruptedException;
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

    private boolean downloadInterrupted;

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
            writelnToConsole(commandService.echo("OK!"));
        } catch (ConnectionFailedException e) {
            serverOutput.appendText(e.getCause().getMessage());
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
            writelnToConsole("Connection closed.");
        } catch (CommandSendingException e) {

        }
    }

    @FXML
    private void echo() {
        try {
            writelnToConsole(commandService.echo(commandParamsString.getText()));
        } catch (CommandSendingException e) {

        }
    }

    @FXML
    private void time() {
        try {
            writelnToConsole("Time on server: " + commandService.time());
        } catch (CommandSendingException e) {

        }
    }

    public void writelnToConsole(String text) {
        serverOutput.appendText(String.format("%s\n", text));
    }

    public void writeToConsole(String text) {
        serverOutput.appendText(String.format("%s", text));
    }

    @FXML
    private void clearConsole() {
        serverOutput.clear();
    }

    @FXML
    private void uploadFile() {
        try {
            commandService.upload(filePath.getText(), Integer.valueOf(preferredBufferSize.getText()), this);
        } catch (CommandSendingException e) {

        }
    }

    @FXML
    private void downloadFile() {
        try {
            if (downloadInterrupted) {
                establishConnection();
                commandService.continueDownload(this);
            } else {
                commandService.download(filePath.getText(), Integer.valueOf(preferredBufferSize.getText()), this, false, 0);
            }
            downloadInterrupted = false;
        } catch (FileSendingInterruptedException e) {
            downloadInterrupted = true;
            commandService = null;
        }
        catch (CommandSendingException e) {

        }
    }
}
