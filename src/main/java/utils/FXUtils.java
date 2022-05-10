package utils;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import parameters.Parameters;

import java.util.ArrayList;
import java.util.List;

public abstract class FXUtils {

    private static List<String> listIDCustomers = dbUtils.getColumn("Заказчики", "ID");
    private static List<String> listIDTechnic = dbUtils.getColumn("Техника", "ID");
    private static List<String> listIDWorker = dbUtils.getColumn("Рабочие", "ID");
    private static List<String> listIDRepair = dbUtils.getColumn("Виды ремонта", "ID");

    private static List<String> listNameCustomers = dbUtils.getColumn("Заказчики", "Наименование");
    private static List<String> listNameTechnic = dbUtils.getColumn("Техника", "Наименование");
    private static List<String> listNameWorker = dbUtils.getColumn("Рабочие", "ФИО");
    private static List<String> listNameRepair = dbUtils.getColumn("Виды ремонта", "Наименование");

    private static List<ComboBox<String>> listBoxes = new ArrayList<>();
    private static List<TextField> listFields = new ArrayList<>();

    private static int indexCustomer = 0, indexTechnic = 0, indexWorker = 0, indexRepair = 0;

    public static List<String> getDataFromFields() {
        List<String> list = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (i == indexCustomer) {
                list.add(listIDCustomers.get(listBoxes.get(0).getSelectionModel().getSelectedIndex()));
            } else if (i == indexTechnic) {
                list.add(listIDTechnic.get(listBoxes.get(1).getSelectionModel().getSelectedIndex()));
            } else if (i == indexWorker) {
                list.add(listIDWorker.get(listBoxes.get(2).getSelectionModel().getSelectedIndex()));
            } else if (i == indexRepair) {
                list.add(listIDRepair.get(listBoxes.get(3).getSelectionModel().getSelectedIndex()));
            } else {
                list.add(listFields.get(j++).getText());
            }
        }

        return list;
    }

    public static boolean setPrevData(FlowPane pane) {
        if (!Parameters.getNameTable().equals("Учет работ")) {
            return true;
        }

        listBoxes.clear();
        listFields.clear();

        List<String> list = Parameters.getRow();

        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (Parameters.getHeadNames().get(i).equals("ID Заказчика")) {
                indexCustomer = i;
                indexTechnic = i + 1;
                indexWorker = i + 2;
                indexRepair = i + 3;
            }
        }

        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (i == indexCustomer) {
                List<String> listNameCustomers = dbUtils.getColumn("Заказчики", "Наименование");

                ComboBox<String> box = new ComboBox<>();
                box.setValue("Заказчик");
                box.getItems().addAll(listNameCustomers);
                for (int j = 0; j < listNameCustomers.size(); j++) {
                    if (listIDCustomers.get(j).equals(list.get(i))) {
                        box.getSelectionModel().select(j);
                    }
                }
                listBoxes.add(box);
                pane.getChildren().add(box);
            } else if (i == indexTechnic) {
                List<String> listNameTechnic = dbUtils.getColumn("Техника", "Наименование");

                ComboBox<String> box = new ComboBox<>();
                box.getItems().addAll(listNameTechnic);
                box.setValue("Техника");
                for (int j = 0; j < listNameTechnic.size(); j++) {
                    if (listIDTechnic.get(j).equals(list.get(i))) {
                        box.getSelectionModel().select(j);
                    }
                }
                listBoxes.add(box);
                pane.getChildren().add(box);
            } else if (i == indexWorker) {
                List<String> listNameWorker = dbUtils.getColumn("Рабочие", "ФИО");

                ComboBox<String> box = new ComboBox<>();
                box.getItems().addAll(listNameWorker);
                box.setValue("Рабочие");
                for (int j = 0; j < listNameWorker.size(); j++) {
                    if (listIDWorker.get(j).equals(list.get(i))) {
                        box.getSelectionModel().select(j);
                    }
                }
                listBoxes.add(box);
                pane.getChildren().add(box);
            }  else if (i == indexRepair) {
                List<String> listNameRepair = dbUtils.getColumn("Виды ремонта", "Наименование");

                ComboBox<String> box = new ComboBox<>();
                box.getItems().addAll(listNameRepair);
                box.setValue("Виды ремонта");
                for (int j = 0; j < listNameRepair.size(); j++) {
                    if (listIDRepair.get(j).equals(list.get(i))) {
                        box.getSelectionModel().select(j);
                    }
                }
                listBoxes.add(box);
                pane.getChildren().add(box);
            } else {
                TextField textField = new TextField();

                textField.setFocusTraversable(false);
                textField.setStyle("-fx-background-color: #fff; -fx-background-radius: 10; -fx-border-color: #696969; -fx-border-radius: 10");
                textField.setEffect(new DropShadow());
                textField.setPromptText(Parameters.getHeadNames().get(i));
                textField.setText(list.get(i));

                if (i == 0) {
                    textField.setEditable(false);
                }

                if (i == Parameters.getHeadNames().size() - 1) {
                    Utils.blockedStatusField(textField);
                }

                listFields.add(textField);
                pane.getChildren().add(textField);
            }

            if (i == Parameters.getHeadNames().size() - 1) {
                Label foodLabel = new Label();
                foodLabel.setMinWidth(600);

                pane.getChildren().add(foodLabel);
            }
        }

        return false;
    }

    public static boolean createFieldsForAccountingWork(FlowPane pane) {
        if (!Parameters.getNameTable().equals("Учет работ")) {
            return true;
        }

        listBoxes.clear();
        listFields.clear();

        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (Parameters.getHeadNames().get(i).equals("ID Заказчика")) {
                indexCustomer = i;
                indexTechnic = i + 1;
                indexWorker = i + 2;
                indexRepair = i + 3;
            }
        }

        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (i == indexCustomer) {
                ComboBox<String> box = new ComboBox<>();
                box.setValue("Заказчик");
                box.getItems().addAll(listNameCustomers);
                listBoxes.add(box);
                pane.getChildren().add(box);
            } else if (i == indexTechnic) {
                ComboBox<String> box = new ComboBox<>();
                box.getItems().addAll(listNameTechnic);
                box.setValue("Техника");
                listBoxes.add(box);
                pane.getChildren().add(box);
            } else if (i == indexWorker) {
                ComboBox<String> box = new ComboBox<>();
                box.getItems().addAll(listNameWorker);
                box.setValue("Рабочие");
                listBoxes.add(box);
                pane.getChildren().add(box);
            }  else if (i == indexRepair) {
                ComboBox<String> box = new ComboBox<>();
                box.getItems().addAll(listNameRepair);
                box.setValue("Виды ремонта");
                listBoxes.add(box);
                pane.getChildren().add(box);
            } else {
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
            }

            if (i == Parameters.getHeadNames().size() - 1) {
                Label foodLabel = new Label();
                foodLabel.setMinWidth(600);

                pane.getChildren().add(foodLabel);
            }
        }

        return false;
    }
}
