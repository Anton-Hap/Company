package controllers.tables;

import constants.Const;
import utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import parameters.Parameters;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class AddRowController {

    private static List<TextField> listFields;

    @FXML
    private Label tableName, message;

    @FXML
    private Button addRowButton, backButton;

    @FXML
    private FlowPane pane;

    @FXML
    void initialize() {
        tableName.setText(Parameters.getNameTable());
        listFields = new ArrayList<>();
        initField();

        addRowButton.setOnAction(event -> {
            try {
                if (Utils.checkClass(getDataFromFields(), message)) {
                    if (Utils.checkWorkerOld(listFields)) {
                        if (Utils.checkTechnicOld(listFields)) {
                            if (Utils.checkAccountingWorkDate(getDataFromFields())) {
                                if (Utils.checkWorkerDateWork(getDataFromFields())) {
                                    if (Utils.checkDamageTechnic(listFields)) {
                                        if (Utils.checkWorkerPhoneNumber(listFields)) {
                                            dbUtils.addRow(Parameters.getNameTable(), Parameters.getHeadNames(), getDataFromFields());
                                            WindowUtils.updateWindow("TableWindow.fxml", addRowButton);
                                        } else {
                                            TimeUtils.showWaitAndDisappearLabel(message, "Номер телефона короче 12 цифр", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                                        }
                                    } else {
                                        TimeUtils.showWaitAndDisappearLabel(message, "Техника имеет больше 3-х поломок", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                                    }
                                } else {
                                    TimeUtils.showWaitAndDisappearLabel(message, "Рабочий уже работает в это время", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                                }
                            } else {
                                TimeUtils.showWaitAndDisappearLabel(message, "Ошибка дат", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                            }
                        } else {
                            TimeUtils.showWaitAndDisappearLabel(message, "Больше 3-х лет", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                        }
                    } else {
                        TimeUtils.showWaitAndDisappearLabel(message, "Меньше 18 лет", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                    }
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                e.printStackTrace();

                TimeUtils.showWaitAndDisappearLabel(message, "Ошибка внешнего ключа", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
            } catch (SQLException e) {
                e.printStackTrace();

                if (e.getErrorCode() == 1366) {
                    TimeUtils.showWaitAndDisappearLabel(message, "Пустое поле", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                } else {
                    TimeUtils.showWaitAndDisappearLabel(message, "Ошибка введенных данных", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                }
            }
        });

        backButton.setOnAction(event -> {
            WindowUtils.updateWindow("TableWindow.fxml", backButton);
        });
    }

    private static List<String> getDataFromFields() {
        List<String> list = new ArrayList<>();

        if (!Parameters.getNameTable().equals("Учет работ")) {
            for (int i = 0; i < listFields.size(); i++) {
                list.add(listFields.get(i).getText());
            }
        } else {
            list = FXUtils.getDataFromFields();
        }

        return list;
    }

    private void initField() {
        if (FXUtils.createFieldsForAccountingWork(pane)) {
            for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
                TextField textField = new TextField();

                textField.setFocusTraversable(false);
                textField.setStyle("-fx-background-color: #fff; -fx-background-radius: 10; -fx-border-color: #696969; -fx-border-radius: 10");
                textField.setEffect(new DropShadow());
                textField.setPromptText(Parameters.getHeadNames().get(i));

                if (i == 0) {
                    textField.setText(dbUtils.getNextID(Parameters.getNameTable()));
                    textField.setEditable(false);
                }

                if (i == Parameters.getHeadNames().size() - 1) {
                    Utils.blockedStatusField(textField);
                }

                listFields.add(textField);
                pane.getChildren().add(textField);

                if (i == Parameters.getHeadNames().size() - 1) {
                    Label foodLabel = new Label();
                    foodLabel.setMinWidth(600);

                    pane.getChildren().add(foodLabel);
                }
            }
        }
    }
}
