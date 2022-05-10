package controllers.tables;

import utils.dbUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import parameters.Parameters;
import utils.WindowUtils;

public class RemoveColumnController {

    @FXML
    private Button backButton, removeColumnButton;

    @FXML
    private TextField field;

    @FXML
    private Label tableName;

    @FXML
    void initialize() {
        tableName.setText(Parameters.getNameTable());

        removeColumnButton.setOnAction(event -> {
            dbUtils.removeColumn(Parameters.getNameTable(), field.getText());
            WindowUtils.updateWindow("TableWindow.fxml", removeColumnButton);
        });

        backButton.setOnAction(event -> {
            WindowUtils.updateWindow("TableWindow.fxml", backButton);
        });
    }
}
