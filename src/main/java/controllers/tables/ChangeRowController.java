package controllers.tables;

import constants.Const;
import utils.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import parameters.Parameters;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ChangeRowController {

    private static List<TextField> listFields;

    @FXML
    private Label tableName, message;

    @FXML
    private Button changeRowButton, backButton;

    @FXML
    private FlowPane pane;

    @FXML
    void initialize() {
        tableName.setText(Parameters.getNameTable());
        listFields = new ArrayList<>();
        initField();

        changeRowButton.setOnAction(event -> {
            try {
                if (Utils.checkClass(getDataFromFields(), message)) {
                    if (Utils.checkWorkerOld(listFields)) {
                        if (Utils.checkTechnicOld(listFields)) {
                            if (Utils.checkAccountingWorkDate(getDataFromFields())) {
                                if (Utils.checkWorkerDateWork(getDataFromFields())) {
                                    if (Utils.checkWorkerPhoneNumber(listFields)) {
                                        if (Utils.checkDamageTechnic(listFields)) {
                                            dbUtils.updateRow(Parameters.getNameTable(), Parameters.getHeadNames(), getDataFromFields(), Parameters.getRow().get(0));
                                            WindowUtils.updateWindow("TableWindow.fxml", changeRowButton);
                                        } else {
                                            dbUtils.removeRow(Parameters.getNameTable(), Parameters.getHeadNames().get(0), Parameters.getRow().get(0));
                                            Parameters.setMessage("Техника получила больше 3-х поломок. Она направлена на диагностику, в дальнейшем она будет списана.");
                                            WindowUtils.updateWindow("TableWindow.fxml", changeRowButton);
                                            WindowUtils.openNewWindow("MessageWindow.fxml", changeRowButton);
                                        }
                                    } else {
                                        TimeUtils.showWaitAndDisappearLabel(message, "Номер телефона короче 12 цифр", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
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
                } else {
                    TimeUtils.showWaitAndDisappearLabel(message, "Не совпадение классов", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                TimeUtils.showWaitAndDisappearLabel(message, "Ошибка внешнего ключа", "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
            } catch (SQLException e) {
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
        if (FXUtils.setPrevData(pane)) {
            for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
                AnchorPane flowPane = new AnchorPane();
                TextField textField = new TextField();
                Label label = new Label();

                textField.setFocusTraversable(false);
                textField.setStyle("-fx-background-color: #fff; -fx-background-radius: 10; -fx-border-color: #696969; -fx-border-radius: 10");
                textField.setEffect(new DropShadow());
                textField.setPromptText(Parameters.getHeadNames().get(i));
                textField.setText(Parameters.getRow().get(i));
                textField.setLayoutY(20);
                textField.setMinWidth(50);

                if (i == 0) {
                    textField.setEditable(false);
                }

                label.setText(Parameters.getHeadNames().get(i));
                label.setMinWidth(150);
                label.setAlignment(Pos.CENTER);
                label.setFont(new Font("Comic Sans MS", 14));

                flowPane.setMinHeight(50);
                flowPane.getChildren().addAll(label, textField);

                if (i == Parameters.getHeadNames().size() - 1) {
                    Utils.blockedStatusField(textField);
                }

                listFields.add(textField);
                pane.getChildren().add(flowPane);

                if (i == Parameters.getHeadNames().size() - 1) {
                    Label foodLabel = new Label();
                    foodLabel.setMinWidth(600);

                    pane.getChildren().add(foodLabel);
                }
            }
        }

//        Utils.checkStatusProcess(listFields);
    }
}
