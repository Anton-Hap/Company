package parameters;

import java.util.List;

public class Parameters {

    private static String nameTable; //name current table

    private static String requestName;

    private static List<String> headNames; //names current columns

    private static List<String> row; //data current row

    private static List<List<String>> table;

    private static String message;

    private static String idCustomer;

    public static String getIdCustomer() {
        return idCustomer;
    }

    public static void setIdCustomer(String idCustomer) {
        Parameters.idCustomer = idCustomer;
    }

    public static List<List<String>> getTable() {
        return table;
    }

    public static void setTable(List<List<String>> table) {
        Parameters.table = table;
    }

    public static void setNameTable(String nameTable) {
        Parameters.nameTable = nameTable;
    }

    public static String getRequestName() {
        return requestName;
    }

    public static void setRequestName(String requestName) {
        Parameters.requestName = requestName;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Parameters.message = message;
    }

    public static List<String> getRow() {
        return row;
    }

    public static void setRow(List<String> row) {
        Parameters.row = row;
    }

    public static List<String> getHeadNames() {
        return headNames;
    }

    public static void setHeadNames(List<String> headNames) {
        Parameters.headNames = headNames;
    }

    public static void setTechnicTable() {
        nameTable = "Техника";
    }

    public static void setWorkerTable() {
        nameTable = "Рабочие";
    }

    public static void setRepairTable() {
        nameTable = "Виды ремонта";
    }

    public static void setCustomersTable() {
        nameTable = "Заказчики";
    }

    public static void setSuppliersTable() {
        nameTable = "Поставщики";
    }

    public static void setAccountingWorkTable() {
        nameTable = "Учет работ";
    }

    public static void setClassTechnicTable() {
        nameTable = "Классы техники";
    }

    public static String getNameTable() {
        return  nameTable;
    }
}
