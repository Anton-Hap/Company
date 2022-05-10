package controllers.tables;

import constants.Const;
import utils.dbUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import parameters.Parameters;
import utils.TimeUtils;
import utils.WindowUtils;

import java.sql.SQLException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddColumnController {

    @FXML
    private Button backButton, addColumnButton;

    @FXML
    private TextField nameField, longField, defaultField;

    @FXML
    private RadioButton notNull;

    @FXML
    private Label tableName, message;

    @FXML
    private ComboBox<String> typeDataBox;

    @FXML
    void initialize() {
        typeDataBox.getItems().addAll("INT", "VARCHAR");
        tableName.setText(Parameters.getNameTable());

        addColumnButton.setOnAction(event -> {
            try {
                if (!longField.getText().equals("")) {
                    if (defaultField.getText().length() <= Integer.parseInt(longField.getText())) {
                        String type = "";

                        if (notNull.isSelected()) {
                            type = typeDataBox.getSelectionModel().getSelectedItem() + "(" + longField.getText() + ")" + "NOT NULL";
                        } else {
                            type = typeDataBox.getSelectionModel().getSelectedItem() + "(" + longField.getText() + ")";
                        }

                        if (!defaultField.getText().equals("")) {
                            type += " DEFAULT '" + defaultField.getText() + "'";
                        }

                        dbUtils.addColumn(Parameters.getNameTable(), nameField.getText(), type);
                        WindowUtils.updateWindow("TableWindow.fxml", addColumnButton);
                    } else {
                        TimeUtils.showWaitAndDisappearLabel(message, "Default больше длинны", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                    }
                } else {
                    TimeUtils.showWaitAndDisappearLabel(message, "Нет длинны", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                }
            } catch (SQLException e) {
                if (e.getErrorCode() == 1060) {
                    TimeUtils.showWaitAndDisappearLabel(message, "Такой столбец существует", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                } else if (e.getErrorCode() == 1067) {
                    TimeUtils.showWaitAndDisappearLabel(message, "Не совпадение типа данных", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                } else {
                    TimeUtils.showWaitAndDisappearLabel(message, "Ошибка данных", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                }
            }
        });

        backButton.setOnAction(event -> {
            WindowUtils.updateWindow("TableWindow.fxml", backButton);
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
        longField.setTextFormatter(formatter);
    }
}
