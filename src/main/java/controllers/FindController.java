package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import parameters.Parameters;
import utils.WindowUtils;
import utils.dbUtils;

import java.util.ArrayList;
import java.util.List;

public class FindController {

    @FXML
    private Button nextButton;

    @FXML
    private ComboBox<String> customersBox;

    @FXML
    void initialize() {
        customersBox.getItems().addAll(dbUtils.getColumn("Заказчики", "Наименование"));
        customersBox.setValue("Заказчики");

        List<String> listID = dbUtils.getColumn("Заказчики", "ID");

        nextButton.setOnAction(event -> {
            if (!customersBox.getSelectionModel().isEmpty()) {

                List<List<String>> table = dbUtils.getTable("Учет работ");
                List<String> headNames = dbUtils.getNameColumns("Учет работ");

                int indexCustomer = 0;
                for (int i = 0; i < headNames.size(); i++) {
                    if (headNames.get(i).equals("ID Заказчика")) {
                        indexCustomer = i;
                        break;
                    }
                }

                List<List<String>> newTable = new ArrayList<>();
                for (int i = 0; i < table.size(); i++) {
                    if (table.get(i).get(indexCustomer).equals(listID.get(customersBox.getSelectionModel().getSelectedIndex()))) {
                        newTable.add(table.get(i));
                    }
                }

                Parameters.setHeadNames(headNames);
                Parameters.setTable(newTable);
                WindowUtils.updateWindow("RequestWindow.fxml", nextButton);
            }
        });
    }
}
