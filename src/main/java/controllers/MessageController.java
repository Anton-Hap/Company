package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import parameters.Parameters;
import utils.WindowUtils;

public class MessageController {

    @FXML
    private Button okButton;

    @FXML
    private Label message;

    @FXML
    void initialize() {
        message.setText(Parameters.getMessage());

        okButton.setOnAction(event -> {
            WindowUtils.closeWindow(okButton);
        });
    }
}
