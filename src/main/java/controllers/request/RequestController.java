package controllers.request;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import parameters.Parameters;
import utils.WindowUtils;

import java.util.List;

public class RequestController {

    @FXML
    private TableView<ObservableList<String>> table;

    @FXML
    private Label requestName;

    @FXML
    private Button backButton;

    @FXML
    void initialize() {
        requestName.setText(Parameters.getRequestName());

        initHead(Parameters.getHeadNames());
        initTable(Parameters.getTable());

        backButton.setOnAction(event -> {
            WindowUtils.updateWindow("MenuWindow.fxml", backButton);
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

            table.getColumns().add(col);
        }
    }

    private void addRow(List<String> row) {
        ObservableList<String> a = FXCollections.observableArrayList();
        a.addAll(row);

        table.getItems().add(a);
    }
}
