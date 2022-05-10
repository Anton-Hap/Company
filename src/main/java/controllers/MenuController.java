package controllers;

import utils.dbUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import parameters.Parameters;
import utils.Utils;
import utils.WindowUtils;

import java.util.List;

public class MenuController {

    @FXML
    private Button technicButton, customersButton, suppliersButton, classTechnicButton, accountingWorkButton, workerButton, repairButton;

    @FXML
    private Button conditionalRequest, parameterRequest, finalRequest, joinRequest, crossRequest, findButton;

    @FXML
    void initialize() {
        Utils.checkStatus();

        findButton.setOnAction(event -> {
            WindowUtils.updateWindow("FindWindow.fxml", findButton);
        });

        crossRequest.setOnAction(event -> {
            Parameters.setRequestName("Динамика заказов по месяцам");
            Parameters.setHeadNames(List.of("Наименование заказчика", "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
                    "Сентябрь", "Октябрь", "Ноябрь", "Декабрь", "Всего"));
            Parameters.setTable(dbUtils.crossRequest());
            WindowUtils.updateWindow("RequestWindow.fxml", crossRequest);
        });

        joinRequest.setOnAction(event -> {
            Parameters.setRequestName("Общий список техники и рабочих");
            Parameters.setHeadNames(List.of("Наименование"));
            Parameters.setTable(dbUtils.unionRequest());
            WindowUtils.updateWindow("RequestWindow.fxml", joinRequest);
        });

        finalRequest.setOnAction(event -> {
            Parameters.setMessage(dbUtils.finalRequest());
            WindowUtils.openNewWindow("MessageWindow.fxml", finalRequest);
        });

        parameterRequest.setOnAction(event -> {
            Parameters.setRequestName("Список рабочих по зарплате");
            Parameters.setHeadNames(dbUtils.getNameColumns("Рабочие"));
            WindowUtils.updateWindow("ParameterWindow.fxml", parameterRequest);
        });

        conditionalRequest.setOnAction(event -> {
            Parameters.setRequestName("Выполненная работа");
            Parameters.setTable(dbUtils.conditionalRequest());
            Parameters.setHeadNames(dbUtils.getNameColumns("Учет работ"));
            WindowUtils.updateWindow("RequestWindow.fxml", conditionalRequest);
        });

        customersButton.setOnAction(event -> {
            Parameters.setCustomersTable();
            WindowUtils.updateWindow("TableWindow.fxml", customersButton);
        });

        suppliersButton.setOnAction(event -> {
            Parameters.setSuppliersTable();
            WindowUtils.updateWindow("TableWindow.fxml", suppliersButton);
        });

        repairButton.setOnAction(event -> {
            Parameters.setRepairTable();
            WindowUtils.updateWindow("TableWindow.fxml", repairButton);
        });

        accountingWorkButton.setOnAction(event -> {
            Parameters.setAccountingWorkTable();
            WindowUtils.updateWindow("TableWindow.fxml", accountingWorkButton);
        });

        classTechnicButton.setOnAction(event -> {
            Parameters.setClassTechnicTable();
            WindowUtils.updateWindow("TableWindow.fxml", classTechnicButton);
        });

        workerButton.setOnAction(event -> {
            Parameters.setWorkerTable();
            WindowUtils.updateWindow("TableWindow.fxml", technicButton);
        });

        technicButton.setOnAction(event -> {
            Parameters.setTechnicTable();
            WindowUtils.updateWindow("TableWindow.fxml", technicButton);
        });
    }
}