package controllers.tables;

import utils.dbUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import parameters.Parameters;
import utils.Utils;
import utils.WindowUtils;

import java.util.List;

public class AboutController {

    @FXML
    private Button backButton;

    @FXML
    private FlowPane pane;

    @FXML
    void initialize() {
        initCurrentData();
        initData();

        backButton.setOnAction(event -> {
            WindowUtils.updateWindow("TableWindow.fxml", backButton);
        });
    }

    private void initData() {
        List<String> foreignKeyName = dbUtils.getForeignKeyName(Parameters.getNameTable());

        if (foreignKeyName.size() != 0) {
            List<String> dataForeignKeyColumns = dbUtils.getDataFromColumns(Parameters.getNameTable(), foreignKeyName, Parameters.getRow().get(0));

            for (int i = 0; i < dataForeignKeyColumns.size(); i++) {
                List<String> row = dbUtils.getRow(Utils.fromIDColumnToTable(foreignKeyName.get(i)), dataForeignKeyColumns.get(i));
                List<String> columns = dbUtils.getNameColumns(Utils.fromIDColumnToTable(foreignKeyName.get(i)));

                Label nameLabel = new Label();
                nameLabel.setMinWidth(600);
                nameLabel.setText(Utils.fromMulNameTableToSingleNameTable(Utils.fromIDColumnToTable(foreignKeyName.get(i))));
                nameLabel.setAlignment(Pos.CENTER);
                nameLabel.setFont(Font.font("Comic Sans MS", 20));

                pane.getChildren().add(nameLabel);

                for (int j = 0; j < row.size(); j++) {
                    Label label = new Label();

                    label.setFocusTraversable(false);
                    label.setStyle("-fx-background-color: #fff; -fx-background-radius: 10; -fx-border-color: #696969; -fx-border-radius: 10");
                    label.setEffect(new DropShadow());
                    label.setPadding(new Insets(5));
                    label.setText(columns.get(j) + " - " + row.get(j));

                    pane.getChildren().addAll(label);
                }
            }

            Label foodLabel = new Label();
            foodLabel.setMinWidth(600);

            pane.getChildren().add(foodLabel);
        }
    }

    private void initCurrentData() {
        Label nameLabel = new Label();
        nameLabel.setMinWidth(600);
        nameLabel.setText(Utils.fromMulNameTableToSingleNameTable(Parameters.getNameTable()));
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setFont(Font.font("Comic Sans MS", 20));

        pane.getChildren().add(nameLabel);

        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            Label label = new Label();

            label.setFocusTraversable(false);
            label.setStyle("-fx-background-color: #fff; -fx-background-radius: 10; -fx-border-color: #696969; -fx-border-radius: 10");
            label.setEffect(new DropShadow());
            label.setPadding(new Insets(5));
            label.setText(Parameters.getHeadNames().get(i) + " - " + Parameters.getRow().get(i));

            pane.getChildren().addAll(label);
        }

        Label foodLabel = new Label();
        foodLabel.setMinWidth(600);

        pane.getChildren().add(foodLabel);
    }
}
