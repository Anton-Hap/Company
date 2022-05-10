package controllers.tables;

import constants.Const;
import utils.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import parameters.Parameters;

import java.util.ArrayList;
import java.util.List;

public class TableController {

    @FXML
    private Button removeRecordButton, addRecordButton, removeColumnButton, addColumnButton, changeRecordButton, backButton, aboutButton, printButton;

    @FXML
    private Label nameTable, messageLabel;

    @FXML
    private TableView<ObservableList<String>> table;

    @FXML
    void initialize() {
        Parameters.setHeadNames(dbUtils.getNameColumns(Parameters.getNameTable()));
        nameTable.setText(Parameters.getNameTable());

        initHead(Parameters.getHeadNames());
        initTable(dbUtils.getTable(Parameters.getNameTable()));

        if (!Parameters.getNameTable().equals("Учет работ")) {
            printButton.setVisible(false);
        }

        printButton.setOnAction(event -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                docUtils.createDoc(table.getSelectionModel().getSelectedItem());
                TimeUtils.showWaitAndDisappearLabel(messageLabel, "Документ напечатан", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
            } else {
                TimeUtils.showWaitAndDisappearLabel(messageLabel, "Вы не выбрали строку", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
            }
        });

        aboutButton.setOnAction(event -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                Parameters.setRow(table.getSelectionModel().getSelectedItem());

                WindowUtils.updateWindow("AboutWindow.fxml", aboutButton);
            } else {
                TimeUtils.showWaitAndDisappearLabel(messageLabel, "Вы не выбрали строку", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
            }
        });

        backButton.setOnAction(event -> {
            WindowUtils.updateWindow("MenuWindow.fxml", backButton);
        });

        changeRecordButton.setOnAction(event -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                if (Utils.checkStatusCompleted(table.getSelectionModel().getSelectedItem())) {
                    Parameters.setRow(table.getSelectionModel().getSelectedItem());
                    WindowUtils.updateWindow("ChangeRowWindow.fxml", changeRecordButton);
                } else {
                    TimeUtils.showWaitAndDisappearLabel(messageLabel, "Работа уже выполнена", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
                }
            } else {
                TimeUtils.showWaitAndDisappearLabel(messageLabel, "Вы не выбрали строку", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
            }
        });

        removeColumnButton.setOnAction(event -> {
            WindowUtils.updateWindow("RemoveColumnWindow.fxml", removeColumnButton);
        });

        addColumnButton.setOnAction(event -> {
            WindowUtils.updateWindow("AddColumnWindow.fxml", addColumnButton);
        });

        addRecordButton.setOnAction(event -> {
            WindowUtils.updateWindow("AddRowWindow.fxml", addRecordButton);
        });

        removeRecordButton.setOnAction(event -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                dbUtils.removeRow(Parameters.getNameTable(), Parameters.getHeadNames().get(0), table.getSelectionModel().getSelectedItem().get(0));
                WindowUtils.updateWindow("TableWindow.fxml", removeRecordButton);
            } else {
                TimeUtils.showWaitAndDisappearLabel(messageLabel, "Вы не выбрали строку", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
            }
        });
    }

    private void initTable(List<List<String>> sqlTable) {
        for (int i = 0; i < sqlTable.size(); i++) {
            addRow(sqlTable.get(i));
        }
    }

    private void initHead(List<String> head) {
        for (int i = 0; i < head.size(); i++) {
            int finalI = i;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(head.get(i));
            col.setCellValueFactory(
                    param -> new SimpleStringProperty(param.getValue().get(finalI))
            );

            if (head.get(i).contains("ID")) {
                col.setVisible(false);
            }
            table.getColumns().add(col);
        }
    }

    private void addRow(List<String> row) {
        ObservableList<String> a = FXCollections.observableArrayList();

        a.addAll(row);

        table.getItems().add(a);
    }
}
