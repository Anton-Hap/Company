package controllers.request;

import constants.Const;
import utils.dbUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import parameters.Parameters;
import utils.TimeUtils;
import utils.WindowUtils;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ParameterController {

    @FXML
    private Button nextButton;

    @FXML
    private ComboBox<String> parameterBox;

    @FXML
    private TextField zpField;

    @FXML
    private Label message;

    @FXML
    void initialize() {
        parameterBox.getItems().addAll("=", "<", ">");


        nextButton.setOnAction(event -> {
            if (!zpField.getText().equals("")) {
                if (!parameterBox.getSelectionModel().isEmpty()) {
                    Parameters.setTable(dbUtils.parameterRequest(parameterBox.getSelectionModel().getSelectedItem(), zpField.getText()));
                    WindowUtils.updateWindow("RequestWindow.fxml", nextButton);
                } else {
                    TimeUtils.showWaitAndDisappearLabel(message, "Не выбран параметр", "Параметр", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                }
            } else {
                TimeUtils.showWaitAndDisappearLabel(message, "Поле пустое", "Параметр", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
            }
        });

        Pattern pattern = Pattern.compile("[0-9]*");
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (pattern.matcher(c.getControlNewText()).matches()) {
                return c ;
            } else {
                return null;
            }
        };
        TextFormatter<String> formatter = new TextFormatter<>(filter);
        zpField.setTextFormatter(formatter);
    }
}
