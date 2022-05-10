package utils;

import constants.Const;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import parameters.Parameters;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Utils {

    public static String fromIDColumnToTable(String idColumnName) {
        return switch (idColumnName) {
            case "ID Поставщика" -> "Поставщики";
            case "ID Класса" -> "Классы техники";
            case "ID Рабочего" -> "Рабочие";
            case "ID Заказчика" -> "Заказчики";
            case "ID Ремонта" -> "Виды ремонта";
            case "ID Техники" -> "Техника";
            case "ID" -> Parameters.getNameTable();
            default -> null;
        };
    }

    public static String fromMulNameTableToSingleNameTable(String idColumnName) {
        return switch (idColumnName) {
            case "Поставщики" -> "Поставщик";
            case "Классы техники" -> "Класс техники";
            case "Рабочие" -> "Рабочий";
            case "Заказчики" -> "Заказчик";
            case "Виды ремонта" -> "Вид ремонта";
            case "Техника" -> "Техника";
            case "Учет работ" -> "Работа";
            default -> null;
        };
    }

    public static void checkStatusProcess(List<TextField> listFields) {
        int indexStatus = 0, indexStartDate = 0, indexPlace = 0, indexCustomer = 0;
        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (Parameters.getHeadNames().get(i).equals("СТАТУС")) {
                indexStatus = i;
            }

            if (Parameters.getHeadNames().get(i).equals("Место работы")) {
                indexPlace = i;
            }

            if (Parameters.getHeadNames().get(i).equals("Дата начала")) {
                indexStartDate = i;
            }

            if (Parameters.getHeadNames().get(i).equals("ID Заказчика")) {
                indexCustomer = i;
            }
        }

        if (listFields.get(indexStatus).getText().equals(Const.STATUS_PROCESS)) {
            listFields.get(indexPlace).setEditable(false);
            listFields.get(indexStartDate).setEditable(false);
            listFields.get(indexCustomer).setEditable(false);
        }
    }

    public static boolean checkStatusCompleted(ObservableList<String> list) {
        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (Parameters.getHeadNames().get(i).equals("СТАТУС")) {
                if (list.get(i).equals(Const.STATUS_COMPLETED)) {
                    return false;
                }

                break;
            }
        }

        return true;
    }

    public static void blockedStatusField(TextField field) {
        if (!Parameters.getNameTable().equals("Учет работ")) {
            return;
        }

        if (field.getText().equals("")) {
            field.setText(Const.STATUS_EXPECTED);
        }
        field.setEditable(false);
    }

    public static void checkStatus() {
        List<List<String>> tableAccountingWork = dbUtils.getTable("Учет работ");
        List<String> headNamesAccountingWork = dbUtils.getNameColumns("Учет работ");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        int indexStatus = 0, indexEndDate = 0, indexStartDate = 0;
        for (int i = 0; i < headNamesAccountingWork.size(); i++) {
            if (headNamesAccountingWork.get(i).equals("Дата окончания")) {
                indexEndDate = i;
                continue;
            }

            if (headNamesAccountingWork.get(i).equals("Дата начала")) {
                indexStartDate = i;
            }

            if (headNamesAccountingWork.get(i).equals("СТАТУС")) {
                indexStatus = i;
            }
        }

        try {
            String status = "";
            for (int i = 0; i < tableAccountingWork.size(); i++) {
                Date workStartDate = dateFormat.parse(tableAccountingWork.get(i).get(indexStartDate));
                Date workEndDate = dateFormat.parse(tableAccountingWork.get(i).get(indexEndDate));

                if (workStartDate.getTime() > currentDate.getTime()) {
                    status = Const.STATUS_EXPECTED;
                } else if (currentDate.getTime() > workStartDate.getTime() && currentDate.getTime() < workEndDate.getTime()) {
                    status = Const.STATUS_PROCESS;
                } else if (currentDate.getTime() > workEndDate.getTime()) {
                    status = Const.STATUS_COMPLETED;
                }

                tableAccountingWork.get(i).set(indexStatus, status);
                dbUtils.updateRow("Учет работ", headNamesAccountingWork, tableAccountingWork.get(i), tableAccountingWork.get(i).get(0));
            }
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkDamageTechnic(List<TextField> listField) {
        if (!Parameters.getNameTable().equals("Техника")) {
            return true;
        }

        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (Parameters.getHeadNames().get(i).equals("Количество поломок")) {
                if (Integer.parseInt(listField.get(i).getText()) > 3) {
                    return false;
                }

                break;
            }
        }

        return true;
    }

    public static boolean checkWorkerDateWork(List<String> listField) {
        if (!Parameters.getNameTable().equals("Учет работ")) {
            return true;
        }

        List<List<String>> table = dbUtils.getTable("Учет работ");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String workerStart = "", workerEnd = "";
        int indexStart = 0, indexEnd = 0, indexWorker = 0;
        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (Parameters.getHeadNames().get(i).equals("Дата начала")) {
                workerStart = listField.get(i);
                indexStart = i;
                continue;
            }

            if (Parameters.getHeadNames().get(i).equals("Дата окончания")) {
                workerEnd = listField.get(i);
                indexEnd = i;
                continue;
            }

            if (Parameters.getHeadNames().get(i).equals("ID Рабочего")) {
                indexWorker = i;
            }
        }

        try {
            for (int i = 0; i < table.size(); i++) {
                if (!table.get(i).get(0).equals(listField.get(0))) {
                    if (table.get(i).get(indexWorker).equals(listField.get(indexWorker))) {

                        Date prevStartDate = dateFormat.parse(table.get(i).get(indexStart)), prevEndDate = dateFormat.parse(table.get(i).get(indexEnd)),
                                currentStartDate = dateFormat.parse(workerStart), currentEndDate = dateFormat.parse(workerEnd);

                        if (currentStartDate.getTime() >= prevStartDate.getTime() && currentStartDate.getTime() <= prevEndDate.getTime()) {
                            return false;
                        }

                        if (currentEndDate.getTime() >= prevStartDate.getTime() && currentEndDate.getTime() <= prevEndDate.getTime()) {
                            return false;
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean checkAccountingWorkDate(List<String> listFields) {
        if (!Parameters.getNameTable().equals("Учет работ")) {
            return true;
        }

        String start = "";
        String end = "";
        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (Parameters.getHeadNames().get(i).equals("Дата начала")) {
                start = listFields.get(i);
            }

            if (Parameters.getHeadNames().get(i).equals("Дата окончания")) {
                end = listFields.get(i);
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            Date currentDate = new Date();

            if (startDate.getTime() < endDate.getTime()) {
                if (startDate.getTime() > currentDate.getTime()) {
                    return true;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean checkTechnicOld(List<TextField> listFields) {
        if (!Parameters.getNameTable().equals("Техника")) {
            return true;
        }

        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (Parameters.getHeadNames().get(i).equals("Дата создания")) {
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                String dateWorker = listFields.get(i).getText();

                try {
                    Date worker = dateFormat.parse(dateWorker);

                    int years = (int) (((currentDate.getTime() - worker.getTime()) / (24 * 60 * 60 * 1000)) / 365);

                    if (years <= 3) {
                        return true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public static boolean checkWorkerPhoneNumber(List<TextField> listFields) {
        if (!Parameters.getNameTable().equals("Рабочие")) {
            return true;
        }

        int indexPhoneNumber = 0;
        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (Parameters.getHeadNames().get(i).equals("Номер телефона")) {
                indexPhoneNumber = i;
            }
        }

        if (listFields.get(indexPhoneNumber).getText().length() < 12) {
            return false;
        }

        return true;
    }

    public static boolean checkWorkerOld(List<TextField> listFields) {
        if (!Parameters.getNameTable().equals("Рабочие")) {
            return true;
        }

        for (int i = 0; i < Parameters.getHeadNames().size(); i++) {
            if (Parameters.getHeadNames().get(i).equals("Дата рождения")) {
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                String dateWorker = listFields.get(i).getText();

                try {
                    Date worker = dateFormat.parse(dateWorker);

                    int years = (int) (((currentDate.getTime() - worker.getTime()) / (24 * 60 * 60 * 1000)) / 365);

                    if (years >= 18) {
                        return true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public static void checkWorkerTable() {
        if (!Parameters.getNameTable().equals("Рабочие")) {
            return;
        }

        List<String> newHeadNames = new ArrayList<>(List.of("ID Hide"));
        newHeadNames.addAll(Parameters.getHeadNames());

        Parameters.setHeadNames(newHeadNames);
    }

    public static boolean checkClass(List<String> listFields, Label message) {
        if (!Parameters.getNameTable().equals("Учет работ")) {
            return true;
        }

        List<String> technic = dbUtils.getRow("техника", listFields.get(5));
        List<String> worker = dbUtils.getRow("рабочие", listFields.get(6));
        List<String> repair = dbUtils.getRow("виды ремонта", listFields.get(7));

        List<String> headNamesTechnic = dbUtils.getNameColumns("техника");
        List<String> headNamesWorker = dbUtils.getNameColumns("рабочие");
        List<String> headNamesRepair = dbUtils.getNameColumns("виды ремонта");

        int idClassTechnic = 0, idClassWorker = 0, idClassRepair = 0;

        for (int i = 0; i < headNamesTechnic.size(); i++) {
            if (headNamesTechnic.get(i).equals("ID Класса")) {
                idClassTechnic = i;
                break;
            }
        }

        for (int i = 0; i < headNamesWorker.size(); i++) {
            if (headNamesWorker.get(i).equals("ID Класса")) {
                idClassWorker = i;
                break;
            }
        }

        for (int i = 0; i < headNamesRepair.size(); i++) {
            if (headNamesRepair.get(i).equals("ID Класса")) {
                idClassRepair = i;
                break;
            }
        }

        if (!(technic.get(idClassTechnic).equals(worker.get(idClassWorker)) && worker.get(idClassWorker).equals(repair.get(idClassRepair)))) {
            TimeUtils.showWaitAndDisappearLabel(message, "Не совпадение классов: " + technic.get(idClassTechnic) + "-" + worker.get(idClassWorker) + "-" + repair.get(idClassRepair), "Добавление записи", Const.MILLI_WAIT, Const.MILLI_DISAPPEAR);
            return false;
        } else {
            return true;
        }
    }
}
